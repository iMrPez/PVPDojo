package com.pvpdojo.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class ModelStats
{
    private static final Logger log = LoggerFactory.getLogger(ModelStats.class);
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
    private int[] kitRecolours;


    public ModelStats(int modelId, BodyPart bodyPart, short[] recolourFrom, short[] recolourTo, short[] textureFrom, short[] textureTo, int resizeX, int resizeY, int resizeZ, int translateZ, CustomLighting lighting, int[] kitRecolours)
    {
        this.modelId = modelId;
        this.bodyPart = bodyPart;
        this.recolourFrom = recolourFrom;
        this.recolourTo = recolourTo;
        this.textureFrom = textureFrom;
        this.textureTo = textureTo;
        this.resizeX = resizeX;
        this.resizeY = resizeY;
        this.resizeZ = resizeZ;
        this.translateZ = translateZ;
        this.lighting = lighting;
        this.kitRecolours = kitRecolours;
    }

    public ModelStats(int modelId, BodyPart bodyPart, short[] recolourFrom, short[] recolourTo, short[] textureFrom, short[] textureTo, int resizeX, int resizeY, int resizeZ, int translateZ, CustomLighting lighting)
    {
        this.modelId = modelId;
        this.bodyPart = bodyPart;
        this.recolourFrom = recolourFrom;
        this.recolourTo = recolourTo;
        this.textureFrom = textureFrom;
        this.textureTo = textureTo;
        this.resizeX = resizeX;
        this.resizeY = resizeY;
        this.resizeZ = resizeZ;
        this.translateZ = translateZ;
        this.lighting = lighting;
    }

    @Override
    public String toString() {
        return "\nModelID: " + modelId +
                "\nBodyPart: " + bodyPart.name() +
                "\nrecolourFrom: " + arrayToText(recolourFrom) +
                "\nrecolourTo: " + arrayToText(recolourTo) +
                "\ntextureFrom: " + arrayToText(textureFrom) +
                "\ntextureTo: " + arrayToText(textureTo) +
                "\nresizeX: " + resizeX +
                "\nresizeY: " + resizeY +
                "\nresizeZ: " + resizeZ +
                "\ntranslateZ: " + translateZ;
    }

    public String arrayToText(short[] array)
    {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < array.length; i++)
        {
            log.info("ADDING: " + array[i]);
            values.append("(").append(array[i]).append("), ");
        }

        return values.toString();
    }

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

    public int[] getKitRecolours() {
        return kitRecolours;
    }

    public void setKitRecolours(int[] kitRecolours) {
        this.kitRecolours = kitRecolours;
    }
}