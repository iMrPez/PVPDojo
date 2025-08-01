package com.pvpdojo.character;


import net.runelite.api.ModelData;
import org.apache.commons.lang3.ArrayUtils;


public class KitRecolourer
{
    public static final short[] BODY_COLOURS_1_SOURCE = new short[]{
            6798, 8741, 25238, 4626, 4550
    };
    public static final short[][] BODY_COLOURS_1_DEST = new short[][]{
            {6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010, -22122, 937, 8130, -13422, 30385},
            {8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010},
            {25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010},
            {4626, 11146, 6439, 12, 4758, 10270},
            {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 17050, 0, 127, -31821, -17991}
    };
    public static final short[] BODY_COLOURS_2_SOURCE = new short[]{
            -10304, 9104, -1, -1, -1
    };
    public static final short[][] BODY_COLOURS_2_DEST = new short[][]{
            {6554, 115, 10304, 28, 5702, 7756, 5681, 4510, -31835, 22437, 2859, -11339, 16, 5157, 10446, 3658, -27314, -21965, 472, 580, 784, 21966, 28950, -15697, -14002, -22116, 945, 8144, -13414, 30389},
            {9104, 10275, 7595, 3610, 7975, 8526, 918, -26734, 24466, 10145, -6882, 5027, 1457, 16565, -30545, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460, -14019},
            new short[0],
            new short[0],
            new short[0]
    };

                        /*
						0 = hair, jaw
						1 = torso, arms
						2 = legs
						3 = Feet
						4 = Hands
						 */

    public static ModelData recolourKitModel(ModelData modelData, BodyPart bodyPart, int[] kitRecolours)
    {
        switch (bodyPart)
        {
            default:
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case HAIR:
            case JAW:
                modelData.recolor(BODY_COLOURS_1_SOURCE[0], BODY_COLOURS_1_DEST[0][kitRecolours[0]]);
                modelData.recolor(BODY_COLOURS_2_SOURCE[0], BODY_COLOURS_2_DEST[0][kitRecolours[0]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case TORSO:
            case ARMS:
                modelData.recolor(BODY_COLOURS_1_SOURCE[1], BODY_COLOURS_1_DEST[1][kitRecolours[1]]);
                modelData.recolor(BODY_COLOURS_2_SOURCE[1], BODY_COLOURS_2_DEST[1][kitRecolours[1]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case LEGS:
                modelData.recolor(BODY_COLOURS_1_SOURCE[2], BODY_COLOURS_1_DEST[2][kitRecolours[2]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[3], BODY_COLOURS_1_DEST[3][kitRecolours[3]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case FEET:
                modelData.recolor(BODY_COLOURS_1_SOURCE[3], BODY_COLOURS_1_DEST[3][kitRecolours[3]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case HANDS:
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
        }
        return modelData;
    }

    public static ModelData recolourKitModel(ModelData modelData, ModelStats modelStats)
    {
        var kitRecolours = modelStats.getKitRecolours();
        switch (modelStats.getBodyPart())
        {
            default:
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case HAIR:
            case JAW:
                modelData.recolor(BODY_COLOURS_1_SOURCE[0], BODY_COLOURS_1_DEST[0][kitRecolours[0]]);
                modelData.recolor(BODY_COLOURS_2_SOURCE[0], BODY_COLOURS_2_DEST[0][kitRecolours[0]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case TORSO:
            case ARMS:
                modelData.recolor(BODY_COLOURS_1_SOURCE[1], BODY_COLOURS_1_DEST[1][kitRecolours[1]]);
                modelData.recolor(BODY_COLOURS_2_SOURCE[1], BODY_COLOURS_2_DEST[1][kitRecolours[1]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case LEGS:
                modelData.recolor(BODY_COLOURS_1_SOURCE[2], BODY_COLOURS_1_DEST[2][kitRecolours[2]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[3], BODY_COLOURS_1_DEST[3][kitRecolours[3]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case FEET:
                modelData.recolor(BODY_COLOURS_1_SOURCE[3], BODY_COLOURS_1_DEST[3][kitRecolours[3]]);
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case HANDS:
                modelData.recolor(BODY_COLOURS_1_SOURCE[4], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
        }
        return modelData;
    }

    public static short[] getKitRecolourFrom(BodyPart bodyPart)
    {
        short[] colourFrom = new short[0];

        switch (bodyPart)
        {
            default:
                colourFrom = ArrayUtils.add(colourFrom, BODY_COLOURS_1_SOURCE[4]);
                break;
            case HAIR:
            case JAW:
                colourFrom = ArrayUtils.addAll(colourFrom, BODY_COLOURS_1_SOURCE[0], BODY_COLOURS_2_SOURCE[0], BODY_COLOURS_1_SOURCE[4]);
                break;
            case TORSO:
            case ARMS:
                colourFrom = ArrayUtils.addAll(colourFrom, BODY_COLOURS_1_SOURCE[1], BODY_COLOURS_2_SOURCE[1], BODY_COLOURS_1_SOURCE[4]);
                break;
            case LEGS:
                colourFrom = ArrayUtils.addAll(colourFrom, BODY_COLOURS_1_SOURCE[2], BODY_COLOURS_1_SOURCE[3], BODY_COLOURS_1_SOURCE[4]);
                break;
            case FEET:
                colourFrom = ArrayUtils.addAll(colourFrom, BODY_COLOURS_1_SOURCE[3], BODY_COLOURS_1_SOURCE[4]);
                break;
            case HANDS:
                colourFrom = ArrayUtils.addAll(colourFrom, BODY_COLOURS_1_SOURCE[4]);
        }

        return colourFrom;
    }

    public static short[] getKitRecolourTo(BodyPart bodyPart, int[] kitRecolours)
    {
        short[] colourTo = new short[0];

        switch (bodyPart)
        {
            default:
                colourTo = ArrayUtils.add(colourTo, BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case HAIR:
            case JAW:
                colourTo = ArrayUtils.addAll(colourTo, BODY_COLOURS_1_DEST[0][kitRecolours[0]], BODY_COLOURS_2_DEST[0][kitRecolours[0]], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case TORSO:
            case ARMS:
                colourTo = ArrayUtils.addAll(colourTo, BODY_COLOURS_1_DEST[1][kitRecolours[1]], BODY_COLOURS_2_DEST[1][kitRecolours[1]], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case LEGS:
                colourTo = ArrayUtils.addAll(colourTo, BODY_COLOURS_1_DEST[2][kitRecolours[2]], BODY_COLOURS_1_DEST[3][kitRecolours[3]], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case FEET:
                colourTo = ArrayUtils.addAll(colourTo, BODY_COLOURS_1_DEST[3][kitRecolours[3]], BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
                break;
            case HANDS:
                colourTo = ArrayUtils.addAll(colourTo, BODY_COLOURS_1_DEST[4][kitRecolours[4]]);
        }

        return colourTo;
    }
}