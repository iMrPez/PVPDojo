package com.pvpdojo.program;

import com.pvpdojo.character.datatypes.Coordinate;
import com.pvpdojo.pathfinding.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

@AllArgsConstructor
@Getter
@Setter
public class ProgramComp
{
    private WorldPoint[] stepsWP;
    private WorldPoint[] pathWP;
    private LocalPoint[] stepsLP;
    private LocalPoint[] pathLP;
    private Coordinate[] coordinates;
    private boolean pathFound;
    private int currentStep;
    private double speed;
    private int turnSpeed;
    private int idleAnim;
    private int walkAnim;
    private MovementType movementType;
    private int rgb;
    private boolean loop;
    private boolean programActive;

    public boolean isLoop() {
        return loop;
    }

    public double getSpeed() {
        return speed;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public boolean isPathFound() {
        return pathFound;
    }

    public boolean isProgramActive() {
        return programActive;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public int getIdleAnim() {
        return idleAnim;
    }

    public int getRgb() {
        return rgb;
    }

    public int getTurnSpeed() {
        return turnSpeed;
    }

    public int getWalkAnim() {
        return walkAnim;
    }

    public LocalPoint[] getPathLP() {
        return pathLP;
    }

    public LocalPoint[] getStepsLP() {
        return stepsLP;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public void setIdleAnim(int idleAnim) {
        this.idleAnim = idleAnim;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public void setPathFound(boolean pathFound) {
        this.pathFound = pathFound;
    }

    public void setPathLP(LocalPoint[] pathLP) {
        this.pathLP = pathLP;
    }

    public void setPathWP(WorldPoint[] pathWP) {
        this.pathWP = pathWP;
    }

    public void setProgramActive(boolean programActive) {
        this.programActive = programActive;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setStepsLP(LocalPoint[] stepsLP) {
        this.stepsLP = stepsLP;
    }

    public void setStepsWP(WorldPoint[] stepsWP) {
        this.stepsWP = stepsWP;
    }

    public void setTurnSpeed(int turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    public void setWalkAnim(int walkAnim) {
        this.walkAnim = walkAnim;
    }

    public WorldPoint[] getPathWP() {
        return pathWP;
    }

    public WorldPoint[] getStepsWP() {
        return stepsWP;
    }

}