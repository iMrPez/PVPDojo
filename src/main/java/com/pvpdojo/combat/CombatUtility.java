package com.pvpdojo.combat;

import com.pvpdojo.*;
import com.pvpdojo.character.datatypes.WeaponData;
import com.pvpdojo.combat.equipment.EquipmentStats;
import com.pvpdojo.combat.equipment.EquipmentUtility;
import com.pvpdojo.combat.inventory.InventoryItem;
import com.pvpdojo.combat.inventory.InventoryItemType;
import com.pvpdojo.combat.inventory.InventoryUseProperties;
import com.pvpdojo.combat.weapon.WeaponStyleControl;
import com.pvpdojo.combat.weapon.WeaponStyleType;
import com.pvpdojo.combatant.CombatantSkills;
import net.runelite.api.*;
import net.runelite.client.game.ItemManager;
import net.runelite.http.api.item.ItemEquipmentStats;
import net.runelite.http.api.item.ItemStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

public class CombatUtility
{
    private static final Logger log = LoggerFactory.getLogger(CombatUtility.class);
    private Client client;
    private PVPDojoPlugin plugin;
    private PVPDojoConfig config;
    private ItemManager itemManager;

    @Inject
    public CombatUtility(Client client, PVPDojoPlugin plugin, PVPDojoConfig config, ItemManager itemManager) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.itemManager = itemManager;
    }


    public EquipmentStats getEquipmentStats()
    {
        ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipment == null)
            return null;

        var weaponData = getWeaponData();

        /// ATTACK BONUSES ///
        int ATTACK_STAB = 0;
        int ATTACK_SLASH = 0;
        int ATTACH_CRUSH = 0;
        int ATTACK_MAGIC = 0;
        int ATTACK_RANGE = 0;

        /// DEFENCE BONUSES ///
        int DEFENCE_STAB = 0;
        int DEFENCE_SLASH = 0;
        int DEFENCE_CRUSH = 0;
        int DEFENCE_MAGIC = 0;
        int DEFENCE_RANGE = 0;

        /// OTHER BONUSES ///
        int OTHER_MELEE_STRENGTH = 0;
        int OTHER_RANGED_STRENGTH = 0;
        int OTHER_MAGIC_DAMAGE = 0;
        int OTHER_PRAYER = 0;

        /// WEAPON SPEED ///
        int WEAPON_SPEED = weaponData != null ? (weaponData.isRapid() ? -1 : 0) : 0;

        for (Item item : equipment.getItems())
        {
            if (item == null) continue;

            ItemStats stats = itemManager.getItemStats(item.getId(), false);
            if (stats != null)
            {

                ItemEquipmentStats eqStats = stats.getEquipment();
                if (eqStats != null)
                {
                    ATTACK_STAB += eqStats.getAstab();
                    ATTACK_SLASH += eqStats.getAslash();
                    ATTACH_CRUSH += eqStats.getAcrush();
                    ATTACK_MAGIC += eqStats.getAmagic();
                    ATTACK_RANGE += eqStats.getArange();

                    DEFENCE_STAB += eqStats.getDstab();
                    DEFENCE_SLASH += eqStats.getDslash();
                    DEFENCE_CRUSH += eqStats.getDcrush();
                    DEFENCE_MAGIC += eqStats.getDmagic();
                    DEFENCE_RANGE += eqStats.getDrange();

                    OTHER_MELEE_STRENGTH += eqStats.getStr();
                    OTHER_RANGED_STRENGTH += eqStats.getRstr();
                    OTHER_MAGIC_DAMAGE += eqStats.getMdmg();
                    OTHER_PRAYER += eqStats.getPrayer();

                    WEAPON_SPEED += eqStats.getAspeed();

                }
            }
        }

        return new EquipmentStats(
                ATTACK_STAB,
                ATTACK_SLASH,
                ATTACH_CRUSH,
                ATTACK_MAGIC,
                ATTACK_RANGE,
                DEFENCE_STAB,
                DEFENCE_SLASH,
                DEFENCE_CRUSH,
                DEFENCE_MAGIC,
                DEFENCE_RANGE,
                OTHER_MELEE_STRENGTH,
                OTHER_RANGED_STRENGTH,
                OTHER_MAGIC_DAMAGE,
                OTHER_PRAYER,
                WEAPON_SPEED
        );
    }


    public WeaponData getWeaponData()
    {
        int style = client.getVarpValue(VarPlayer.ATTACK_STYLE);
        int weaponType = client.getVarbitValue(Varbits.EQUIPPED_WEAPON_TYPE);

        var weaponID = EquipmentUtility.getWeaponID(client);

        if (weaponType == 0) // Unarmed
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,0, "Unarmed", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.CRUSH, 0, "Punch", false);
                case 1: return new WeaponData(weaponID,0, "Unarmed", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 1, "Kick", false);
                case 3: return new WeaponData(weaponID,0, "Unarmed", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.CRUSH, 3, "Block", false);
            }
        }

        if (weaponType == 1) // Axe
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,1, "Axe", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.SLASH, 0, "Chop", false);
                case 1: return new WeaponData(weaponID,1, "Axe", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.SLASH, 1, "Hack", false);
                case 2: return new WeaponData(weaponID,1, "Axe", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 2, "Smash", false);
                case 3: return new WeaponData(weaponID,1, "Axe", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.SLASH, 3, "Block", false);
            }
        }

        if (weaponType == 2) // Blunt
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,2, "Blunt", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.CRUSH, 0, "Pound", false);
                case 1: return new WeaponData(weaponID,2, "Blunt", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 1, "Pummel", false);
                case 3: return new WeaponData(weaponID,2, "Blunt", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.CRUSH, 3, "Block", false);
            }
        }

        // Ranged weapon types
        if (weaponType == 3) // Bow
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,3, "Bow", WeaponStyleControl.RANGE_ACCURATE, WeaponStyleType.RANGE, 0, "Accurate", false);
                case 1: return new WeaponData(weaponID,3, "Bow", WeaponStyleControl.RANGE_RAPID, WeaponStyleType.RANGE, 1, "Rapid", true);
                case 3: return new WeaponData(weaponID,3, "Bow", WeaponStyleControl.RANGE_LONGRANGE, WeaponStyleType.RANGE, 3, "Longrange", false);
            }
        }

        if (weaponType == 5) // Crossbow
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,5, "Crossbow", WeaponStyleControl.RANGE_ACCURATE, WeaponStyleType.RANGE, 0, "Accurate", false);
                case 1: return new WeaponData(weaponID,5, "Crossbow", WeaponStyleControl.RANGE_RAPID, WeaponStyleType.RANGE, 1, "Rapid", true);
                case 3: return new WeaponData(weaponID,5, "Crossbow", WeaponStyleControl.RANGE_LONGRANGE, WeaponStyleType.RANGE, 3, "Longrange", false);
            }
        }

        if (weaponType == 9) // Slash Sword
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,9, "Slash Sword", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.SLASH, 0, "Chop", false);
                case 1: return new WeaponData(weaponID,9, "Slash Sword", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.SLASH, 1, "Slash", false);
                case 2: return new WeaponData(weaponID,9, "Slash Sword", WeaponStyleControl.MELEE_CONTROLLED, WeaponStyleType.STAB, 2, "Lunge", false);
                case 3: return new WeaponData(weaponID,9, "Slash Sword", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.SLASH, 3, "Block", false);
            }
        }

        if (weaponType == 15) // Spear
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,15, "Spear", WeaponStyleControl.MELEE_CONTROLLED, WeaponStyleType.STAB, 0, "Lunge", false);
                case 1: return new WeaponData(weaponID,15, "Spear", WeaponStyleControl.MELEE_CONTROLLED, WeaponStyleType.SLASH, 1, "Swipe", false);
                case 2: return new WeaponData(weaponID,15, "Spear", WeaponStyleControl.MELEE_CONTROLLED, WeaponStyleType.CRUSH, 2, "Pound", false);
                case 3: return new WeaponData(weaponID,15, "Spear", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.STAB, 3, "Block", false);
            }
        }

        if (weaponType == 16) // Spiked
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,16, "Spiked", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.CRUSH, 0, "Pound", false);
                case 1: return new WeaponData(weaponID,16, "Spiked", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 1, "Pummel", false);
                case 2: return new WeaponData(weaponID,16, "Spiked", WeaponStyleControl.MELEE_CONTROLLED, WeaponStyleType.STAB, 2, "Pummel", false);
                case 3: return new WeaponData(weaponID,16, "Spiked", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.CRUSH, 3, "Block", false);
            }
        }


        if (weaponType == 17) // Stab Sword
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,17, "Stab Sword", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.STAB, 0, "Stab", false);
                case 1: return new WeaponData(weaponID,17, "Stab Sword", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.STAB, 1, "Lunge", false);
                case 2: return new WeaponData(weaponID,17, "Stab Sword", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.SLASH, 2, "Slash", false);
                case 3: return new WeaponData(weaponID,17, "Stab Sword", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.STAB, 3, "Block", false);
            }
        }

        if (weaponType == 18) // Staff
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,18, "Staff", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.CRUSH, 0, "Bash", false);
                case 1: return new WeaponData(weaponID,18, "Staff", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 1, "Pound", false);
                case 3: return new WeaponData(weaponID,18, "Staff", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.CRUSH, 3, "Focus", false);
                case 4: return new WeaponData(weaponID,18, "Staff", WeaponStyleControl.MAGIC_AUTOCAST, WeaponStyleType.SLASH, 4, "Autocast", false);
            }
        }

        if (weaponType == 19) // Thrown
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,19, "Thrown", WeaponStyleControl.RANGE_ACCURATE, WeaponStyleType.RANGE, 0, "Accurate", false);
                case 1: return new WeaponData(weaponID,19, "Thrown", WeaponStyleControl.RANGE_RAPID, WeaponStyleType.RANGE, 1, "Rapid", true);
                case 3: return new WeaponData(weaponID,19, "Thrown", WeaponStyleControl.RANGE_LONGRANGE, WeaponStyleType.RANGE, 3, "Longrange", false);
            }
        }

        if (weaponType == 20) // Whip
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,20, "Whip", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.SLASH, 0, "Flick", false);
                case 1: return new WeaponData(weaponID,20, "Whip", WeaponStyleControl.MELEE_CONTROLLED, WeaponStyleType.SLASH, 1, "Lash", false);
                case 3: return new WeaponData(weaponID,20, "Whip", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.SLASH, 3, "Deflect", false);
            }
        }

        if (weaponType == 23) // Godsword
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,23, "Godsword", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.SLASH, 0, "Chop", false);
                case 1: return new WeaponData(weaponID,23, "Godsword", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.SLASH, 1, "Slash", false);
                case 2: return new WeaponData(weaponID,23, "Godsword", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 2, "Smash", false);
                case 3: return new WeaponData(weaponID,23, "Godsword", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.SLASH, 3, "Block", false);
            }
        }

        if (weaponType == 24) // Powered Staff
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,24, "Powered Staff", WeaponStyleControl.MAGIC_ACCURATE, WeaponStyleType.MAGIC, 0, "Accurate", false);
                case 1: return new WeaponData(weaponID,24, "Powered Staff", WeaponStyleControl.MAGIC_ACCURATE, WeaponStyleType.MAGIC, 1, "Accurate", false);
                case 3: return new WeaponData(weaponID,24, "Powered Staff", WeaponStyleControl.MAGIC_LONGRANGE, WeaponStyleType.MAGIC, 3, "Longrange", false);
            }
        }

        if (weaponType == 30) // Partisan
        {
            switch (style)
            {
                case 0: return new WeaponData(weaponID,30, "Partisan", WeaponStyleControl.MELEE_ACCURATE, WeaponStyleType.STAB, 0, "Stab", false);
                case 1: return new WeaponData(weaponID,30, "Partisan", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.STAB, 1, "Lunge", false);
                case 2: return new WeaponData(weaponID,30, "Partisan", WeaponStyleControl.MELEE_AGGRESSIVE, WeaponStyleType.CRUSH, 2, "Pound", false);
                case 3: return new WeaponData(weaponID,30, "Partisan", WeaponStyleControl.MELEE_DEFENSIVE, WeaponStyleType.STAB, 3, "Block", false);
            }
        }

        return null;
    }

    public Prayers getPrayerFromMenu(MenuEntry menuEntry)
    {
        return Prayers.getPrayer(menuEntry.getTarget());
    }

    public InventoryItem getItemFromMenu(MenuEntry menuEntry)
    {
        if (menuEntry.getOption().contains("Eat"))
        {
            if (menuEntry.getTarget().contains("Anglerfish")) return InventoryItem.ANGLERFISH;
            if (menuEntry.getTarget().contains("Cooked karambwan")) return InventoryItem.KARAMBWAN;
        }

        if (menuEntry.getOption().contains("Drink"))
        {
            if (menuEntry.getTarget().contains("Saradomin brew")) return InventoryItem.SARADOMIN_BREW;
            if (menuEntry.getTarget().contains("Super restore")) return InventoryItem.ANGLERFISH;
        }

        return null;
    }

    public DamageInfo getDamage(AttackData attackData,
                                WeaponData attackerWeaponData, EquipmentStats attackerStats, CombatantSkills attackerSkills, List<Prayer> attackerPrayers,
                                WeaponData targetWeaponData, EquipmentStats targetStats, CombatantSkills targetSkills, List<Prayer> targetPrayers)
    {
        var effectiveAttackerSkills = plugin.combatUtility.getEffectiveSkills(attackerSkills, attackerWeaponData, attackerPrayers);
        var effectiveAttackerStats = getEffectiveStats(attackerStats, attackerPrayers);
        var maxHit = getMaxHit(attackData.spell, effectiveAttackerSkills, attackerWeaponData, attackerStats);

        var attackRoll = 0f;
        if (attackData.spell != null)
        {
            attackRoll = effectiveAttackerSkills.MAGIC * (64f + getAttackEquipmentBonus(effectiveAttackerStats, WeaponStyleType.MAGIC));
        }
        else
        {
            attackRoll = effectiveAttackerSkills.ATTACK * (64f + getAttackEquipmentBonus(effectiveAttackerStats, attackerWeaponData.getStyleType()));
        }

        var effectedTargetSkills = plugin.combatUtility.getEffectiveSkills(targetSkills, targetWeaponData, targetPrayers);
        var effectiveTargetStats = getEffectiveStats(targetStats, targetPrayers);

        var defenceRoll = 0f;
        if (attackData.spell != null)
        {
            defenceRoll = effectedTargetSkills.MAGIC * (64f + getDefenceEquipmentBonus(effectiveTargetStats, WeaponStyleType.MAGIC));
        }
        else
        {
            defenceRoll = effectedTargetSkills.DEFENCE * (64f + getDefenceEquipmentBonus(effectiveTargetStats, targetWeaponData.getStyleType()));
        }

        if (attackData.isSpec && attackData.weaponData.getCanSpec())
        {
            attackRoll *= attackData.weaponData.getSpecData().accuracyMultiplier;
        }

        var hitChance = 0f;
        if (attackRoll > defenceRoll)
        {
            hitChance = 1 - ((defenceRoll + 2) / (2 * (attackRoll + 1)));
        }
        else
        {
            hitChance = (attackRoll) / (2 * (defenceRoll + 1));
        }

        //log.info("Hit Chance: " + hitChance);

        Random random = new Random();
        var hasHit = (random.nextInt(100) / 100f) < hitChance;

        if (hasHit)
        {
            var hitAmount = Math.max(random.nextInt(maxHit), 1);


            var damageAfterPrayer = getDamageAfterPrayer(hitAmount, attackData.spell != null ? CombatStyle.MAGIC : attackerWeaponData.getWeaponCombatStyle(), targetPrayers);
            var damageInfo = new DamageInfo(damageAfterPrayer, damageAfterPrayer != hitAmount);
            return damageInfo;
        }
        else
        {
            var hitAmount = 100;
            var damageAfterPrayer = getDamageAfterPrayer(hitAmount, attackData.spell != null ? CombatStyle.MAGIC : attackerWeaponData.getWeaponCombatStyle(), targetPrayers);
            return new DamageInfo(0, damageAfterPrayer != hitAmount);
        }
    }

    public int getDamageAfterPrayer(int damageAmount, CombatStyle attackStyle, List<Prayer> targetPrayers)
    {
        switch (attackStyle)
        {
            case MAGIC:
                if (targetPrayers.contains(Prayer.PROTECT_FROM_MAGIC)) return Math.max((int)(damageAmount * 0.6f), 1);
            case MELEE:
                if (targetPrayers.contains(Prayer.PROTECT_FROM_MELEE)) return Math.max((int)(damageAmount * 0.6f), 1);
            case RANGE:
                if (targetPrayers.contains(Prayer.PROTECT_FROM_MISSILES)) return Math.max((int)(damageAmount * 0.6f), 1);
        }

        return damageAmount;
    }

    public float getAttackEquipmentBonus(EquipmentStats stats, WeaponStyleType type)
    {
        switch (type) {
            case NONE:
                return 0f;
            case STAB:
                return stats.ATTACK_STAB;
            case SLASH:
                return stats.ATTACK_SLASH;
            case CRUSH:
                return stats.ATTACH_CRUSH;
            case MAGIC:
                return stats.ATTACK_MAGIC;
            case RANGE:
                return stats.ATTACK_RANGE;
        }

        return 0f;
    }

    public float getDefenceEquipmentBonus(EquipmentStats stats, WeaponStyleType type)
    {
        switch (type) {
            case NONE:
                return 0f;
            case STAB:
                return stats.DEFENCE_STAB;
            case SLASH:
                return stats.DEFENCE_SLASH;
            case CRUSH:
                return stats.ATTACH_CRUSH;
            case MAGIC:
                return stats.DEFENCE_MAGIC;
            case RANGE:
                return stats.DEFENCE_RANGE;
        }

        return 0f;
    }

    public int getMaxHit(Spell spell, CombatantSkills skills, WeaponData weaponStyle, EquipmentStats attackerStats)
    {

        if (spell != null)
        {
            return (int)((float)spell.Damage * ((attackerStats.OTHER_MAGIC_DAMAGE / 100f) + 1f));
        }

        switch (weaponStyle.getStyleType())
        {
            case NONE:
                break;
            case STAB:
            case SLASH:
            case CRUSH:
                var str = skills.STRENGTH + 0.5f;
                var strBonus = ((attackerStats.OTHER_MELEE_STRENGTH + 64f) / 640f);
                return (int)(str * strBonus);
            case MAGIC:

                return 0;
            case RANGE:
                var range = skills.RANGE + 0.5f;
                var rangeBonus = ((attackerStats.OTHER_RANGED_STRENGTH + 64f) / 640f);
                return (int)(range * rangeBonus);
            default:
                return 0;
        }
        return 0;
    }

    public Spell getSpell()
    {
        if (client.getSelectedWidget() == null)
        {
            return null;
        }

        var spellWidget = client.getSelectedWidget().getName();
        log.info("Selected Spell: " + spellWidget);
        for (var spell : Spell.values())
        {
            log.info("Checking: (" + spell.SpellName + " | " + spellWidget + ")");
            if (spellWidget.contains(spell.SpellName))
            {
                log.info("Found Spell: " + spell.SpellName);
                return spell;
            }
        }

        return null;
    }

    public CombatantSkills getEffectiveSkills(CombatantSkills skills, WeaponData weaponStyle, List<Prayer> prayers)
    {
        return new CombatantSkills(
                getEffectiveLevel(Skill.ATTACK, skills, weaponStyle, prayers),
                getEffectiveLevel(Skill.STRENGTH, skills, weaponStyle, prayers),
                getEffectiveLevel(Skill.DEFENCE, skills, weaponStyle, prayers),
                getEffectiveLevel(Skill.RANGED, skills, weaponStyle, prayers),
                getEffectiveLevel(Skill.MAGIC, skills, weaponStyle, prayers)
        );
    }

    public int getEffectiveLevel(Skill skill, CombatantSkills skills, WeaponData weaponStyle, List<Prayer> prayers)
    {
        if (weaponStyle == null) return -1;

        switch (skill) {
            case ATTACK:
                var attackStyleBonus = weaponStyle.getStyleBonus(skill);
                var attackPrayerModifier = getSkillPrayerModifier(skill, prayers);
                return Math.round((skills.ATTACK + attackStyleBonus) * attackPrayerModifier) + 8;
            case STRENGTH:
                var strengthStyleBonus = weaponStyle.getStyleBonus(skill);
                var strengthPrayerModifier = getSkillPrayerModifier(skill, prayers);
                return Math.round((skills.STRENGTH + strengthStyleBonus) * strengthPrayerModifier) + 8;
            case DEFENCE:
                var defenceStyleBonus = weaponStyle.getStyleBonus(skill);
                var defencePrayerModifier = getSkillPrayerModifier(skill, prayers);
                return Math.round((skills.DEFENCE + defenceStyleBonus) * defencePrayerModifier) + 8;
            case RANGED:
                var rangeStyleBonus = weaponStyle.getStyleBonus(skill);
                var rangePrayerModifier = getSkillPrayerModifier(skill, prayers);
                return Math.round((skills.RANGE + rangeStyleBonus) * rangePrayerModifier) + 8;
            case MAGIC:
                var magicStyleBonus = weaponStyle.getStyleBonus(skill);
                var magicPrayerModifier = getSkillPrayerModifier(skill, prayers);
                return Math.round((skills.RANGE + magicStyleBonus) * magicPrayerModifier) + 9;
            default:
                return -1;
        }
    }

    public float getSkillPrayerModifier(Skill skill, List<Prayer> prayers)
    {
        switch (skill)
        {
            case ATTACK:
                if (prayers.contains(Prayer.CLARITY_OF_THOUGHT)) return 1.05f;
                if (prayers.contains(Prayer.IMPROVED_REFLEXES)) return 1.10f;
                if (prayers.contains(Prayer.INCREDIBLE_REFLEXES)) return 1.15f;
                if (prayers.contains(Prayer.CHIVALRY)) return 1.15f;
                if (prayers.contains(Prayer.PIETY)) return 1.20f;
                break;
            case DEFENCE:
                if (prayers.contains(Prayer.THICK_SKIN)) return 1.05f;
                if (prayers.contains(Prayer.ROCK_SKIN)) return 1.10f;
                if (prayers.contains(Prayer.STEEL_SKIN)) return 1.15f;
                if (prayers.contains(Prayer.CHIVALRY)) return 1.20f;
                if (prayers.contains(Prayer.PIETY)) return 1.25f;

                if (prayers.contains(Prayer.DEADEYE)) return 1.05f;
                if (prayers.contains(Prayer.RIGOUR)) return 1.25f;

                if (prayers.contains(Prayer.MYSTIC_VIGOUR)) return 1.05f;
                if (prayers.contains(Prayer.AUGURY)) return 1.25f;
                break;
            case STRENGTH:
                if (prayers.contains(Prayer.BURST_OF_STRENGTH)) return 1.05f;
                if (prayers.contains(Prayer.SUPERHUMAN_STRENGTH)) return 1.10f;
                if (prayers.contains(Prayer.ULTIMATE_STRENGTH)) return 1.15f;
                if (prayers.contains(Prayer.CHIVALRY)) return 1.18f;
                if (prayers.contains(Prayer.PIETY)) return 1.23f;
                break;
            case RANGED:
                if (prayers.contains(Prayer.SHARP_EYE)) return 1.05f;
                if (prayers.contains(Prayer.HAWK_EYE)) return 1.10f;
                if (prayers.contains(Prayer.EAGLE_EYE)) return 1.15f;
                break;
            case MAGIC:
                break;
            default:
                break;
        }

        return 1;
    }

    public EquipmentStats getEffectiveStats(EquipmentStats stats, List<Prayer> prayers)
    {
        float ATTACK_STAB = stats.ATTACK_STAB;
        float ATTACK_SLASH = stats.ATTACK_SLASH;
        float ATTACH_CRUSH = stats.ATTACH_CRUSH;
        float ATTACK_MAGIC = stats.ATTACK_MAGIC;
        float ATTACK_RANGE = stats.ATTACK_RANGE;

        float DEFENCE_STAB = stats.DEFENCE_STAB;
        float DEFENCE_SLASH = stats.DEFENCE_SLASH;
        float DEFENCE_CRUSH = stats.DEFENCE_CRUSH;
        float DEFENCE_MAGIC = stats.DEFENCE_MAGIC;
        float DEFENCE_RANGE = stats.DEFENCE_RANGE;
        float OTHER_MELEE_STRENGTH = stats.OTHER_MELEE_STRENGTH;
        float OTHER_RANGED_STRENGTH = stats.OTHER_RANGED_STRENGTH;
        float OTHER_MAGIC_DAMAGE = stats.OTHER_MAGIC_DAMAGE;


        if (prayers.contains(Prayer.MYSTIC_WILL))
        {
            ATTACK_MAGIC *= 1.05f;
            DEFENCE_MAGIC *= 1.05f;
        }

        if (prayers.contains(Prayer.MYSTIC_LORE))
        {
            ATTACK_MAGIC *= 1.10f;
            DEFENCE_MAGIC *= 1.10f;
            OTHER_MAGIC_DAMAGE += 1;
        }

        if (prayers.contains(Prayer.MYSTIC_MIGHT))
        {
            ATTACK_MAGIC *= 1.15f;
            DEFENCE_MAGIC *= 1.15f;
            OTHER_MAGIC_DAMAGE += 2;
        }

        if (prayers.contains(Prayer.MYSTIC_VIGOUR))
        {
            ATTACK_MAGIC *= 1.18f;
            DEFENCE_MAGIC *= 1.18f;
            OTHER_MAGIC_DAMAGE += 3;
        }

        if (prayers.contains(Prayer.AUGURY))
        {
            ATTACK_MAGIC *= 1.25f;
            DEFENCE_MAGIC *= 1.25f;
            OTHER_MAGIC_DAMAGE += 4;
        }

        if (prayers.contains(Prayer.DEADEYE))
        {
            ATTACK_RANGE *= 1.18f;;
            OTHER_RANGED_STRENGTH *= 1.18f;
        }

        if (prayers.contains(Prayer.RIGOUR))
        {
            ATTACK_RANGE *= 1.20f;
            OTHER_RANGED_STRENGTH *= 1.23f;
        }

        return new EquipmentStats(
                ATTACK_STAB,
                ATTACK_SLASH,
                ATTACH_CRUSH,
                ATTACK_MAGIC,
                ATTACK_RANGE,
                DEFENCE_STAB,
                DEFENCE_SLASH,
                DEFENCE_CRUSH,
                DEFENCE_MAGIC,
                DEFENCE_RANGE,
                OTHER_MELEE_STRENGTH,
                OTHER_RANGED_STRENGTH,
                OTHER_MAGIC_DAMAGE,
                stats.OTHER_PRAYER,
                stats.WEAPON_SPEED);
    }

    public int getAttackStyleStatBonus(WeaponData style, EquipmentStats stats)
    {
        switch (style.getStyleType())
        {
            case NONE:
                return 0;
            case STAB:
                return (int) stats.ATTACK_STAB;
            case SLASH:
                return (int) stats.ATTACK_SLASH;
            case CRUSH:
                return (int) stats.ATTACH_CRUSH;
            case MAGIC:
                return (int) stats.ATTACK_MAGIC;
            case RANGE:
                return (int) stats.ATTACK_RANGE;
            default:
                return 0;
        }
    }

    public int getDefenceStyleStatBonus(WeaponData style, EquipmentStats stats)
    {
        switch (style.getStyleType())
        {
            case NONE:
                return 0;
            case STAB:
                return (int) stats.DEFENCE_STAB;
            case SLASH:
                return (int) stats.DEFENCE_SLASH;
            case CRUSH:
                return (int) stats.DEFENCE_CRUSH;
            case MAGIC:
                return (int) stats.DEFENCE_MAGIC;
            case RANGE:
                return (int) stats.DEFENCE_RANGE;
            default:
                return 0;
        }
    }
}
