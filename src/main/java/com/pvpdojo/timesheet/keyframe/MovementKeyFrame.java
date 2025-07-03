package com.pvpdojo.timesheet.keyframe;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementKeyFrame extends KeyFrame
{
    private int plane;
    private boolean poh;
    private int[][] path;
    private int currentStep;
    private int stepClientTick;
    private boolean loop;
    private double speed;
    private int turnRate;

    public MovementKeyFrame(double tick, int plane, boolean poh, int[][] path, int currentStep, int stepClientTick, boolean loop, double speed, int turnRate)
    {
        super(KeyFrameType.MOVEMENT, tick);
        this.plane = plane;
        this.poh = poh;
        this.path = path;
        this.currentStep = currentStep;
        this.stepClientTick = stepClientTick;
        this.loop = loop;
        this.speed = speed;
        this.turnRate = turnRate;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public int getPlane() {
        return plane;
    }

    public int[][] getPath() {
        return path;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public int getStepClientTick() {
        return stepClientTick;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean isPoh() {
        return poh;
    }

    public double getSpeed() {
        return speed;
    }

    public int getTurnRate() {
        return turnRate;
    }
}