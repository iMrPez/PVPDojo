package com.pvpdojo.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomLighting
{
    private int ambient;
    private int contrast;
    private int x;
    private int y;
    private int z;

    public int getAmbient() {
        return ambient;
    }

    public int getContrast() {
        return contrast;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setAmbient(int ambient) {
        this.ambient = ambient;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }
}