package com.pvpdojo.orientation;

import com.pvpdojo.timesheet.keyframe.KeyFrameType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrientationInstruction
{
    private KeyFrameType type;
    private boolean setOrientation;

    public KeyFrameType getType() {
        return type;
    }

    public boolean isSetOrientation() {
        return setOrientation;
    }
}