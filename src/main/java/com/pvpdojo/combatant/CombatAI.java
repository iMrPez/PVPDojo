package com.pvpdojo.combatant;

import com.pvpdojo.PVPDojoConfig;
import com.pvpdojo.PVPDojoPlugin;
import com.pvpdojo.character.datatypes.CharacterState;
import com.pvpdojo.character.datatypes.EquipmentItemData;
import com.pvpdojo.character.datatypes.MaxSpecData;
import com.pvpdojo.combat.AttackData;
import com.pvpdojo.combat.CombatStyle;
import com.pvpdojo.combat.Spell;
import com.pvpdojo.combat.inventory.InventoryItem;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatAI
{
    private static final Logger log = LoggerFactory.getLogger(CombatAI.class);
    private Client client;
    private PVPDojoPlugin plugin;
    private PVPDojoConfig config;
    private Dummy dummy;

    public int prayerSwapTimer = 0;

    public int equipmentSwapTimer = 0;

    private int freezeCooldownTimer = 0;

    public Intention intention = Intention.NONE;

    private int moveCooldownTimer = 0;

    public CombatAI(Client client, PVPDojoPlugin plugin, PVPDojoConfig config, Dummy dummy)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.dummy = dummy;

        reset();
    }

    public void reset()
    {
        prayerSwapTimer = 0;
        equipmentSwapTimer = 0;
        freezeCooldownTimer = 0;

        dummy.setCombatStyle(CombatStyle.MAGIC);
    }

    public void OnGameTick()
    {

        if (checkForConsumableUse()) return;

        checkForPrayerSwitch();

        if (checkForGearSwap()) return;

        checkForAttack();

        if (moveCooldownTimer > 0)
        {
            moveCooldownTimer--;
        }

        if (freezeCooldownTimer > 0)
        {
            freezeCooldownTimer--;
        }
    }

    private void checkForAttack()
    {
        var playerDistance = dummy.getDistanceToPlayer();

        boolean cantDoAction = (dummy.combatActionTicks > 1 && (intention != Intention.SPEC_NORMAL || (specWeapon != null && !specWeapon.weaponData.getIsInstantHit())));

        if (dummy.state == CharacterState.IDLE && !dummy.isFrozen &&
                (intention != Intention.SPEC_NORMAL || (specWeapon != null && !specWeapon.weaponData.getIsInstantHit()))
                && dummy.combatActionTicks > 0)
        {
            if (plugin.corePlayer.isFrozen && config.useDD())
            {
                moveUnderPlayer();
                return;
            }
            else if (config.useRandomMovement() && (intention != Intention.STANDARD_MELEE && intention != Intention.SPEC_NORMAL))
            {
                Random range = new Random();
                var shouldMove = range.nextInt(10);
                if (shouldMove > 6)
                {
                    moveToRandom();
                    return;
                }
            }
        }
        switch (intention)
        {
            case NONE:

                break;
            case STANDARD_MELEE:
                if (cantDoAction) return;

                lookAtPlayer();

                if (playerDistance == 1)
                {
                    var attackData = new AttackData(null, dummy.getWeaponData(), false, 1);
                    dummy.tryAttack(attackData);
                }
                else
                {
                    moveToPlayer();
                }
                break;
            case STANDARD_RANGE:
                if (cantDoAction) return;

                lookAtPlayer();

                if (playerDistance >= 1)
                {
                    var attackData = new AttackData(null, dummy.getWeaponData(), false, 1);
                    dummy.tryAttack(attackData);
                }
                else
                {
                    moveToPlayer();
                }
                break;
            case STANDARD_MAGIC:
                if (cantDoAction) return;

                lookAtPlayer();

                if (playerDistance >= 1)
                {
                    var attackData = new AttackData(config.useIceBarrage() ? Spell.ICE_BARRAGE : Spell.BLOOD_BARRAGE, dummy.getWeaponData(), false, 1);
                    dummy.tryAttack(attackData);
                }
                else
                {
                    moveToPlayer();
                }
                break;
            case SPEC_NORMAL:
                if (cantDoAction) return;

                lookAtPlayer();

                if (playerDistance < 2)
                {
                    if (specWeapon != null)
                    {
                        var attackData = new AttackData(null, specWeapon.weaponData, true, 4);
                        dummy.tryAttack(attackData);
                        specWeapon = null;
                    }
                }
                else
                {
                    moveToPlayer();
                }
                break;
            case MAGIC_FREEZE:
                if (cantDoAction) return;

                lookAtPlayer();

                if (playerDistance >= 1)
                {
                    var attackData = new AttackData(config.useIceBarrage() ? Spell.ICE_BARRAGE : Spell.BLOOD_BARRAGE, dummy.getWeaponData(), false, 1);
                    dummy.tryAttack(attackData);
                }
                else
                {
                    moveToPlayer();
                }
                break;
        }
    }


    private void moveToRandom()
    {
        if (moveCooldownTimer > 0) return;

        dummy.moveToRandom();
        moveCooldownTimer += 5;
    }

    private void moveToPlayer()
    {
        if (moveCooldownTimer > 0) return;

        dummy.moveToPlayer(true);
        moveCooldownTimer += 3;
    }

    private void moveUnderPlayer()
    {
        if (moveCooldownTimer > 0) return;

        dummy.moveToUnderPlayer(true);
        moveCooldownTimer += 1;
    }

    private EquipmentItemData specWeapon = null;

    private boolean checkForGearSwap()
    {
        var player = plugin.corePlayer;

        if (dummy.state == CharacterState.DOING_ACTION)
        {
            log.info("Doing Action");
            return false;
        }

        if (equipmentSwapTimer > 0)
        {
            equipmentSwapTimer--;

            if (intention == Intention.MAGIC_FREEZE)
            {
                intention = Intention.STANDARD_MAGIC;
            }

            if (intention == Intention.SPEC_NORMAL)
            {
                intention = Intention.STANDARD_MELEE;
            }

            return false;
        }
        else
        {
            /*Random random = new Random();
            var nextSwap = Math.max(random.nextInt(), config.minEquipmentSwapDelay());*/
            equipmentSwapTimer = config.equipmentSwapDelay();
        }

        if (shouldFreeze(player))
        {
            log.info("Freezing Player");
            return false;
        }

        // Go for SPEC
        if (shouldSpec(player))
        {
            log.info("Specing Player");
            return false;
        }

        var style = getBestStyle();

        log.info("Setting Intention: " + style);
        setIntention(style);

        return false;
    }

    private boolean shouldFreeze(CorePlayer player)
    {
        if (player.isFrozen || freezeCooldownTimer > 0) return false;

        dummy.setCombatStyle(CombatStyle.MAGIC);
        intention = Intention.MAGIC_FREEZE;
        freezeCooldownTimer = 30;
        return true;
    }

    private void setIntention(CombatStyle style)
    {
        switch (style)
        {
            case MAGIC:
                dummy.setCombatStyle(CombatStyle.MAGIC);
                intention = Intention.STANDARD_MAGIC;
                freezeCooldownTimer = 30;
                break;
            case MELEE:
                dummy.setCombatStyle(CombatStyle.MELEE);
                intention = Intention.STANDARD_MELEE;
                break;
            case RANGE:
                dummy.setCombatStyle(CombatStyle.RANGE);
                intention = Intention.STANDARD_RANGE;
                break;
        }
    }


    private CombatStyle getBestStyle()
    {
        var playerProtectStyle = getProtectionPrayer(getPlayerPrayers());

        var playerCombatStyle = getPlayerAttackStyle(true);
        /*var dummyCombatStyle = dummy.combatStyle;*/

        Random random = new Random();

        if (playerProtectStyle != null)
        {
            switch (playerProtectStyle)
            {
                case MAGIC:
                    var magicValue = random.nextInt(10);

                    if (magicValue >= 2)
                    {
                        return CombatStyle.RANGE;
                    }
                    else
                    {
                        return CombatStyle.MELEE;
                    }
                case MELEE:
                    var meleeValue = random.nextInt(10);

                    if (meleeValue >= 2 || true)
                    {
                        return CombatStyle.RANGE;
                    }
                    else
                    {
                        return CombatStyle.MAGIC;
                    }
                case RANGE:
                    var rangeValue = random.nextInt(10);

                    if (rangeValue >= 5 && false)
                    {
                        return CombatStyle.MAGIC;
                    }
                    else
                    {
                        return CombatStyle.MELEE;
                    }
            }
        }
        else
        {
            switch (playerCombatStyle)
            {
                case MAGIC:
                case RANGE:
                case MELEE:
                    var magicValue = random.nextInt(10);

                    if (magicValue >= 5)
                    {
                        return CombatStyle.RANGE;
                    }
                    else /*if (magicValue >= 3)*/
                    {
                        return CombatStyle.MELEE;
                    }
                    /*else
                    {
                        return CombatStyle.MAGIC;
                    }*/

            }
        }


        return CombatStyle.RANGE;
    }


    private boolean shouldSpec(CorePlayer player)
    {
        var specData = getMaxSpecAmount();
        if (specData.weapon != null && player.getHealth() <= specData.damage && (!dummy.isFrozen || dummy.isInMeleeRange()))
        {
            if (dummy.combatStyle != CombatStyle.MELEE)
            {
                dummy.setCombatStyle(CombatStyle.MELEE);
            }
            dummy.switchWeapon(specData.weapon);
            specWeapon = specData.weapon;
            dummy.updateWeaponAnimation();
            intention = Intention.SPEC_NORMAL;
            dummy.isTryingToSpec = true;
            return true;
        }

        return false;
    }

    private MaxSpecData getMaxSpecAmount()
    {
        int highestSpec = 0;
        EquipmentItemData highestSpecItem = null;

        for (EquipmentItemData itemData : plugin.specEquipmentData.equipmentList)
        {
            var maxHit = plugin.combatUtility.specMaxHit(itemData.weaponData, plugin.meleeEquipmentData.getEquipmentStats(plugin.itemManager), dummy.getSkills(), dummy.prayers, plugin.corePlayer.prayers);

            var specEnergy = dummy.specialAttackEnergy;
            var specData = itemData.weaponData.getSpecData();

            //log.info("Item: " + itemData.itemID + " | MaxHit: " + maxHit + " | TotalEnergy: " + specEnergy + " | Energy: " + specData.energy );
            if (specData.energy < specEnergy)
            {
                var attacks = (specData.instantHit ? Math.floor(specEnergy / (float)specData.energy) : 1) * specData.attackCount;
                var totalDamage = maxHit * attacks;

                log.info("TotalDamage: " + totalDamage + " | (" + maxHit + "/" + attacks + ")");

                if (highestSpec < Math.floor(totalDamage))
                {
                    highestSpec = (int) Math.floor(totalDamage);
                    highestSpecItem = itemData;
                }
            }
        }

        return new MaxSpecData(highestSpec, highestSpecItem);
    }



    private void checkForPrayerSwitch()
    {
        if (!config.useProtectionPrayers() || dummy.getDistanceToPlayer() < 1) return;

        if (prayerSwapTimer > 0)
        {
            prayerSwapTimer--;
        }
        else
        {
            prayerSwapTimer = config.prayerSwapDelay();

            var playerAttackStyle = getPlayerAttackStyle(true);
            if (playerAttackStyle != null)
            {
                switch (playerAttackStyle)
                {
                    case MAGIC:
                        List<Prayer> magicPrayers = new ArrayList<>();
                        magicPrayers.add(Prayer.PROTECT_FROM_MAGIC);
                        dummy.setPrayers(magicPrayers);
                        break;
                    case MELEE:
                        List<Prayer> meleePrayers = new ArrayList<>();
                        meleePrayers.add(Prayer.PROTECT_FROM_MELEE);
                        dummy.setPrayers(meleePrayers);
                        break;
                    case RANGE:
                        List<Prayer> rangePrayers = new ArrayList<>();
                        rangePrayers.add(Prayer.PROTECT_FROM_MISSILES);
                        dummy.setPrayers(rangePrayers);
                        break;
                }
            }
        }
    }

    public boolean checkForConsumableUse()
    {

        if (!config.useConsumables()) return false;

        var dummyHP = dummy.getHealth();
        Random random = new Random();
        var randomRange = random.nextInt(config.consumableUseRange());

        if (dummyHP > 0 && dummyHP <= config.tripleEatAt() - randomRange && canTripleEat())
        {
            tripleEat();
            return true;
        }
        else if (dummyHP > 0 && dummyHP <= config.singleHardFoodAt() - randomRange && canEatHardFood())
        {
            eatHardFood();
            return true;
        }
        else if (dummyHP > 0 && dummyHP <= config.singleBrewDrinkAt() - randomRange && canDrinkPotion())
        {
            drinkSaraBrewDose();
            return false;
        }

        return false;
    }

    private void lookAtPlayer()
    {
        dummy.lookAtLocation(client.getLocalPlayer().getLocalLocation(), 2);
    }

    public void eatHardFood()
    {
        dummy.requestItemUse(InventoryItem.ANGLERFISH);
    }

    public void eatComboFood()
    {
        dummy.requestItemUse(InventoryItem.KARAMBWAN);
    }

    public void drinkSaraBrewDose()
    {
        dummy.requestItemUse(InventoryItem.SARADOMIN_BREW);
    }

    public void tripleEat()
    {
        eatHardFood();
        drinkSaraBrewDose();
        eatComboFood();
    }

    public boolean canEatHardFood()
    {
        return dummy.foodActionTicks <= 1 && dummy.hardFoodUses > 0;
    }

    public boolean canEatComboFood()
    {
        return dummy.comboFoodActionTicks <= 1 && dummy.comboFoodUses > 0;
    }

    public boolean canDrinkPotion()
    {
        return dummy.potionActionTicks <= 1 && dummy.saraBrewUses > 0;
    }

    public boolean canTripleEat()
    {
        return canEatHardFood() && canEatComboFood() && canDrinkPotion();
    }


    public List<Prayer> getPlayerPrayers()
    {
        return plugin.corePlayer.prayers;
    }

    public CombatStyle getProtectionPrayer(List<Prayer> prayers)
    {
        //log.info("Prayers: " + prayers.size());
        if (prayers.contains(Prayer.PROTECT_FROM_MAGIC)) return CombatStyle.MAGIC;
        if (prayers.contains(Prayer.PROTECT_FROM_MISSILES)) return CombatStyle.RANGE;
        if (prayers.contains(Prayer.PROTECT_FROM_MELEE)) return CombatStyle.MELEE;

        return null;
    }

    public CombatStyle getPlayerAttackStyle(boolean includeStaffAsMage)
    {
        var weapon = plugin.combatUtility.getWeaponData();

        if (weapon != null)
        {
            return weapon.getWeaponCombatStyle(includeStaffAsMage);
        }

        return null;
    }

}
