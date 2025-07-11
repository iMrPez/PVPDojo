package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpotanimData
{
    private final String name;
    private final int id;
    private final int modelId;
    private final int animationId;
    private final int resizeX;
    private final int resizeY;
    private final int ambient;
    private final int contrast;
    private final int[] recolorToReplace;
    private final int[] recolorToFind;

    @Override
    public String toString()
    {
        return name + " (" + id + ")";
    }

    public int getAnimationId() {
        return animationId;
    }

    public int[] getRecolorToFind() {
        return recolorToFind;
    }

    public int getContrast() {
        return contrast;
    }

    public int getAmbient() {
        return ambient;
    }

    public int getId() {
        return id;
    }

    public int[] getRecolorToReplace() {
        return recolorToReplace;
    }

    public String getName() {
        return name;
    }

    public int getResizeY() {
        return resizeY;
    }

    public int getResizeX() {
        return resizeX;
    }

    public int getModelId() {
        return modelId;
    }
}