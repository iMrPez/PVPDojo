package com.pvpdojo;

import com.pvpdojo.character.SpellData;

public class AttackData
{
    public final Spell spell;
    public final WeaponData weaponData;
    public boolean isSpec;
    public final int specCount;

    public AttackData(Spell spell, WeaponData data, boolean isSpec, int specCount)
    {
        this.spell = spell;
        this.weaponData = data;
        this.isSpec = isSpec;
        this.specCount = specCount;
    }
}
