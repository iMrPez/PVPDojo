package com.pvpdojo.timesheet.keyframe;



import com.pvpdojo.character.CustomModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelKeyFrame extends KeyFrame
{
    private boolean useCustomModel;
    private int modelId;
    private CustomModel customModel;
    private int radius;

    public ModelKeyFrame(double tick, boolean useCustomModel, int modelId, CustomModel customModel, int radius)
    {
        super(KeyFrameType.MODEL, tick);
        this.useCustomModel = useCustomModel;
        this.modelId = modelId;
        this.customModel = customModel;
        this.radius = radius;
    }

    public boolean isUseCustomModel() {
        return useCustomModel;
    }

    public int getModelId() {
        return modelId;
    }

    public CustomModel getCustomModel() {
        return customModel;
    }

    public int getRadius() {
        return radius;
    }
}