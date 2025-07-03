package com.pvpdojo.timesheet.keyframe.keyframeactions;

import com.pvpdojo.timesheet.keyframe.KeyFrame;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyFrameAction
{
    private KeyFrameActionType actionType;
    private KeyFrame keyFrame;
}