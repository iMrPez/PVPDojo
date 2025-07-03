package com.pvpdojo.character;

import com.pvpdojo.Spell;

public class SpellData
{
    public final Spell spell;
    public final int damage;
    public final String spellName;

    public SpellData(Spell spell, int damage, String spellName) {
        this.spell = spell;
        this.damage = damage;
        this.spellName = spellName;
    }

    public boolean doesFreeze()
    {
        if (spell != null)
        {
            if (spell == Spell.ICE_BARRAGE ||
                spell == Spell.ICE_BLITZ)
            {
                return true;
            }
        }

        return true;
    }
}
