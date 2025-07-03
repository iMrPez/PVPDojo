package com.pvpdojo.timesheet.keyframe;

import com.pvpdojo.timesheet.keyframe.settings.HitsplatSprite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HitsplatKeyFrame extends KeyFrame
{
    public static final double DEFAULT_DURATION = (double) 5 / 3;

    private int duration;
    private HitsplatSprite sprite;
    private int damage;
    private int renderPos;

    public HitsplatKeyFrame(double tick, int renderPos, KeyFrameType hitsplatType, int duration, HitsplatSprite sprite, int damage)
    {
        super(hitsplatType, tick);
        this.duration = duration;
        this.sprite = sprite;
        this.damage = damage;
        this.renderPos = renderPos;
    }

    public int getDuration() {
        return duration;
    }

    public HitsplatSprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public int getRenderPos() {
        return renderPos;
    }
}