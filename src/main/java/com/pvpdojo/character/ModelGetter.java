package com.pvpdojo.character;

import com.pvpdojo.PVPDojoPlugin;
import com.pvpdojo.character.datatypes.EquipmentData;
import net.runelite.api.*;
import net.runelite.client.callback.ClientThread;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelGetter
{

    private static final Logger log = LoggerFactory.getLogger(ModelGetter.class);
    private final Client client;
    private final ClientThread clientThread;
    private final PVPDojoPlugin plugin;
    private final DataFinder dataFinder;

    @Inject
    public ModelGetter(Client client, ClientThread clientThread, PVPDojoPlugin plugin, DataFinder dataFinder)
    {
        this.client = client;
        this.clientThread = clientThread;
        this.plugin = plugin;
        this.dataFinder = dataFinder;
    }

    public ModelStats[] getModelStat(BodyPart bodyPart)
    {
        var player = client.getLocalPlayer();
        PlayerComposition comp = player.getPlayerComposition();
        final int[] items = comp.getEquipmentIds();
        final int[] colors = comp.getColors().clone();

        int[] fSpotAnims = new int[0];

        int animId = player.getAnimation();
        if (animId == -1)
        {
            animId = player.getPoseAnimation();
        }

        List<ModelStats> bodyPartStats = new ArrayList<>();
        ModelStats[] modelStats = dataFinder.findModelsForPlayer(false, comp.getGender() == 0, items, animId, fSpotAnims);
        for (int i = 0; i < modelStats.length; i++)
        {

            modelStats[i].setKitRecolours(colors);
            if (modelStats[i].getBodyPart().compareTo(bodyPart) == 0)
            {
                bodyPartStats.add(modelStats[i]);
            }
        }

        return listToArray(bodyPartStats, ModelStats.class);
    }

    public static <T> T[] listToArray(List<T> list, Class<T> clazz)
    {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(array);
    }

    public CustomModel createModel(EquipmentData equipmentData)
    {
        var modelStats = equipmentData.getModelStats();
        var ls = LightingStyle.ACTOR;
        Model model = constructModelFromCache(modelStats, true, ls, null);
        CustomLighting lighting = new CustomLighting(ls.getAmbient(), ls.getContrast(), ls.getX(), ls.getY(), ls.getZ());
        CustomModelComp comp = new CustomModelComp(0, CustomModelType.CACHE_PLAYER, 7699, modelStats, null, ls, lighting, false, "Local Player");
        CustomModel customModel = new CustomModel(model, comp);

        return customModel;
    }

    public Model constructModelFromCache(ModelStats[] modelStats, boolean player, LightingStyle ls, CustomLighting cl)
    {
        ModelData md = constructModelDataFromCache(modelStats, player);
        if (ls == LightingStyle.CUSTOM)
        {
            return client.mergeModels(md).light(cl.getAmbient(), cl.getContrast(), cl.getX(), -cl.getZ(), cl.getY());
        }

        return client.mergeModels(md).light(ls.getAmbient(), ls.getContrast(), ls.getX(), -ls.getZ(), ls.getY());
    }

    public ModelData constructModelDataFromCache(ModelStats[] modelStatsArray,  boolean player)
    {
        ModelData[] mds = new ModelData[modelStatsArray.length];

        for (int i = 0; i < modelStatsArray.length; i++)
        {
            ModelStats modelStats = modelStatsArray[i];
            if (modelStats == null) continue;

            ModelData modelData = client.loadModelData(modelStats.getModelId());

            if (modelData == null)
                continue;

            modelData.cloneColors().cloneVertices();

            for (short s = 0; s < modelStats.getRecolourFrom().length; s++)
                modelData.recolor(modelStats.getRecolourFrom()[s], modelStats.getRecolourTo()[s]);

            if (player)
                KitRecolourer.recolourKitModel(modelData, modelStats);

            short[] textureFrom = modelStats.getTextureFrom();
            short[] textureTo = modelStats.getTextureTo();

            if (textureFrom == null || textureTo == null)
            {
                modelStats.setTextureFrom(new short[0]);
                modelStats.setTextureTo(new short[0]);
            }

            textureFrom = modelStats.getTextureFrom();
            textureTo = modelStats.getTextureTo();

            if (textureFrom.length > 0 && textureTo.length > 0)
            {
                for (int e = 0; e < textureFrom.length; e++)
                {
                    modelData.retexture(textureFrom[e], textureTo[e]);
                }
            }

            if (modelStats.getResizeX() == 0 && modelStats.getResizeY() == 0 && modelStats.getResizeZ() == 0)
            {
                modelStats.setResizeX(128);
                modelStats.setResizeY(128);
                modelStats.setResizeZ(128);
            }

            modelData.scale(modelStats.getResizeX(), modelStats.getResizeZ(), modelStats.getResizeY());

            modelData.translate(0, -1 * modelStats.getTranslateZ(), 0);

            mds[i] = modelData;
        }

        return client.mergeModels(mds);
    }
}
