package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnimData
{
    private final int id;
    private final String name;

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
}