package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemData
{
    private final int id;
    private final String name;
    private final int inventoryModel;
    private final int maleModel0;
    private final int maleModel1;
    private final int maleModel2;
    private final int maleOffset;
    private final int femaleModel0;
    private final int femaleModel1;
    private final int femaleModel2;
    private final int femaleOffset;
    private final int maleHeadModel;
    private final int maleHeadModel2;
    private final int femaleHeadModel;
    private final int femaleHeadModel2;
    private final int resizeX;
    private final int resizeY;
    private final int resizeZ;
    private final int[] colorReplace;
    private final int[] colorFind;
    private final int[] textureReplace;
    private final int[] textureFind;

    @Override
    public String toString()
    {
        return name + " (" + id + ")";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFemaleHeadModel() {
        return femaleHeadModel;
    }

    public int getFemaleHeadModel2() {
        return femaleHeadModel2;
    }

    public int getFemaleModel0() {
        return femaleModel0;
    }

    public int getFemaleModel1() {
        return femaleModel1;
    }

    public int getFemaleModel2() {
        return femaleModel2;
    }

    public int getFemaleOffset() {
        return femaleOffset;
    }

    public int getInventoryModel() {
        return inventoryModel;
    }

    public int getMaleHeadModel() {
        return maleHeadModel;
    }

    public int getMaleHeadModel2() {
        return maleHeadModel2;
    }

    public int getMaleModel0() {
        return maleModel0;
    }

    public int getMaleModel1() {
        return maleModel1;
    }

    public int getMaleModel2() {
        return maleModel2;
    }

    public int getMaleOffset() {
        return maleOffset;
    }

    public int getResizeX() {
        return resizeX;
    }

    public int getResizeY() {
        return resizeY;
    }

    public int getResizeZ() {
        return resizeZ;
    }

    public int[] getColorFind() {
        return colorFind;
    }

    public int[] getColorReplace() {
        return colorReplace;
    }

    public int[] getTextureFind() {
        return textureFind;
    }

    public int[] getTextureReplace() {
        return textureReplace;
    }
}