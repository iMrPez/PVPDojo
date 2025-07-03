package com.pvpdojo;

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

    private int prayerSwapTimer = 0;

    private int equipmentSwapTimer = 0;

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
        prayerSwapTimer = config.prayerSwapDelay();
        equipmentSwapTimer = config.equipmentSwapDelay();

        dummy.setCombatStyle(CombatStyle.MAGIC);
    }

    public void OnGameTick()
    {
        CheckForPrayerSwitch();

        CheckForEquipmentSwitch();

        TryToAttack();

    }

    private void TryToAttack()
    {
        switch (dummy.combatStyle)
        {
            case MAGIC:
                lookAtPlayer();
                dummy.tryAttack(Spell.ICE_BARRAGE);
                break;
            case MELEE:
                var playerDistance = dummy.getDistanceToPlayer();
                if (playerDistance != 1)
                {
                    dummy.moveToPlayer(true);
                }
                else
                {
                    lookAtPlayer();
                    dummy.tryAttack(null);
                }
                break;
            case RANGE:
                lookAtPlayer();
                dummy.tryAttack(null);
                break;
        }
    }

    private void lookAtPlayer() {
        dummy.lookAtLocation(client.getLocalPlayer().getLocalLocation(), 2);
    }

    private void CheckForEquipmentSwitch()
    {
        if (equipmentSwapTimer > 0)
        {
            equipmentSwapTimer--;
        }
        else
        {
            Random random = new Random();

            var nextSwap = Math.max(random.nextInt(config.equipmentSwapDelay()), config.minEquipmentSwapDelay());
            equipmentSwapTimer = nextSwap;

            var playerProtectStyle = getProtectionPrayer(getPlayerPrayers());
            if (playerProtectStyle != null)
            {
                var playerDistance = dummy.getDistanceToPlayer();

                switch (playerProtectStyle)
                {
                    case MAGIC:
                        if (playerDistance > 1)
                        {
                            dummy.setCombatStyle(CombatStyle.RANGE);
                        }
                        else
                        {
                            dummy.setCombatStyle(CombatStyle.MELEE);
                        }
                        break;
                    case MELEE:
                        var useRange = random.nextBoolean();
                        if (/*useRange*/ true)
                        {
                            dummy.setCombatStyle(CombatStyle.RANGE);
                        }
                        /*else
                        {
                            dummy.setCombatStyle(CombatStyle.MAGIC);
                        }*/
                        break;
                    case RANGE:
                        dummy.setCombatStyle(CombatStyle.MELEE);
                        break;
                }
            }
        }
    }

    private void CheckForPrayerSwitch()
    {
        if (prayerSwapTimer > 0)
        {
            prayerSwapTimer--;
        }
        else
        {
            Random random = new Random();

            var nextSwap = Math.max(random.nextInt(config.prayerSwapDelay()), config.minPrayerSwapDelay());
            prayerSwapTimer = nextSwap;

            var playerAttackStyle = getPlayerAttackStyle();
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


    public List<Prayer> getPlayerPrayers()
    {
        return plugin.corePlayer.prayers;
    }

    public CombatStyle getProtectionPrayer(List<Prayer> prayers)
    {
        log.info("Prayers: " + prayers.size());
        if (prayers.contains(Prayer.PROTECT_FROM_MAGIC)) return CombatStyle.MAGIC;
        if (prayers.contains(Prayer.PROTECT_FROM_MISSILES)) return CombatStyle.RANGE;
        if (prayers.contains(Prayer.PROTECT_FROM_MELEE)) return CombatStyle.MELEE;

        return null;
    }

    public CombatStyle getPlayerAttackStyle()
    {
        var weapon = plugin.combatUtility.getWeaponData();

        if (weapon != null)
        {
            return weapon.getWeaponCombatStyle();
        }

        return null;
    }
}
