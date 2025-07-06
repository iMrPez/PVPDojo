package com.pvpdojo.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomModelComp
{
    private int customModelId;
    private CustomModelType type;
    private int modelId;
    private ModelStats[] modelStats;
    private DetailedModel[] detailedModels;
    private LightingStyle lightingStyle;
    private CustomLighting customLighting;
    private boolean priority;
    private String name;
}