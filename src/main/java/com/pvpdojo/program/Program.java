package com.pvpdojo.program;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class Program
{
    private ProgramComp comp;
    private Color color;

    public ProgramComp getComp() {
        return comp;
    }

    public Color getColor() {
        return color;
    }
}