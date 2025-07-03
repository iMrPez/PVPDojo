package com.pvpdojo;

public class WeaponAnimationData 
{
    public final int idleID;
    public final int walkID;
    public final int runID;
    public final int specID;
    public final int style1ID;
    public final int style2ID;
    public final int style3ID;
    public final int style4ID;
    public final int hitID;

    public WeaponAnimationData(int idleID, int walkID, int runID, int specID, int style1ID, int style2ID, int style3ID, int style4ID, int hitID)
    {
        this.idleID = idleID;
        this.walkID = walkID;
        this.runID = runID;
        this.specID = specID;
        this.style1ID = style1ID;
        this.style2ID = style2ID;
        this.style3ID = style3ID;
        this.style4ID = style4ID;
        this.hitID = hitID;
    }

    public int getStyleID(int style)
    {
        switch (style)
        {
            case 0:
                return style1ID;
            case 1:
                return style2ID;
            case 2:
                return style3ID;
            case 3:
                return style4ID;
        }

        return -1;
    }
}
