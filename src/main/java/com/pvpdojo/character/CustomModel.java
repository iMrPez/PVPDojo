package com.pvpdojo.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Model;

@Getter
@Setter
@AllArgsConstructor
public class CustomModel
{
    private Model model;
    private CustomModelComp comp;

    @Override
    public String toString()
    {
        return comp.getName();
    }

    public Model getModel() {
        return model;
    }
}