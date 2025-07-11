package com.pvpdojo.combat.weapon;

import com.pvpdojo.character.datatypes.SpecialAttackData;

public enum Weapon
{
    // Non-Special Attack Weapons
    BOW_OF_FAERDHINEN_C(25867,"Bow of faerdhinen (c)",  1352, 513, null,
            new WeaponAnimationData(808, 819, 824, -1, 426, 426, 426, 426, 424)),

    RUNE_CROSSBOW(9185,"Rune crossbow",  2695, 513, null,
            new WeaponAnimationData(4591, 4226, 4228, -1, 7552, 7552, 7552, 7552, 4177)),

    ANCIENT_STAFF(4675,"Ancient staff",  2555, 513, null,
            new WeaponAnimationData(813, 1205, 1210, -1, 414, 414, 414, 414, 415)),

    AHRIMS_STAFF_100(4862, "Ahrim's staff 100", 1329, 513, null,
            new WeaponAnimationData(813, 1205, 1210, -1, 2078, 2078, 2078, 2078, 4177)),


    // Special Attack Weapons
    OSMUMTENS_FANG(26219,"Osmumten's fang",  2549, 513,
            new SpecialAttackData(25, 1f, 1.50f, 1, false, 9366),
            null),

    DRAGON_SCIMITAR(4587,"Dragon Scimitar",  2500, 513,
            new SpecialAttackData(55, 1f, 1.25f, 1, false, 2540),
            new WeaponAnimationData(808, 819, 824,1872, 390, 390, 386, 390, 4177)),

    ABYSSAL_WHIP(4151,"Abyssal Whip",  2720, 513,
            new SpecialAttackData(50, 1f, 1.25f, 2, false, 2713),
            new WeaponAnimationData(808, 1660, 1661, 1658, 1658, 1658, 1658, 1658, 1659)),

    MAGIC_SHORTBOW_IMBUED(12788,"Magic shortbow (i)",  2693, 513,
            new SpecialAttackData(50, 1f, 1.43f, 2, false, 2545),
            null),

    MAGIC_SHORTBOW(861,"Magic shortbow",  2693, 513,
            new SpecialAttackData(55, 1f, 1.43f, 2, false, 2545),
            null),

    GRANITE_MAUL(4153,"Granite Maul",  2714, 513,
            new SpecialAttackData(50, 1f, 1f, 1, true, 2715),
            new WeaponAnimationData(1662, 1663, 1664, 1667, 1665, 1665, 1665, 1665, 1666)),

    BANDOS_GODSWORD(11804, "Bandos Godsword", 3847, 513,
            new SpecialAttackData(50, 1.21f, 2.0f, 1, false, -1),
            null),

    DRAGON_DAGGER(1215,"Dragon Dagger",  2549, 513,
            new SpecialAttackData(25, 1.15f, 1.15f, 2, false, 1979),
            new WeaponAnimationData(808, 819, 824, 1062, 376, 376, 377, 376, 378)),

    DRAGON_DAGGER_P(1231,"Dragon Dagger (p)",  2549, 513,
            new SpecialAttackData(25, 1.15f, 1.15f, 2, false, 1979),
            new WeaponAnimationData(808, 819, 824, 1062, 376, 376, 377, 376, 378));

    public final int ID;
    public final String WeaponName;
    public final int AttackSound;
    public final int HitSound;

    public final SpecialAttackData SpecialAttackData;
    public final WeaponAnimationData WeaponAnimationData;

    Weapon(int id, String weaponName, int attackSound, int hitSound, SpecialAttackData specialAttackData, WeaponAnimationData weaponAnimationData)
    {
        ID = id;
        WeaponName = weaponName;
        AttackSound = attackSound;
        HitSound = hitSound;
        SpecialAttackData = specialAttackData;
        WeaponAnimationData = weaponAnimationData;
    }


}

