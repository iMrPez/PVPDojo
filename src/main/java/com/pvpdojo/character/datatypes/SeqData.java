package com.pvpdojo.character.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeqData
{
    private final String name;
    private final int id;
    private final int leftHandItem;
    private final int rightHandItem;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getLeftHandItem() {
        return leftHandItem;
    }

    public int getRightHandItem() {
        return rightHandItem;
    }
}