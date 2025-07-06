package com.pvpdojo;

import com.google.gson.Gson;
import com.pvpdojo.character.datatypes.EquipmentData;
import net.runelite.client.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PVPDojoData
{
    private static final String CONFIG_GROUP = "pvpdojo";
    private static final Logger log = LoggerFactory.getLogger(PVPDojoData.class);


    public EquipmentData meleeEquipmentData;
    public EquipmentData rangeEquipmentData;
    public EquipmentData magicEquipmentData;
    public EquipmentData specEquipmentData;


    public PVPDojoData(EquipmentData meleeEquipmentData, EquipmentData rangeEquipmentData, EquipmentData magicEquipmentData, EquipmentData specEquipmentData)
    {
        this.meleeEquipmentData = meleeEquipmentData;
        this.rangeEquipmentData = rangeEquipmentData;
        this.magicEquipmentData = magicEquipmentData;
        this.specEquipmentData = specEquipmentData;
    }

    public static void saveData(ConfigManager configManager, PVPDojoData data)
    {
        String json = new Gson().toJson(data);
        log.info("Saving " + json);
        configManager.setConfiguration(CONFIG_GROUP, "settings", json);
    }

    public static PVPDojoData loadData(ConfigManager configManager)
    {
        String json = configManager.getConfiguration(CONFIG_GROUP, "settings");
        log.info("Loading " + json);
        return json != null ? new Gson().fromJson(json, PVPDojoData.class) : null;
    }
}
