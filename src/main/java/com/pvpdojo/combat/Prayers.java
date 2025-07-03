package com.pvpdojo.combat;

import net.runelite.api.Prayer;

import java.util.List;

public enum Prayers
{
    THICK_SKIN("Thick Skin", Prayer.THICK_SKIN, 541, 9, 2690,
            List.of(Prayer.ROCK_SKIN, Prayer.STEEL_SKIN, Prayer.CHIVALRY, Prayer.PIETY)),

    BURST_OF_STRENGTH("Burst of Strength", Prayer.BURST_OF_STRENGTH, 541, 10, 2688,
            List.of(Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    CLARITY_OF_THOUGHT("Clarity of Thought", Prayer.CLARITY_OF_THOUGHT, 541, 11, 2664,
            List.of(Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    SHARP_EYE("Sharp Eye", Prayer.SHARP_EYE, 541, 27, 2663,
            List.of(Prayer.HAWK_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    MYSTIC_WILL("Mystic Will", Prayer.MYSTIC_WILL, 541, 30, 2670,
    List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
            Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
            Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
            Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    ROCK_SKIN("Rock Skin", Prayer.ROCK_SKIN, 541, 12, 2684,
            List.of(Prayer.THICK_SKIN, Prayer.STEEL_SKIN, Prayer.CHIVALRY, Prayer.PIETY)),

    SUPERHUMAN_STRENGTH("Superhuman Strength", Prayer.SUPERHUMAN_STRENGTH, 541, 13, 2689,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    IMPROVED_REFLEXES("Improved Reflexes", Prayer.IMPROVED_REFLEXES, 541, 14, 2662,
            List.of(Prayer.CLARITY_OF_THOUGHT, Prayer.INCREDIBLE_REFLEXES, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    RAPID_RESTORE("Rapid Restore", Prayer.RAPID_RESTORE, 541, 15, 2679,
            List.of()),
    RAPID_HEAL("Rapid Heal", Prayer.RAPID_HEAL, 541, 16, 2678,
            List.of()),
    PROTECT_ITEM("Protect Item", Prayer.PROTECT_ITEM, 541, 17, 1982,
            List.of()),

    HAWK_EYE("Hawk Eye", Prayer.HAWK_EYE, 541, 28, 2666,
            List.of(Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    MYSTIC_LORE("Mystic Lore", Prayer.MYSTIC_LORE, 541, 31, 2668,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),


    STEEL_SKIN("Steel Skin", Prayer.STEEL_SKIN, 541, 18, 2687,
            List.of(Prayer.THICK_SKIN, Prayer.ROCK_SKIN, Prayer.CHIVALRY, Prayer.PIETY)),

    ULTIMATE_STRENGTH("Ultimate Strength", Prayer.ULTIMATE_STRENGTH, 541, 19, 2691,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    INCREDIBLE_REFLEXES("Incredible Reflexes", Prayer.INCREDIBLE_REFLEXES, 541, 20, 2667,
            List.of(Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    PROTECT_FROM_MAGIC("Protect from Magic", Prayer.PROTECT_FROM_MAGIC, 541, 21, 2675,
            List.of(Prayer.RETRIBUTION, Prayer.REDEMPTION, Prayer.SMITE,
                    Prayer.PROTECT_FROM_MELEE, Prayer.PROTECT_FROM_MISSILES)),

    PROTECT_FROM_MISSILES("Protect from Missiles", Prayer.PROTECT_FROM_MISSILES, 541, 22, 2677,
            List.of(Prayer.RETRIBUTION, Prayer.REDEMPTION, Prayer.SMITE,
                    Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MELEE)),

    PROTECT_FROM_MELEE("Protect from Melee", Prayer.PROTECT_FROM_MELEE, 541, 23, 2676,
            List.of(Prayer.RETRIBUTION, Prayer.REDEMPTION, Prayer.SMITE,
                    Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES)),

    /*EAGLE_EYE("Eagle Eye", Prayer.EAGLE_EYE, 541, 0),
    MYSTIC_MIGHT("Mystic Might", Prayer.MYSTIC_MIGHT, 541, 0),*/
    RETRIBUTION("Retribution", Prayer.RETRIBUTION, 541, 24, 2682,
            List.of(Prayer.REDEMPTION, Prayer.SMITE,
                    Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES, Prayer.PROTECT_FROM_MELEE)),

    REDEMPTION("Redemption", Prayer.REDEMPTION, 541, 25, 2680,
            List.of(Prayer.RETRIBUTION, Prayer.SMITE,
                    Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES, Prayer.PROTECT_FROM_MELEE)),

    SMITE("Smite", Prayer.SMITE, 541, 26, 2686,
            List.of(Prayer.RETRIBUTION, Prayer.REDEMPTION,
                    Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES, Prayer.PROTECT_FROM_MELEE)),

    PRESERVE("Preserve", Prayer.PRESERVE, 541, 37, 3826,
            List.of()), // TODO FIND PRESERVE SOUND

    CHIVALRY("Chivalry", Prayer.CHIVALRY, 541, 34, 3826,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    DEADEYE("Deadeye", Prayer.DEADEYE, 541, 29, 10194,
            List.of(Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    MYSTIC_VIGOUR("Mystic Vigour", Prayer.MYSTIC_VIGOUR, 541, 32, 10100,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.AUGURY)),

    PIETY("Piety", Prayer.PIETY, 541, 35, 3825,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)),

    RIGOUR("Rigour", Prayer.RIGOUR, 541, 33, 3825,
            List.of(Prayer.SHARP_EYE, Prayer.HAWK_EYE, Prayer.DEADEYE,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR, Prayer.AUGURY)), // TODO FIND RIGOUR SOUND

    AUGURY("Augury", Prayer.AUGURY, 541, 36, 3825,
            List.of(Prayer.HAWK_EYE, Prayer.SHARP_EYE, Prayer.DEADEYE, Prayer.RIGOUR,
                    Prayer.BURST_OF_STRENGTH, Prayer.SUPERHUMAN_STRENGTH, Prayer.ULTIMATE_STRENGTH, Prayer.CHIVALRY, Prayer.PIETY,
                    Prayer.CLARITY_OF_THOUGHT, Prayer.IMPROVED_REFLEXES, Prayer.INCREDIBLE_REFLEXES,
                    Prayer.MYSTIC_WILL, Prayer.MYSTIC_LORE, Prayer.MYSTIC_VIGOUR)); // TODO FIND AUGURY SOUND

    public final String prayerName;
    public final Prayer prayer;
    public final int groupId;
    public final int childId;
    public final int soundID;
    public final List<Prayer> invalidPrayers;

    Prayers(String prayerName, Prayer prayer, int groupId, int childId, int soundID, List<Prayer> invalidPrayers)
    {
        this.prayerName = prayerName;
        this.prayer = prayer;
        this.groupId = groupId;
        this.childId = childId;
        this.soundID = soundID;
        this.invalidPrayers = invalidPrayers;
    }

    public static Prayers getPrayer(String prayerName)
    {
        for (Prayers prayer : values())
        {
            if (prayerName.contains(prayer.prayerName)) return prayer;
        }

        return null;
    }

    public static boolean isProtectionPrayer(Prayer prayer)
    {
        switch (prayer) {
            case PROTECT_FROM_MAGIC:
            case PROTECT_FROM_MISSILES:
            case PROTECT_FROM_MELEE:
                return true;
            default:
                return false;
        }
    }
}
