package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NPCData
{
    private final int id;
    private String name;
    private final int[] models;
    private final int size;
    private final int standingAnimation;
    private final int walkingAnimation;
    private final int runAnimation;
    private final int idleRotateLeftAnimation;
    private final int idleRotateRightAnimation;
    private final int rotate180Animation;
    private final int rotateLeftAnimation;
    private final int rotateRightAnimation;
    private final int widthScale;
    private final int heightScale;
    private final int[] recolorToReplace;
    private final int[] recolorToFind;

    @Override
    public String toString()
    {
        return name + " (" + id + ")";
    }

    public int getId() {
        return id;
    }

    public int[] getRecolorToReplace() {
        return recolorToReplace;
    }

    public int[] getRecolorToFind() {
        return recolorToFind;
    }

    public String getName() {
        return name;
    }

    public int getHeightScale() {
        return heightScale;
    }

    public int getIdleRotateLeftAnimation() {
        return idleRotateLeftAnimation;
    }

    public int[] getModels() {
        return models;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdleRotateRightAnimation() {
        return idleRotateRightAnimation;
    }

    public int getRotate180Animation() {
        return rotate180Animation;
    }

    public int getRotateLeftAnimation() {
        return rotateLeftAnimation;
    }

    public int getRotateRightAnimation() {
        return rotateRightAnimation;
    }

    public int getRunAnimation() {
        return runAnimation;
    }

    public int getSize() {
        return size;
    }

    public int getStandingAnimation() {
        return standingAnimation;
    }

    public int getWalkingAnimation() {
        return walkingAnimation;
    }

    public int getWidthScale() {
        return widthScale;
    }

}