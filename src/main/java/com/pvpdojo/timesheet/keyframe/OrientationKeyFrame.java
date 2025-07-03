package com.pvpdojo.timesheet.keyframe;

import com.pvpdojo.orientation.OrientationGoal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrientationKeyFrame extends KeyFrame
{
    private OrientationGoal goal;
    private int start;
    private int end;
    private double duration;
    private int turnRate;

    public OrientationKeyFrame(double tick, OrientationGoal goal, int start, int end, double duration, int turnRate)
    {
        super(KeyFrameType.ORIENTATION, tick);
        this.goal = goal;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.turnRate = turnRate;
    }

    public OrientationGoal getGoal() {
        return goal;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public double getDuration() {
        return duration;
    }

    public int getTurnRate() {
        return turnRate;
    }
}