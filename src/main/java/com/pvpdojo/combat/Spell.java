package com.pvpdojo.combat;

public enum Spell
{
    NONE(0, "NONE", 0, 0),
    WIND_STRIKE(8, "Wind Strike", 2079, 220),
    WATER_STRIKE(8, "Water Strike", 2079, 211),
    EARTH_STRIKE(8, "Earth Strike", 2079, 132),
    FIRE_STRIKE(8, "Fire Strike", 2079, 160),
    WIND_BOLT(12, "Wind Bolt", 11423, 218),
    WATER_BOLT(12, "Water Bolt", 11423, 209),
    EARTH_BOLT(12, "Earth Bolt", 11423, 130),
    FIRE_BOLT(12, "Fire Bolt", 11423, 157),
    WIND_BLAST(16, "Wind Blast", 11423, 216),
    WATER_BLAST(16, "Water Blast", 11423, 207),
    EARTH_BLAST(16, "Earth Blast", 11423, 128),
    FIRE_BLAST(16, "Fire Blast", 11423, 155),
    SARADOMIN_STRIKE(20, "Saradomin Strike", 0, 0),
    CLAWS_OF_GUTHIX(20, "Claws of Guthix", 0, 0),
    FLAMES_OF_ZAMORAK(20, "Flames of Zamorak", 0, 0),
    WIND_WAVE(20, "Wind Wave", 11430, 222),
    WATER_WAVE(20, "Water Wave", 11430, 213),
    EARTH_WAVE(20, "Earth Wave", 11430, 134),
    FIRE_WAVE(20, "Fire Wave", 11430, 162),
    WIND_SURGE(24, "Wind Surge", 9145, 4028),
    WATER_SURGE(24, "Water Surge", 9145, 4030),
    EARTH_SURGE(24, "Earth Surge", 9145, 4025),
    FIRE_SURGE(24, "Fire Surge", 9145, 4032),
    SMOKE_RUSH(13, "Smoke Rush", 10091, 183),
    SHADOW_RUSH(14, "Shadow Rush", 10091, 178),
    BLOOD_RUSH(15, "Blood Rush", 10091, 106),
    ICE_RUSH(16, "Ice Rush", 10091, 171),
    SMOKE_BURST(17, "Smoke Burst", 10092, 183),
    SHADOW_BURST(18, "Shadow Burst", 10092, 178),
    BLOOD_BURST(21, "Blood Burst", 10092, 106),
    ICE_BURST(22, "Ice Burst", 10092, 171),
    SMOKE_BLITZ(23, "Smoke Blitz", 10091, 183),
    SHADOW_BLITZ(24, "Shadow Blitz", 10091, 178),
    BLOOD_BLITZ(25, "Blood Blitz", 10091, 106),
    ICE_BLITZ(26, "Ice Blitz", 10091, 171),
    SMOKE_BARRAGE(27, "Smoke Barrage", 10092, 183),
    SHADOW_BARRAGE(28, "Shadow Barrage", 10092, 178),
    BLOOD_BARRAGE(29, "Blood Barrage", 10092, 106),
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