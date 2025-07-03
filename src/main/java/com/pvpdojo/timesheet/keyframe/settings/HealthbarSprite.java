package com.pvpdojo.timesheet.keyframe.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.SpriteID;

@Getter
@AllArgsConstructor
public enum HealthbarSprite
{
    DEFAULT("Default", 2176, SpriteID.HEALTHBAR_DEFAULT_BACK_30PX),
    ;

    private final String name;
    private final int foregroundSpriteID;
    private final int backgroundSpriteID;

    @Override
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getBackgroundSpriteID() {
        return backgroundSpriteID;
    }

    public int getForegroundSpriteID() {
        return foregroundSpriteID;
    }
}