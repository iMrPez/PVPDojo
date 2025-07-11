package com.pvpdojo.character.datatypes;

public class MaxSpecData
{
    public final int damage;
    public final EquipmentItemData weapon;

    public MaxSpecData(int damage, EquipmentItemData weapon)
    {
        this.damage = damage;
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "MaxSpecData{" +
                "damage=" + damage +
                ", weapon=" + weapon +
                '}';
    }
}
