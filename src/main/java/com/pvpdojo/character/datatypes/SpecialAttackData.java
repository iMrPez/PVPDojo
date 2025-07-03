package com.pvpdojo.character.datatypes;

public class SpecialAttackData
{
    public final int energy;
    public final float damageMultiplier;
    public final float accuracyMultiplier;
    public final int attackCount;
    public final boolean instantHit;
    public final int specialAttackSound;


    public SpecialAttackData(int energy, float damageMultiplier, float accuracyMultiplier, int attackCount, boolean instantHit, int specSound)
    {
        this.energy = energy;
        this.damageMultiplier = damageMultiplier;
        this.accuracyMultiplier = accuracyMultiplier;
        this.attackCount = attackCount;
        this.instantHit = instantHit;
        this.specialAttackSound = specSound;
    }
}
