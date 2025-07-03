package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectData
{
    private final int id;
    private final String name;
    private final int animationId;
    private final int[] objectModels;
    private final int[] objectTypes;
    private final int modelSizeX;
    private final int modelSizeY;
    private final int modelSizeZ;
    private final int ambient;
    private final int contrast;
    private final int[] recolorToReplace;
    private final int[] recolorToFind;
    private final int[] textureToReplace;
    private final int[] retextureToFind;

    @Override
    public String toString()
    {
        return name + " (" + id + ")";
    }

    public int[] getRecolorToFind() {
        return recolorToFind;
    }

    public int[] getRecolorToReplace() {
        return recolorToReplace;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAmbient() {
        return ambient;
    }

    public int getAnimationId() {
        return animationId;
    }

    public int getContrast() {
        return contrast;
    }

    public int getModelSizeX() {
        return modelSizeX;
    }

    public int getModelSizeY() {
        return modelSizeY;
    }

    public int getModelSizeZ() {
        return modelSizeZ;
    }

    public int[] getObjectModels() {
        return objectModels;
    }

    public int[] getObjectTypes() {
        return objectTypes;
    }

    public int[] getRetextureToFind() {
        return retextureToFind;
    }

    public int[] getTextureToReplace() {
        return textureToReplace;
    }
}