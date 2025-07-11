package com.pvpdojo;

import com.pvpdojo.character.datatypes.EquipmentData;
import com.pvpdojo.character.datatypes.EquipmentItemData;
import net.runelite.api.EquipmentInventorySlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentHandler
{
    public Map<EquipmentInventorySlot, EquipmentItemData> defaultEquipment = new HashMap<>();
    public Map<EquipmentInventorySlot, EquipmentItemData> equippedEquipment = new HashMap<>();


    public EquipmentHandler()
    {
        var defaultEquipmentData = EquipmentData.getDefault();

        for (EquipmentItemData itemData : defaultEquipmentData.equipmentList)
        {
            defaultEquipment.put(itemData.slot, itemData);
        }

    }

    public EquipmentData getEquipmentData()
    {
        List<EquipmentItemData> items = new ArrayList<>();
        for (EquipmentInventorySlot slot : EquipmentInventorySlot.values())
        {
            if (equippedEquipment.containsKey(slot))
            {
                items.add(equippedEquipment.get(slot));
            }
            else
            {
                items.add(defaultEquipment.get(slot));
            }
        }
        return  new EquipmentData(items);
    }

    public void equipItem(EquipmentItemData itemData)
    {
        if (itemData.slot == EquipmentInventorySlot.WEAPON)
        {
            if (itemData.weaponData != null && itemData.weaponData.isTwoHanded())
            {
                equippedEquipment.remove(EquipmentInventorySlot.SHIELD);
            }
        }
        equippedEquipment.put(itemData.slot, itemData);
    }

    public void unequipSlot(EquipmentInventorySlot slot)
    {
        equippedEquipment.remove(slot);
    }

    public void equipData(EquipmentData equipmentData)
    {
        equippedEquipment.clear();

        for (EquipmentItemData itemData : equipmentData.equipmentList)
        {
            equippedEquipment.put(itemData.slot, itemData);
        }
    }
}
