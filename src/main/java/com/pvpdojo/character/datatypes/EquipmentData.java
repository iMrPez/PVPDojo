package com.pvpdojo.character.datatypes;

import com.pvpdojo.character.BodyPart;
import com.pvpdojo.character.CustomLighting;
import com.pvpdojo.character.LightingStyle;
import com.pvpdojo.character.ModelStats;
import com.pvpdojo.combat.equipment.EquipmentStats;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.client.game.ItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipmentData
{
    private static final Logger log = LoggerFactory.getLogger(EquipmentData.class);
    public List<EquipmentItemData> equipmentList;



    public EquipmentData(List<EquipmentItemData> equipmentList)
    {
        this.equipmentList = equipmentList;
    }

    public int[] getEquipmentIDs()
    {
        int[] equipmentIDs = new int[equipmentList.size()];

        for (int i = 0; i < equipmentIDs.length; i++)
        {
            equipmentIDs[i] = equipmentList.get(i).itemID;
        }

        return equipmentIDs;
    }

    public EquipmentItemData getWeapon()
    {
        for (EquipmentItemData equipmentItemData : equipmentList)
        {
            if (equipmentItemData.slot == EquipmentInventorySlot.WEAPON)
            {
                return equipmentItemData;
            }
        }

        return null;
    }

    public ModelStats[] getModelStats()
    {
        List<ModelStats> modelStats = new ArrayList<>();
        for (int i = 0; i < equipmentList.size(); i++)
        {
            EquipmentItemData itemData = equipmentList.get(i);
            if (itemData != null && itemData.modelStats != null)
            {
                modelStats.addAll(Arrays.asList(itemData.modelStats));
            }

        }

        return listToArray(modelStats, ModelStats.class);
    }

    public void replace(EquipmentItemData itemData)
    {

        List<EquipmentItemData> newList = new ArrayList<>();
        for (int i = 0; i < equipmentList.size(); i++)
        {
            var oldItem = equipmentList.get(i);
            if (oldItem.slot == itemData.slot)
            {
                newList.add(itemData);
            }
            else
            {
                newList.add(oldItem);
            }
        }

        equipmentList = newList;
    }

    public static <T> T[] listToArray(List<T> list, Class<T> clazz)
    {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(array);
    }


    public EquipmentStats getEquipmentStats(ItemManager itemManager)
    {
        int astab = 0;
        int aslash = 0;
        int acrush = 0;
        int amagic = 0;
        int arange = 0;

        int dstab = 0;
        int dslash = 0;
        int dcrush = 0;
        int dmagic = 0;
        int drange = 0;

        int str = 0;
        int rstr = 0;
        float mdmg = 0;
        int prayer = 0;
        int aspeed = 0;

        for (EquipmentItemData itemData : equipmentList)
        {
            var itemStats = itemManager.getItemStats(itemData.itemID);

            if (itemStats != null)
            {
                var equipmentStats = itemStats.getEquipment();

                if (equipmentStats != null)
                {
                   astab += equipmentStats.getAstab();
                   aslash += equipmentStats.getAslash();
                   acrush += equipmentStats.getAcrush();
                   amagic += equipmentStats.getAmagic();
                   arange += equipmentStats.getArange();

                   dstab += equipmentStats.getDstab();
                   dslash += equipmentStats.getDslash();
                   dcrush += equipmentStats.getDcrush();
                   dmagic += equipmentStats.getDmagic();
                   drange += equipmentStats.getDrange();

                   str += equipmentStats.getStr();
                   rstr += equipmentStats.getRstr();
                   mdmg += equipmentStats.getMdmg();
                   prayer += equipmentStats.getPrayer();
                   aspeed += equipmentStats.getAspeed();
                }
            }
        }

        return new EquipmentStats(astab, aslash, acrush, amagic, arange, dstab, dslash, dcrush, dmagic, drange, str, rstr, mdmg, prayer, aspeed);
    }

    public static EquipmentData getDefault()
    {
        int[] kitRecolours = new int[] {
                6,
                6,
                1,
                0,
                0
        };
        LightingStyle ls = LightingStyle.ACTOR;
        CustomLighting customLighting = new CustomLighting(
                ls.getAmbient(),
                ls.getContrast(),
                ls.getX(),
                ls.getY(),
                ls.getZ());

        EquipmentData equipmentData = new EquipmentData(List.of(
                new EquipmentItemData(-1, EquipmentInventorySlot.HEAD, null, null),
                new EquipmentItemData(-1, EquipmentInventorySlot.CAPE, null, null),
                new EquipmentItemData(-1, EquipmentInventorySlot.AMULET, null, null),
                new EquipmentItemData(-1, EquipmentInventorySlot.WEAPON, null, null),
                new EquipmentItemData(-1, EquipmentInventorySlot.BODY, null, null),
                new EquipmentItemData(-1, EquipmentInventorySlot.BODY, null, new ModelStats[]
                        {
                        new ModelStats(
                        28719, BodyPart.TORSO, new short[0], new short[0], new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                        )}),
                new EquipmentItemData(-1, EquipmentInventorySlot.SHIELD, null, null),
                new EquipmentItemData(-1, EquipmentInventorySlot.ARMS, null, new ModelStats[]
                        {
                                new ModelStats(
                                        26632, BodyPart.ARMS, new short[0], new short[0], new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                                )}),
                new EquipmentItemData(-1, EquipmentInventorySlot.LEGS, null, new ModelStats[]
                        {
                                new ModelStats(
                                        28337, BodyPart.LEGS, new short[0], new short[0], new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                                )}),
                new EquipmentItemData(-1, EquipmentInventorySlot.HAIR, null, new ModelStats[]
                        {
                                new ModelStats(
                                        28322, BodyPart.HAIR, new short[] {0}, new short[] {1}, new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                                )}),
                new EquipmentItemData(-1, EquipmentInventorySlot.GLOVES, null, new ModelStats[]
                        {
                                new ModelStats(
                                        176, BodyPart.HANDS, new short[0], new short[0], new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                                )}),
                new EquipmentItemData(-1, EquipmentInventorySlot.BOOTS, null, new ModelStats[]
                        {
                                new ModelStats(
                                        183, BodyPart.FEET, new short[0], new short[0], new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                                )}),
                new EquipmentItemData(-1, EquipmentInventorySlot.JAW, null, new ModelStats[]
                        {
                                new ModelStats(
                                        253, BodyPart.JAW, new short[0], new short[0], new short[0], new short[0], 128, 128, 128, 0, customLighting, kitRecolours
                                )})
                ));

        return equipmentData;
    }

}
