package com.pvpdojo.combat.weapon;

public class WeaponUtility
{
    public static Weapon getWeapon(int id)
    {
        for (Weapon weapon : Weapon.values())
        {
            if (weapon.ID == id) return weapon;
        }

        return null;
    }
}
