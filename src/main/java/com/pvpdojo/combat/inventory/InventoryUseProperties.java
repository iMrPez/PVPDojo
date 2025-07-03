package com.pvpdojo.combat.inventory;

public class InventoryUseProperties
{
    public final int health;
    public final boolean overheal;
    public final boolean overstat;
    public final int attack;
    public final int strength;
    public final int defence;
    public final int range;
    public final int magic;

    public InventoryUseProperties(int health, boolean overheal, boolean overstat, int attack, int strength, int defence, int range, int magic)
    {
        this.health = health;
        this.overheal = overheal;
        this.overstat = overstat;
        this.attack = attack;
        this.strength = strength;
        this.defence = defence;
        this.range = range;
        this.magic = magic;
    }

}
