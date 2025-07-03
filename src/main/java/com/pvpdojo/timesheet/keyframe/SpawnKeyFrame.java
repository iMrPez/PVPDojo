package com.pvpdojo.timesheet.keyframe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpawnKeyFrame extends KeyFrame
{
    private boolean spawnActive;

    public SpawnKeyFrame(double tick, boolean spawnActive)
    {
        super(KeyFrameType.SPAWN, tick);
        this.spawnActive = spawnActive;
    }

    public boolean isSpawnActive() {
        return spawnActive;
    }
}