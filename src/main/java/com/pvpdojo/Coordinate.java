package com.pvpdojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Coordinate
{
    private int column;
    private int row;

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}