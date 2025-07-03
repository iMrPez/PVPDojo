package com.pvpdojo;

public class EquipmentStats
{


    /// ATTACK BONUSES ///
    public final float ATTACK_STAB;
    public final float ATTACK_SLASH;
    public final float ATTACH_CRUSH;
    public final float ATTACK_MAGIC;
    public final float ATTACK_RANGE;

    /// DEFENCE BONUSES ///
    public final float DEFENCE_STAB;
    public final float DEFENCE_SLASH;
    public final float DEFENCE_CRUSH;
    public final float DEFENCE_MAGIC;
    public final float DEFENCE_RANGE;

    /// OTHER BONUSES ///
    public final float OTHER_MELEE_STRENGTH;
    public final float OTHER_RANGED_STRENGTH;
    public final float OTHER_MAGIC_DAMAGE;
    public final float OTHER_PRAYER;

    /// WEAPON SPEED ///
    public final float WEAPON_SPEED;

    public EquipmentStats(float ATTACK_STAB,
                          float ATTACK_SLASH,
                          float ATTACH_CRUSH,
                          float ATTACK_MAGIC,
                          float ATTACK_RANGE,
                          float DEFENCE_STAB,
                          float DEFENCE_SLASH,
                          float DEFENCE_CRUSH,
                          float DEFENCE_MAGIC,
                          float DEFENCE_RANGE,
                          float OTHER_MELEE_STRENGTH,
                          float OTHER_RANGED_STRENGTH,
                          float OTHER_MAGIC_DAMAGE,
                          float OTHER_PRAYER,
                          float WEAPON_SPEED)
    {
        this.ATTACK_STAB = ATTACK_STAB;
        this.ATTACK_SLASH = ATTACK_SLASH;
        this.ATTACH_CRUSH = ATTACH_CRUSH;
        this.ATTACK_MAGIC = ATTACK_MAGIC;
        this.ATTACK_RANGE = ATTACK_RANGE;
        this.DEFENCE_STAB = DEFENCE_STAB;
        this.DEFENCE_SLASH = DEFENCE_SLASH;
        this.DEFENCE_CRUSH = DEFENCE_CRUSH;
        this.DEFENCE_MAGIC = DEFENCE_MAGIC;
        this.DEFENCE_RANGE = DEFENCE_RANGE;
        this.OTHER_MELEE_STRENGTH = OTHER_MELEE_STRENGTH;
        this.OTHER_RANGED_STRENGTH = OTHER_RANGED_STRENGTH;
        this.OTHER_MAGIC_DAMAGE = OTHER_MAGIC_DAMAGE;
        this.OTHER_PRAYER = OTHER_PRAYER;
        this.WEAPON_SPEED = WEAPON_SPEED;
    }
}
