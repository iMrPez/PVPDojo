package com.pvpdojo.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.callback.ClientThread;
import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class Character
{
    private String name;
    private boolean active;
    private boolean locationSet;
    private Color color;
    private WorldPoint nonInstancedPoint;
    private LocalPoint instancedPoint;
    private int[] instancedRegions;
    private int instancedPlane;
    private boolean inInstance;
    private CharacterObject characterObject;
    private int targetOrientation;


    @Override
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isLocationSet() {
        return locationSet;
    }

    public Color getColor() {
        return color;
    }

    public CharacterObject getCharacterObject() {
        return characterObject;
    }

    public LocalPoint getInstancedPoint() {
        return instancedPoint;
    }

    public int getInstancedPlane() {
        return instancedPlane;
    }

    public int[] getInstancedRegions() {
        return instancedRegions;
    }

    public WorldPoint getNonInstancedPoint() {
        return nonInstancedPoint;
    }

    public int getTargetOrientation() {
        return targetOrientation;
    }

    public boolean isInInstance() {
        return inInstance;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCharacterObject(CharacterObject characterObject) {
        this.characterObject = characterObject;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void setInInstance(boolean inInstance) {
        this.inInstance = inInstance;
    }

    public void setInstancedPlane(int instancedPlane) {
        this.instancedPlane = instancedPlane;
    }

    public void setInstancedPoint(LocalPoint instancedPoint) {
        this.instancedPoint = instancedPoint;
    }

    public void setInstancedRegions(int[] instancedRegions) {
        this.instancedRegions = instancedRegions;
    }

    public void setLocationSet(boolean locationSet) {
        this.locationSet = locationSet;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setNonInstancedPoint(WorldPoint nonInstancedPoint) {
        this.nonInstancedPoint = nonInstancedPoint;
    }

    public void setTargetOrientation(int targetOrientation) {
        this.targetOrientation = targetOrientation;
    }


    public void setActive(boolean setActive, boolean reset, ClientThread clientThread)
    {
        clientThread.invokeLater(() ->
        {
            if (setActive)
            {
                active = true;
                if (reset)
                {
                    characterObject.setActive(false);
                }
                characterObject.setActive(true);
                return;
            }

            active = false;
            characterObject.setActive(false);
        });
    }
}