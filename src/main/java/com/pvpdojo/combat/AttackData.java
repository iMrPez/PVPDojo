package com.pvpdojo.combat;

import com.pvpdojo.character.datatypes.WeaponData;

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

    @Override
    public String toString() {
        return "AttackData{" +
                "spell=" + spell +
                ", weaponData=" + weaponData +
                ", isSpec=" + isSpec +
                ", specCount=" + specCount +
                '}';
    }
}
