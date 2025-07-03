package com.pvpdojo;

import com.pvpdojo.orientation.OrientationAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.coords.LocalPoint;

@AllArgsConstructor
@Getter
@Setter
public class MovementComposition
{
    private boolean moving;
    private LocalPoint localPoint;
    private OrientationAction orientationAction;
    private int orientationGoal;
    private int orientationToSet;

    public boolean isMoving() {
        return moving;
    }

    public int getOrientationGoal() {
        return orientationGoal;
    }

    public void setOrientationGoal(int orientationGoal) {
        this.orientationGoal = orientationGoal;
    }

    public void setOrientationAction(OrientationAction orientationAction) {
        this.orientationAction = orientationAction;
    }

    public void setOrientationToSet(int orientationToSet) {
        this.orientationToSet = orientationToSet;
    }

    public int getOrientationToSet() {
        return orientationToSet;
    }

    public LocalPoint getLocalPoint() {
        return localPoint;
    }
}