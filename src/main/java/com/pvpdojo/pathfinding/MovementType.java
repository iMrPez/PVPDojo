package com.pvpdojo.pathfinding;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MovementType
{
    NORMAL("Normal"),
    WATERBORNE("Waterborne"),
    GHOST("Ghost")
    ;

    String name;

    @Override
    public String toString()
    {
        return name;
    }
}