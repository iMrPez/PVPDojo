package com.pvpdojo.character.datatypes;

import com.pvpdojo.combat.CombatStyle;
import com.pvpdojo.combat.weapon.WeaponStyleControl;
import com.pvpdojo.combat.weapon.WeaponStyleType;
import com.pvpdojo.combat.weapon.WeaponUtility;
import net.runelite.api.Skill;

public class WeaponData
{
    private final int weaponID;
    private final int weaponType;
    private final String weaponTypeName;
    private final WeaponStyleControl styleControl;
    private final WeaponStyleType styleType;
    private final int styleIndex;
    private final String styleName;
    private final boolean isRapid;

    public WeaponData(int weaponID, int weaponType, String weaponTypeName, WeaponStyleControl styleControl, WeaponStyleType styleType, int styleIndex, String styleName, boolean isRapid) {
        this.weaponID = weaponID;
        this.weaponType = weaponType;
        this.weaponTypeName = weaponTypeName;
        this.styleControl = styleControl;
        this.styleType = styleType;
        this.styleIndex = styleIndex;
        this.styleName = styleName;
        this.isRapid = isRapid;
    }

    public int getWeaponID() { return weaponID; }

    public int getWeaponType() {
        return weaponType;
    }

    public String getWeaponTypeName() {
        return weaponTypeName;
    }

    public WeaponStyleControl getStyleControl() {
        return styleControl;
    }

    public WeaponStyleType getStyleType()
    {
        return styleType;
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public String getStyleName() {
        return styleName;
    }

    public boolean isRapid() {
        return isRapid;
    }

    public int getStyleBonus(Skill skill)
    {
        switch (styleControl)
        {
            case MELEE_ACCURATE:
                if (skill == Skill.ATTACK) return 3;
            case MELEE_AGGRESSIVE:
                if (skill == Skill.STRENGTH) return 3;
            case MELEE_DEFENSIVE:
                if (skill == Skill.DEFENCE) return 3;
            case MELEE_CONTROLLED:
                if (skill == Skill.ATTACK) return 1;
                if (skill == Skill.STRENGTH) return 1;
                if (skill == Skill.DEFENCE) return 1;
            case RANGE_ACCURATE:
                if (skill == Skill.RANGED) return 3;
            case RANGE_RAPID:
                return 0;
            case RANGE_LONGRANGE:
                if (skill == Skill.DEFENCE) return 3;
            case MAGIC_ACCURATE:
                if (skill == Skill.MAGIC) return 3;
            case MAGIC_LONGRANGE:
                if (skill == Skill.MAGIC) return 1;
                if (skill == Skill.DEFENCE) return 3;
            case MAGIC_AUTOCAST:
                return 0;
            default:
                return 0;
        }
    }

    public CombatStyle getWeaponCombatStyle()
    {
        switch (getStyleType())
        {
            case NONE:
            case STAB:
            case SLASH:
            case CRUSH:
                return CombatStyle.MELEE;
            case MAGIC:
                return CombatStyle.MAGIC;
            case RANGE:
                return CombatStyle.RANGE;
        }

        return CombatStyle.MELEE;
    }

    public boolean getCanSpec()
    {
        var weapon = WeaponUtility.getWeapon(weaponID);
        return (weapon != null && weapon.SpecialAttackData != null);
    }

    public SpecialAttackData getSpecData()
    {
        var weapon = WeaponUtility.getWeapon(weaponID);
        if (weapon != null) return weapon.SpecialAttackData;
        return null;
    }

    public int getWeaponAttackSound()
    {
        var weapon = WeaponUtility.getWeapon(weaponID);
        if (weapon != null) return weapon.AttackSound;
        return 2566;
    }

    public int getWeaponSpecSound()
    {
        var weapon = WeaponUtility.getWeapon(weaponID);
        if (weapon != null && weapon.SpecialAttackData != null) return weapon.SpecialAttackData.specialAttackSound;
        return 2566;
    }

    public boolean getIsInstantHit()
    {

        if (getCanSpec())
        {
            return getSpecData().instantHit;
        }

        return false;
    }
}
