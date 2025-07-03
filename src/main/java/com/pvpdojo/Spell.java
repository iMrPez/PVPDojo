package com.pvpdojo;

import com.pvpdojo.character.SpellData;

public enum Spell
{
    NONE(0, "NONE", 0, 0),
    WIND_STRIKE(8, "Wind Strike", 0, 0),
    WATER_STRIKE(8, "Water Strike", 0, 0),
    EARTH_STRIKE(8, "Earth Strike", 0, 0),
    FIRE_STRIKE(8, "Fire Strike", 0, 0),
    WIND_BOLT(12, "Wind Bolt", 0, 0),
    WATER_BOLT(12, "Water Bolt", 0, 0),
    EARTH_BOLT(12, "Earth Bolt", 0, 0),
    FIRE_BOLT(12, "Fire Bolt", 0, 0),
    WIND_BLAST(16, "Wind Blast", 0, 0),
    WATER_BLAST(16, "Water Blast", 0, 0),
    EARTH_BLAST(16, "Earth Blast", 0, 0),
    FIRE_BLAST(16, "Fire Blast", 0, 0),
    SARADOMIN_STRIKE(20, "Saradomin Strike", 0, 0),
    CLAWS_OF_GUTHIX(20, "Claws of Guthix", 0, 0),
    FLAMES_OF_ZAMORAK(20, "Flames of Zamorak", 0, 0),
    WIND_WAVE(20, "Wind Wave", 0, 0),
    WATER_WAVE(20, "Water Wave", 0, 0),
    EARTH_WAVE(20, "Earth Wave", 0, 0),
    FIRE_WAVE(20, "Fire Wave", 0, 0),
    WIND_SURGE(24, "Wind Surge", 0, 0),
    WATER_SURGE(24, "Water Surge", 0, 0),
    EARTH_SURGE(24, "Earth Surge", 0, 0),
    FIRE_SURGE(24, "Fire Surge", 0, 0),
    SMOKE_RUSH(13, "Smoke Rush", 0, 0),
    SHADOW_RUSH(14, "Shadow Rush", 0, 0),
    BLOOD_RUSH(15, "Blood Rush", 0, 0),
    ICE_RUSH(16, "Ice Rush", 0, 0),
    SMOKE_BURST(17, "Smoke Burst", 0, 0),
    SHADOW_BURST(18, "Shadow Burst", 0, 0),
    BLOOD_BURST(21, "Blood Burst", 0, 0),
    ICE_BURST(22, "Ice Burst", 0, 0),
    SMOKE_BLITZ(23, "Smoke Blitz", 0, 0),
    SHADOW_BLITZ(24, "Shadow Blitz", 0, 0),
    BLOOD_BLITZ(25, "Blood Blitz", 0, 0),
    ICE_BLITZ(26, "Ice Blitz", 0, 0),
    SMOKE_BARRAGE(27, "Smoke Barrage", 0, 0),
    SHADOW_BARRAGE(28, "Shadow Barrage", 0, 0),
    BLOOD_BARRAGE(29, "Blood Barrage", 0, 0),
    ICE_BARRAGE(30, "Ice Barrage", 10092, 171);

    public final int Damage;
    public final String SpellName;
    public final int SpellAnim;
    public final int SpellSoundID;


    Spell(int damage, String spellName, int spellAnim, int spellSoundID)
    {
        Damage = damage;
        SpellName = spellName;
        SpellAnim = spellAnim;
        SpellSoundID = spellSoundID;
    }


    public boolean doesFreeze()
    {
        return this == Spell.ICE_BARRAGE ||
                this == Spell.ICE_BLITZ;
    }
}