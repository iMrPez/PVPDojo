package com.pvpdojo.character;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CustomModelType
{
    FORGED("Forged"),
    CACHE_NPC("NPC"),
    CACHE_OBJECT("Object"),
    CACHE_PLAYER("Player"),
    CACHE_GROUND_ITEM("Ground Item"),
    CACHE_MAN_WEAR("Male Worn Item"),
    CACHE_WOMAN_WEAR("Female Worn Item"),
    CACHE_SPOTANIM("SpotAnim");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}