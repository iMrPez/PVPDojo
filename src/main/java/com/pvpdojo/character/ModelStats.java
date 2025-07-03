package com.pvpdojo.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ModelStats
{
    private int modelId;
    private BodyPart bodyPart;
    private short[] recolourFrom;
    private short[] recolourTo;
    private short[] textureFrom;
    private short[] textureTo;
    private int resizeX;
    private int resizeY;
    private int resizeZ;
    private int translateZ;
    private CustomLighting lighting;

    public int getModelId() {
        return modelId;
    }

    public int getResizeY() {
        return resizeY;
    }

    public int getResizeX() {
        return resizeX;
    }

    public int getResizeZ() {
        return resizeZ;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public CustomLighting getLighting() {
        return lighting;
    }

    public int getTranslateZ() {
        return translateZ;
    }

    public short[] getRecolourFrom() {
        return recolourFrom;
    }

    public short[] getRecolourTo() {
        return recolourTo;
    }

    public short[] getTextureFrom() {
        return textureFrom;
    }

    public short[] getTextureTo() {
        return textureTo;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setLighting(CustomLighting lighting) {
        this.lighting = lighting;
    }

    public void setRecolourFrom(short[] recolourFrom) {
        this.recolourFrom = recolourFrom;
    }

    public void setRecolourTo(short[] recolourTo) {
        this.recolourTo = recolourTo;
    }

    public void setResizeX(int resizeX) {
        this.resizeX = resizeX;
    }

    public void setResizeY(int resizeY) {
        this.resizeY = resizeY;
    }

    public void setResizeZ(int resizeZ) {
        this.resizeZ = resizeZ;
    }

    public void setTextureFrom(short[] textureFrom) {
        this.textureFrom = textureFrom;
    }

    public void setTextureTo(short[] textureTo) {
        this.textureTo = textureTo;
    }

    public void setTranslateZ(int translateZ) {
        this.translateZ = translateZ;
    }


}