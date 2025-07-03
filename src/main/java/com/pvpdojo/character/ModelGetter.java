package com.pvpdojo.character;

import com.pvpdojo.PVPDojoPlugin;
import com.pvpdojo.character.datatypes.PlayerAnimationType;
import com.pvpdojo.character.datatypes.WeaponAnimData;
import com.pvpdojo.timesheet.keyframe.KeyFrameType;
import net.runelite.api.*;
import net.runelite.api.kit.KitType;
import net.runelite.client.callback.ClientThread;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Arrays;

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


    public void storePlayer(Player player, boolean allowSpotAnim)
    {
        PlayerComposition comp = player.getPlayerComposition();
        final int[] items = comp.getEquipmentIds();
        final int[] colours = comp.getColors().clone();

        IterableHashTable<ActorSpotAnim> actorSpotAnims = player.getSpotAnims();
        int[] spotAnims = new int[0];
        for (ActorSpotAnim actorSpotAnim : actorSpotAnims)
        {
            spotAnims = ArrayUtils.add(spotAnims, actorSpotAnim.getId());
        }

        int[] fSpotAnims = new int[0];
        if (allowSpotAnim)
        {
            fSpotAnims = Arrays.copyOf(spotAnims, spotAnims.length);
        }

        int animId = player.getAnimation();
        if (animId == -1)
        {
            animId = player.getPoseAnimation();
        }

        String name = player.getName();
        if (player == client.getLocalPlayer())
        {
            name = "Local Player";
        }

        ModelStats[] modelStats = dataFinder.findModelsForPlayer(false, comp.getGender() == 0, items, animId, fSpotAnims);

        handleStoreOptions(modelStats, CustomModelType.CACHE_PLAYER, name, colours, true, LightingStyle.ACTOR);
    }

    private void handleStoreOptions(ModelStats[] modelStats, CustomModelType customModelType, String name, int[] kitRecolours, boolean player, LightingStyle ls)
    {
        clientThread.invokeLater(() ->
        {
            Model model = plugin.constructModelFromCache(modelStats, kitRecolours, player, ls, null);
            store(model, modelStats, customModelType, name, kitRecolours, ls);
        });
    }

    private void store(Model model, ModelStats[] modelStats, CustomModelType customModelType, String name, int[] kitRecolours, LightingStyle ls)
    {
        CustomLighting lighting = new CustomLighting(ls.getAmbient(), ls.getContrast(), ls.getX(), ls.getY(), ls.getZ());
        CustomModelComp comp = new CustomModelComp(0, customModelType, 7699, modelStats, kitRecolours, null, ls, lighting, false, name);
        CustomModel customModel = new CustomModel(model, comp);
        plugin.addCustomModel(customModel);
    }

}
