package com.pvpdojo.character.datatypes;

import com.pvpdojo.character.ModelStats;
import net.runelite.api.EquipmentInventorySlot;

public class EquipmentItemData
{
    public final int itemID;
    public final EquipmentInventorySlot slot;
    public final WeaponData weaponData;
    public final ModelStats[] modelStats;

    public EquipmentItemData(int itemID, EquipmentInventorySlot slot, WeaponData weaponData, ModelStats[] modelStats)
    {
        this.itemID = itemID;
        this.slot = slot;
        this.weaponData = weaponData;
        this.modelStats = modelStats;
    }
}
