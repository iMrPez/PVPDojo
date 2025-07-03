package com.pvpdojo.character;

import com.pvpdojo.Dummy;
import com.pvpdojo.PVPDojoPlugin;
import com.pvpdojo.PVPUtility;
import com.pvpdojo.character.datatypes.PlayerAnimationType;
import com.pvpdojo.character.datatypes.WeaponAnimData;
import com.pvpdojo.timesheet.keyframe.AnimationKeyFrame;
import com.pvpdojo.timesheet.keyframe.KeyFrameType;
import com.pvpdojo.timesheet.keyframe.SpotAnimKeyFrame;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.kit.KitType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.util.ColorUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.*;
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

        AnimationKeyFrame akf = null;
        SpotAnimKeyFrame[] spkfs = new SpotAnimKeyFrame[0];
        if (true/*menuOption == ModelMenuOption.STORE_ADD_ANIMATE*/)
        {
            int itemId = player.getPlayerComposition().getEquipmentId(KitType.WEAPON);

            WeaponAnimData weaponAnim = dataFinder.findWeaponAnimData(itemId);
            if (weaponAnim != null)
            {
                akf = new AnimationKeyFrame(
                        plugin.getCurrentTick(),
                        false,
                        player.getAnimation(),
                        0,
                        false,
                        false,
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.IDLE),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.WALK),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.RUN),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.ROTATE_180),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.ROTATE_RIGHT),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.ROTATE_LEFT),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.IDLE_ROTATE_RIGHT),
                        WeaponAnimData.getAnimation(weaponAnim, PlayerAnimationType.IDLE_ROTATE_LEFT));
            }

            int i = 0;
            for (ActorSpotAnim actorSpotAnim : actorSpotAnims)
            {
                if (i == 2)
                {
                    break;
                }

                KeyFrameType type = i == 0 ? KeyFrameType.SPOTANIM : KeyFrameType.SPOTANIM2;

                SpotAnimKeyFrame spkf = new SpotAnimKeyFrame(
                        plugin.getCurrentTick(),
                        type,
                        actorSpotAnim.getId(),
                        false,
                        actorSpotAnim.getHeight());

                spkfs = ArrayUtils.add(spkfs, spkf);

                i++;
            }
        }

        handleStoreOptions(modelStats, CustomModelType.CACHE_PLAYER, name, colours, true, LightingStyle.ACTOR, player.getOrientation(), animId, player.getWalkAnimation(), akf, spkfs);
    }

    private void handleStoreOptions(ModelStats[] modelStats, CustomModelType customModelType, String name, int[] kitRecolours, boolean player, LightingStyle ls, int orientation, int poseAnimation, int walkAnimation, AnimationKeyFrame keyFrame, SpotAnimKeyFrame[] spkfs)
    {

        /*for (ModelStats modelStat : modelStats) {
            log.info("ModelID: " + modelStat.getModelId());
            log.info("BodyPart: " + modelStat.getBodyPart());
            log.info("resizeX: " + modelStat.getResizeX());
            log.info("resizeY: " + modelStat.getResizeY());
            log.info("resizeZ: " + modelStat.getResizeZ());
            log.info("translateZ: " + modelStat.getTranslateZ());
            log.info("--- Lighting ---");
            log.info("Ambient: " + modelStat.getLighting().getAmbient());
            log.info("Contrast: " + modelStat.getLighting().getContrast());
            log.info("X: " + modelStat.getLighting().getX());
            log.info("Y: " + modelStat.getLighting().getY());
            log.info("Z: " + modelStat.getLighting().getZ());

        }*/

        clientThread.invokeLater(() ->
        {
            Model model = plugin.constructModelFromCache(modelStats, kitRecolours, player, ls, null);
            store(model, modelStats, customModelType, name, kitRecolours, ls, orientation, poseAnimation, walkAnimation, keyFrame, spkfs);
        });
    }

    private void store(Model model, ModelStats[] modelStats, CustomModelType customModelType, String name, int[] kitRecolours, LightingStyle ls, int orientation, int poseAnimation, int walkAnimation, AnimationKeyFrame keyFrame, SpotAnimKeyFrame[] spkfs)
    {
        CustomLighting lighting = new CustomLighting(ls.getAmbient(), ls.getContrast(), ls.getX(), ls.getY(), ls.getZ());
        CustomModelComp comp = new CustomModelComp(0, customModelType, 7699, modelStats, kitRecolours, null, null, ls, lighting, false, name);
        CustomModel customModel = new CustomModel(model, comp);
        plugin.addCustomModel(customModel);
    }

}
