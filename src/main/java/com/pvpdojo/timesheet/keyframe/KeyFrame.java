package com.pvpdojo.timesheet.keyframe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeyFrame
{
    private KeyFrameType keyFrameType;
    private double tick;


    public double getTick() {
        return tick;
    }

    public KeyFrameType getKeyFrameType() {
        return keyFrameType;
    }

}