package com.pvpdojo.combatant;

import com.pvpdojo.*;
import com.pvpdojo.character.CharacterObject;
import com.pvpdojo.character.datatypes.AnimationType;
import com.pvpdojo.character.datatypes.DamageData;
import com.pvpdojo.character.datatypes.WeaponData;
import com.pvpdojo.combat.*;
import com.pvpdojo.combat.equipment.EquipmentStats;
import com.pvpdojo.combat.equipment.EquipmentUtility;
import com.pvpdojo.combat.weapon.WeaponAnimationData;
import com.pvpdojo.combat.weapon.WeaponUtility;
import com.pvpdojo.timesheet.keyframe.HealthKeyFrame;
import com.pvpdojo.timesheet.keyframe.TextKeyFrame;
import com.pvpdojo.timesheet.keyframe.settings.HealthbarSprite;
import com.pvpdojo.timesheet.keyframe.settings.OverheadSprite;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class CorePlayer extends Combatant
{

    private static final Logger log = LoggerFactory.getLogger(CorePlayer.class);

    public boolean shouldAutoAttack = false;

    private int lastEquippedWeapon = -1;
    private boolean hasRequestedSpecialBar = false;
    private int requestSpecCount = 0;
    private int instantHitWeaponCountDown = 0;
    private boolean isLightbearerEquipped = false;

    private int specialAttackRechargeCounter = 0;

    private LocalPoint lastDestination;
    public boolean clickedAttackThisTick = false;
    public boolean swappedWeaponThisTick = false;

    @Inject
    public CorePlayer(Client client, PVPDojoPlugin plugin, PVPDojoConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

    }


    public void OnGameTick()
    {

        if (!plugin.hasFightStarted()) return;


        /*var destination = client.getLocalDestinationLocation();
        log.info("Destination: " + destination);
        if (destination != null)
        {
            if (lastDestination == null || (destination.getX() != lastDestination.getX() || destination.getY() != lastDestination.getY()))
            {
                log.info("Moved");
                shouldAutoAttack = false;
                attackInterrupted();
                lastDestination = destination;
            }
        }*/

        checkForAttack();

        //log.info("Weapon ID: " + PVPUtility.getWeaponID(client));
        checkForNewWeapon();

        if (clickedAttackThisTick && swappedWeaponThisTick)
        {
            shouldAutoAttack = true;
            clickedAttackThisTick = false;
            swappedWeaponThisTick = false;
        }

        //log.info("Trigger Game Tick DUMMY!");
        triggerGameTick();

        //updateActivePrayers();
        updateOverheadPrayers();

        if (instantHitWeaponCountDown > 0)
        {
            instantHitWeaponCountDown--;
        }

        updateSpecialEnergyRecharge();


    }

    public void playerMoved()
    {
        shouldAutoAttack = false;
        attackInterrupted();
    }

    public void checkForAttack()
    {
        if (!isInRange(false)) return;

        if (hasRequestedSpecialBar && (shouldAutoAttack || instantHitWeaponCountDown > 0))
        {
            plugin.specialBarOverlay.activeSpecial();
            tryAttack(requestSpecCount > 0 ? requestSpecCount : 1, true);
            hasRequestedSpecialBar = false;
            requestSpecCount = 0;
        }
        else if (shouldAutoAttack)
        {
            tryAttack(1, true);
        }
    }

    @Override
    public void fightStarted()
    {
        setHealth(getMaxHP());

        resetConsumables();
    }

    @Override
    public void fightFinished()
    {
        attackInterrupted();
        reset();
        prayers.clear();
        updateOverheadPrayers();
        plugin.healthOverlay.setPlayerHealthKeyFrame(new HealthKeyFrame(plugin.getTicks(), 1, HealthbarSprite.DEFAULT, getMaxHP(), getMaxHP()));

    }

    private void resetConsumables() {
        hardFoodUses = config.playerHardFood();
        comboFoodUses = config.playerComboFood();
        saraBrewUses = config.playerBrewDoses();
    }

    @Override
    public void setAnimation(int animationId, AnimationType type, boolean force)
    {
        client.getLocalPlayer().setAnimation(animationId);
        client.getLocalPlayer().setAnimationFrame(0);
    }


    public void OnClientTick()
    {

    }

    private void updateSpecialEnergyRecharge()
    {
        if (specialAttackEnergy < 100)
        {
            specialAttackRechargeCounter += isLightbearerEquipped ? 1 : 2;

            if (specialAttackRechargeCounter >= 50)
            {
                specialAttackEnergy = Math.min(100, specialAttackEnergy + 10);
                specialAttackRechargeCounter -= 50;
            }
        }
        else
        {
            specialAttackRechargeCounter = 0;
        }
    }

    private void checkForNewWeapon()
    {
        var currentWeaponID = EquipmentUtility.getWeaponID(client);

        if (lastEquippedWeapon != currentWeaponID && lastEquippedWeapon != -1)
        {

            var weaponData = plugin.combatUtility.getWeaponData();

            log.info("New Weapon Equipped");
            swappedWeaponThisTick = true;
            shouldAutoAttack = false;
            plugin.specialBarOverlay.setSpecialBarSelected(false);
            hasRequestedSpecialBar = false;
            requestSpecCount = 0;

            attackInterrupted();

            if (weaponData.getIsInstantHit())
            {
                instantHitWeaponCountDown = 3;
            }
        }

        lastEquippedWeapon = currentWeaponID;
    }

    public int getRequestedSpecCount()
    {
        return requestSpecCount;
    }

    public void requestedSpecialBar()
    {
        requestSpecCount++;
        hasRequestedSpecialBar = true;
    }

    public void updateOverheadPrayers()
    {
        if (prayers.contains(Prayer.PROTECT_FROM_MAGIC))
        {
            plugin.overheadOverlay.setPlayerPrayerSprite(OverheadSprite.PROTECT_MAGIC);
        }
        else if (prayers.contains(Prayer.PROTECT_FROM_MISSILES))
        {
            plugin.overheadOverlay.setPlayerPrayerSprite(OverheadSprite.PROTECT_RANGED);
        }
        else if (prayers.contains(Prayer.PROTECT_FROM_MELEE))
        {
            plugin.overheadOverlay.setPlayerPrayerSprite(OverheadSprite.PROTECT_MELEE);
        }
        else
        {
            plugin.overheadOverlay.setPlayerPrayerSprite(OverheadSprite.NONE);
        }
    }


    @Override
    public void displayDamage(DamageData data)
    {

        //log.info("Displaying Player Damage!");
        if (data.isSpec)
        {
            for (int i = 0; i < data.getSpecHitCount(); i++)
            {
                var damage = data.damages[i] * data.getSpecMultiplier();
                plugin.hitsplatOverlay.HitPlayer(plugin.getTicks(), EquipmentUtility.getHitsplatSprite((int)damage), (int)damage);
            }
        }
        else
        {
            for (int i = 0; i < data.getNormalHitCount(); i++)
            {
                var damage = data.damages[i] * data.getSpecMultiplier();
                plugin.hitsplatOverlay.HitPlayer(plugin.getTicks(), EquipmentUtility.getHitsplatSprite((int)damage), (int)damage);
            }
        }

        EquipmentUtility.playCombatStyleHitSound(client, data.combatStyle);
        plugin.healthOverlay.setPlayerHealthKeyFrame(new HealthKeyFrame(plugin.getTicks(), 10, HealthbarSprite.DEFAULT, getMaxHP(), getHealth()));

        if (getHealth() <= 0)
        {
            plugin.stopFight(false);
        }
    }

    @Override
    public int getHitDelayOffset() {
        return 1;
    }

    public void clickedAttack()
    {
        tryAttack(1, false);
        clickedAttackThisTick = true;
    }

    protected void tryAttack(int specCount, boolean ignoreSpell)
    {
        var spell = plugin.combatUtility.getSpell();
        if (ignoreSpell)
        {
            spell = null;
        }

        shouldAutoAttack = spell == null;

        var weaponData = getWeaponData();
        var attackData = new AttackData(spell, weaponData, getIsSpec(), specCount);
        requestAttack(attackData);
    }

    public void equipmentChanged()
    {
        attackInterrupted();
    }

    public boolean isInRange(boolean includeSpell)
    {
        CharacterObject dummyObject = plugin.dummy.dummyCharacter.getCharacterObject();
        LocalPoint localPoint = dummyObject.getLocation();
        var dummyWP = WorldPoint.fromLocal(client, localPoint);

        var spell = plugin.combatUtility.getSpell();
        var isMelee = getWeaponData().getWeaponCombatStyle(false) == CombatStyle.MELEE;
        return EquipmentUtility.isInRange(dummyWP, client.getLocalPlayer().getWorldLocation(), (!includeSpell || spell == null) && isMelee ? 1 : 10);
    }

    private void attackInterrupted()
    {
        cancelAttack();
        shouldAutoAttack = false;

    }

    @Override
    public void attackedTarget(Combatant combatant, DamageData data, DamageInfo damageInfo)
    {
        plugin.specialBarOverlay.setSpecialBarSelected(false);

        plugin.totalHitsOnDummy++;
        plugin.totalOffPrayerHitsOnDummy += !damageInfo.onPrayer ? 1 : 0;
        plugin.totalDamageOnDummy += data.getTotalDamage();
    }

    @Override
    public boolean getIsSpec()
    {
        return plugin.specialBarOverlay.isSpecialBarSelected();
    }

    @Override
    public int getSpecCount()
    {
        return getRequestedSpecCount();
    }

    @Override
    public Combatant getTarget()
    {
        return plugin.dummy;
    }

    @Override
    public EquipmentStats getEquipmentStats()
    {
        return plugin.combatUtility.getEquipmentStats();
    }

    @Override
    public CombatantSkills getSkills()
    {
        return plugin.fightPanel.getPlayerSkills();
    }

    @Override
    public WeaponData getWeaponData()
    {
        return plugin.combatUtility.getWeaponData();
    }


    @Override
    public WeaponAnimationData getWeaponAnimationData()
    {
        var weaponData = getWeaponData();
        if (weaponData != null)
        {
            var weapon = WeaponUtility.getWeapon(weaponData.getWeaponID());
            if (weapon != null)
            {
                return weapon.WeaponAnimationData;
            }
        }
        return null;
    }

    @Override
    public int getMaxHP()
    {
        return plugin.fightPanel.getPlayerHP();
    }

    @Override
    protected void displayCombatTicks(int ticks)
    {
        if (ticks > 0)
        {
            plugin.textOverlay.setPlayerAttackTicksFrame(new TextKeyFrame(plugin.getTicks(), 1, "" + ticks));
        }
        else
        {
            plugin.textOverlay.setPlayerAttackTicksFrame(null);
        }
    }




}
