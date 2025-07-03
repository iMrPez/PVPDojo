package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KitData
{
    private final int id;
    private final int bodyPartId;
    private final int[] models;
    private final int[] chatheadModels;
    private final int[] recolorToReplace;
    private final int[] recolorToFind;

    public int getId() {
        return id;
    }

    public int getBodyPartId() {
        return bodyPartId;
    }

    public int[] getChatheadModels() {
        return chatheadModels;
    }

    public int[] getModels() {
        return models;
    }

    public int[] getRecolorToFind() {
        return recolorToFind;
    }

    public int[] getRecolorToReplace() {
        return recolorToReplace;
    }
}