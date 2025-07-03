package com.pvpdojo;

import com.pvpdojo.character.SpellData;

public class DamageData
{
    public final float[] damages;
    public final CombatStyle combatStyle;
    public final AttackData attackData;
    public final WeaponData weaponData;
    public final boolean isSpec;

    public DamageData(float[] damages, CombatStyle combatStyle, WeaponData weaponData, AttackData attackData)
    {
        this.damages = damages;
        this.combatStyle = combatStyle;
        this.attackData = attackData;
        this.weaponData = weaponData;
        this.isSpec = attackData.isSpec;
    }

    public int getTotalDamage()
    {
        var specMultiplier = getSpecMultiplier();
        float total = 0f;
        for (int i = 0; i < getSpecHitCount(); i++)
        {
            float damage = damages[i];
            total += damage;
        }

        return (int)Math.floor(total * specMultiplier);
    }

    public float getSpecMultiplier()
    {
        if (!isSpec) return 1;

        if (weaponData.getCanSpec())
        {
            return weaponData.getSpecData().damageMultiplier;
        }

        return 1;
    }

    public int getSpecHitCount()
    {
        if (!isSpec) return 1;

        if (weaponData.getCanSpec())
        {
            return weaponData.getSpecData().attackCount;
        }

        return 1;
    }

    public int getNormalHitCount()
    {
        return 1;
    }

}
