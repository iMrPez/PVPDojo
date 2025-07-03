package com.pvpdojo.combatant;

import com.pvpdojo.*;
import com.pvpdojo.character.datatypes.DamageData;
import com.pvpdojo.character.datatypes.DamageDisplayData;
import com.pvpdojo.character.datatypes.WeaponData;
import com.pvpdojo.combat.*;
import com.pvpdojo.combat.equipment.EquipmentStats;
import com.pvpdojo.combat.equipment.EquipmentUtility;
import com.pvpdojo.combat.inventory.InventoryItem;
import com.pvpdojo.combat.inventory.InventoryUseProperties;
import com.pvpdojo.combat.weapon.WeaponAnimationData;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Combatant
{

    private static final Logger log = LoggerFactory.getLogger(Combatant.class);
    protected Client client;
    protected PVPDojoPlugin plugin;
    protected PVPDojoConfig config;


    public int combatActionTicks = 0;

    protected int foodActionTicks = 0;
    protected int fastFoodActionTicks = 0;
    protected int potionActionTicks = 0;

    protected boolean hasRequestedItemUse = false;
    protected List<InventoryItem> requestedItems = new ArrayList<>();

    private boolean hasRequestedAttack = false;
    private AttackData requestedAttackData = null;
    private EquipmentStats requestedEquipmentStats = null;

    public boolean isFrozen = false;
    public int frozenTicks = 0;
    private int frozenCooldownTicks = 0;

    private int maxHealth = 99;
    private int health = 99;

    private List<DamageDisplayData> hitDisplays = new ArrayList<>();


    public List<Prayer> prayers = new ArrayList<>();

    public int saraBrewUses = 10;
    public int superRestoreUses = 5;
    public int hardFoodUses = 5;
    public int comboFoodUses = 5;

    public int specialAttackEnergy = 100;

    protected void initialize(int maxHealth)
    {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    protected void triggerGameTick()
    {
        handleAttack();
        handleHitDisplay();
        handleFreeze();
        handleInventory();
    }

    private void handleInventory()
    {
        if (hasRequestedItemUse)
        {
            var requestedItemList = new ArrayList<>(requestedItems);
            for (InventoryItem item : requestedItems)
            {
                log.info("Trying to use: " + item.itemName);
                if (canUseInventoryItem(item) == 2)
                {
                    log.info("Using item: " + item.itemName);
                    useInventoryItem(item);
                }

                requestedItemList.remove(item);
            }

            requestedItems = requestedItemList;
        }

        hasRequestedItemUse = false;
        requestedItems.clear();

        if (foodActionTicks > 0)
        {
            foodActionTicks--;
        }

        if (fastFoodActionTicks > 0)
        {
            fastFoodActionTicks--;
        }

        if (potionActionTicks > 0)
        {
            potionActionTicks--;
        }
    }

    private void handleFreeze()
    {
        if (frozenCooldownTicks > 0) frozenCooldownTicks--;

        if (isFrozen)
        {
            if (frozenTicks > 0)
            {
                frozenTicks--;
            }
            else
            {
                isFrozen = false;
            }
        }
    }

    protected void handleHitDisplay()
    {
        var displays = new ArrayList<>(hitDisplays);
        for (DamageDisplayData displayData : displays)
        {
            if (displayData.hitDelay > 0)
            {
                displayData.hitDelay--;
            }
            else
            {
                displayDamage(displayData.damageData);
                hitDisplays.remove(displayData);
            }
        }
    }

    public void handleAttack()
    {
        if (hasRequestedAttack && (combatActionTicks <= 1 || (requestedAttackData.weaponData.getIsInstantHit() && requestedAttackData.isSpec)))
        {
            hasRequestedAttack = false;

            requestedAttackData.isSpec = getIsSpec();
            if (requestedAttackData.isSpec)
            {
                for (int i = 0; i < requestedAttackData.specCount; i++)
                {
                    if (specialAttackEnergy >= requestedAttackData.weaponData.getSpecData().energy)
                    {
                        doAttack(requestedAttackData, requestedEquipmentStats);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            else
            {
                doAttack(requestedAttackData, requestedEquipmentStats);
            }

        }
        else
        {
            if (combatActionTicks > 0) updateCombatActionTicks(combatActionTicks - 1);
        }
    }


    public void addPrayer(Prayers prayer)
    {
        for (Prayer invalidPrayer : prayer.invalidPrayers)
        {
            prayers.remove(invalidPrayer);
        }
        prayers.add(prayer.prayer);
    }

    public void removePrayer(Prayer prayer)
    {
        prayers.remove(prayer);
    }



    public int getInventoryItemCooldown(InventoryItem item)
    {
        if (item == null) return 0;

        switch (item.type)
        {
            case OTHER:
                break;
            case HARD_FOOD:
                return foodActionTicks;
            case COMBO_FOOD:
                return fastFoodActionTicks;
            case SARA_BREW:
            case SUPER_RESTORE:
                return potionActionTicks;
        }

        return 0;
    }

    public int getInventoryItemUses(InventoryItem item)
    {
        switch (item.type)
        {
            case OTHER:
                return 0;
            case HARD_FOOD:
                return hardFoodUses;
            case COMBO_FOOD:
                return comboFoodUses;
            case SARA_BREW:
                return saraBrewUses;
            case SUPER_RESTORE:
                return superRestoreUses;
        }

        return 0;
    }


    /// -1 Invalid Item, 0: DELAYED, 1: NO USES, 2: Can Use
    public int canUseInventoryItem(InventoryItem item)
    {
        if (item == null) return -1;

        switch (item.type)
        {
            case OTHER:
                break;
            case HARD_FOOD:
                if (foodActionTicks > 0) return 0;
                if (hardFoodUses <= 0) return 1;
                break;
            case COMBO_FOOD:
                if (fastFoodActionTicks > 0) return 0;
                if (comboFoodUses <= 0) return 1;
                break;
            case SARA_BREW:
                if (potionActionTicks > 0) return 0;
                if (saraBrewUses <= 0) return 1;
                break;
            case SUPER_RESTORE:
                if (potionActionTicks > 0) return 0;
                if (superRestoreUses <= 0) return 1;
                break;
        }

        return 2;
    }

    public void requestItemUse(InventoryItem item)
    {
        if (item == null) return;

        log.info("Requested Item: " + item.itemName);
        requestedItems.add(item);
        hasRequestedItemUse = true;
    }


    public void useInventoryItem(InventoryItem item)
    {
        if (item == null) return;

        switch (item.type)
        {
            case OTHER:
                break;
            case HARD_FOOD:
                client.playSoundEffect(2393);
                setAnimation(829);
                if (combatActionTicks > 0 && combatActionTicks < 4)
                {
                    combatActionTicks = 4;
                }
                foodActionTicks = 3;
                hardFoodUses--;
                applyItemProperties(item.useProperties);
                break;
            case COMBO_FOOD:
                client.playSoundEffect(2393);
                setAnimation(829);
                if (combatActionTicks > 0)
                {
                    combatActionTicks += 2;
                }
                foodActionTicks = 3;
                fastFoodActionTicks = 3;
                potionActionTicks = 3;
                comboFoodUses--;
                applyItemProperties(item.useProperties);
                break;
            case SARA_BREW:
                client.playSoundEffect(2401);
                setAnimation(829);
                potionActionTicks = 3;
                foodActionTicks = 3;
                saraBrewUses--;
                applyItemProperties(item.useProperties);
                break;
            case SUPER_RESTORE:
                client.playSoundEffect(2401);
                setAnimation(829);
                potionActionTicks = 3;
                foodActionTicks = 3;
                superRestoreUses--;
                applyItemProperties(item.useProperties);
                break;
        }
    }

    private void applyItemProperties(InventoryUseProperties properties)
    {
        if (properties.overheal)
        {
            if (health >= maxHealth)
            {
                setHealth(Math.max(maxHealth + properties.health, health));
            }
            else
            {
                setHealth(health + properties.health);
            }
        }
        else
        {
            if (health <= maxHealth)
            {
                health = Math.min(Math.max(health + properties.health, 0), maxHealth);
            }
        }

    }

    private void doAttack(AttackData attackData, EquipmentStats equipmentStats)
    {
        var weaponData = attackData.weaponData;
        var combatStyle = weaponData.getWeaponCombatStyle();

        updateCombatActionTicks(attackData.spell != null ? 5 : (int) equipmentStats.WEAPON_SPEED);


        var target = getTarget();
        var damage1 = plugin.combatUtility.getDamage(attackData, weaponData, getEquipmentStats(), getSkills(), prayers,
                target.getWeaponData(), target.getEquipmentStats(), target.getSkills(), target.prayers);
        var damage2 = plugin.combatUtility.getDamage(attackData, weaponData, getEquipmentStats(), getSkills(), prayers,
                target.getWeaponData(), target.getEquipmentStats(), target.getSkills(), target.prayers);
        var damage3 = plugin.combatUtility.getDamage(attackData, weaponData, getEquipmentStats(), getSkills(), prayers,
                target.getWeaponData(), target.getEquipmentStats(), target.getSkills(), target.prayers);
        var damage4 = plugin.combatUtility.getDamage(attackData, weaponData, getEquipmentStats(), getSkills(), prayers,
                target.getWeaponData(), target.getEquipmentStats(), target.getSkills(), target.prayers);


        var damages = new float[]
                {
                        damage1.damage,
                        damage2.damage,
                        damage3.damage,
                        damage4.damage
                };


        var damageData = new DamageData(
                damages,
                combatStyle,
                weaponData,
                attackData);

        var weapon = getWeaponAnimationData();
        if (attackData.spell != null)
        {
            setAnimation(attackData.spell.SpellAnim);
            EquipmentUtility.playSound(client, attackData.spell.SpellSoundID);
        }
        else
        {
            if (attackData.isSpec && attackData.weaponData.getSpecData() != null)
            {
                EquipmentUtility.playSound(client, weaponData.getWeaponAttackSound());
                if (weapon != null)
                {
                    setAnimation(weapon.specID);
                }
                specialAttackEnergy -= attackData.weaponData.getSpecData().energy;
            }
            else
            {
                EquipmentUtility.playSound(client, weaponData.getWeaponAttackSound());
                if (weapon != null)
                {
                    setAnimation(weapon.getStyleID(weaponData.getStyleIndex()));
                }
            }
        }


        target.damage(damageData);
        attackedTarget(target, damageData, damage1);
    }


    protected void requestAttack(AttackData data)
    {
        /*if (combatActionTicks <= 1 || (data.isSpec && data.weaponData.getCanSpec() && data.weaponData.getSpecData().instantHit))
        {*/
            //log.info("Weapon: " + data.weaponData.getWeaponTypeName() + " | Is Spec: " + data.isSpec + " | Spec Count: " + data.specCount);
            hasRequestedAttack = true;
            requestedAttackData = data;
            requestedEquipmentStats = getEquipmentStats();
        /*}*/
    }

    public void cancelAttack()
    {
        hasRequestedAttack = false;
    }

    public void updateCombatActionTicks(int newActionTicks)
    {
        combatActionTicks = newActionTicks;

        displayCombatTicks(combatActionTicks);
    }

    public void reset()
    {
        isFrozen = false;
        combatActionTicks = 0;
        specialAttackEnergy = 100;
        hasRequestedAttack = false;
        hasRequestedItemUse = false;
        foodActionTicks = 0;
        fastFoodActionTicks = 0;
        potionActionTicks = 0;
    }

    public abstract void fightStarted();

    public abstract void fightFinished();

    public abstract void setAnimation(int animationId);

    public abstract void attackedTarget(Combatant combatant, DamageData data, DamageInfo damageInfo);

    public abstract boolean getIsSpec();

    public abstract int getSpecCount();

    public abstract Combatant getTarget();

    public abstract EquipmentStats getEquipmentStats();

    public abstract CombatantSkills getSkills();

    public abstract WeaponData getWeaponData();

    public abstract WeaponAnimationData getWeaponAnimationData();


    protected abstract void displayCombatTicks(int newActionTicks);

    protected abstract void displayDamage(DamageData damageData) ;

    public abstract int getHitDelayOffset();


    public void freeze()
    {
        if (frozenCooldownTicks > 0)
        {
            return;
        }

        isFrozen = true;
        frozenTicks = 15;
        frozenCooldownTicks = 20;
    }

    public void damage(DamageData data)
    {
        setHealth(health - data.getTotalDamage());

        if (data.attackData.spell != null && data.attackData.spell.doesFreeze() && data.getTotalDamage() > 0)
        {
            freeze();
        }

        hitDisplays.add(new DamageDisplayData(getTarget().toString(),
                data.combatStyle == CombatStyle.MELEE ? 1 - getHitDelayOffset() : 2 - getHitDelayOffset(), data));
    }


    public int getHealth()
    {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int value)
    {
        health = value;
    }

}

