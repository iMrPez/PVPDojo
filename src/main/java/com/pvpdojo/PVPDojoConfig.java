package com.pvpdojo;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("pvpdojo")
public interface PVPDojoConfig extends Config
{

    @ConfigSection(
            name = "Play Settings",
            description = "play settings",
            position = 0
    )
    String playSettings = "playSettings";

    @ConfigSection(
            name = "Player Settings",
            description = "Player Settings",
            position = 1
    )
    String playerSettings = "playerSettings";

    @ConfigItem(
            keyName = "playerHitPoints",
            name = "Player HitPoints",
            description = "Player HitPoints",
            section = playerSettings,
            position = 0
    )
    default int playerHitPoints()
    {
        return 99;
    }

    @ConfigSection(
            name = "Dummy Settings",
            description = "Dummy Settings.",
            position = 2
    )
    String dummySettings = "dummySettings";

    @ConfigItem(
            keyName = "dummyHitPoints",
            name = "Dummy HitPoints",
            description = "Dummy HitPoints",
            section = dummySettings,
            position = 0
    )
    default int dummyHitPoints()
    {
        return 99;
    }

    @ConfigItem(
            keyName = "useProtectionPrayers",
            name = "Use Protection Prayer",
            description = "Should the Dummy use protection prayers?",
            section = dummySettings,
            position = 0
    )
    default boolean useProtectionPrayers()
    {
        return true;
    }

    @ConfigItem(
            keyName = "useOffensivePrayers",
            name = "Use Offensive Prayer",
            description = "Should the Dummy use offensive prayers?",
            section = dummySettings,
            position = 0
    )
    default boolean useOffensivePrayers()
    {
        return true;
    }

    @ConfigItem(
            keyName = "useDefensivePrayers",
            name = "Use Defensive Prayer",
            description = "Should the Dummy use defensive prayers?",
            section = dummySettings,
            position = 0
    )
    default boolean useDefensivePrayers()
    {
        return true;
    }

    @ConfigItem(
            keyName = "prayerSwapDelay",
            name = "Prayer Swap Delay",
            description = "Max Amount of ticks before the Dummy uses protection prayer from what you're using",
            section = dummySettings,
            position = 1
    )

    default int prayerSwapDelay()
    {
        return 15;
    }

    @ConfigItem(
            keyName = "minPrayerSwapDelay",
            name = "Min Prayer Swap Delay",
            description = "Min Amount of ticks before the Dummy uses protection prayer from what you're using",
            section = dummySettings,
            position = 2
    )
    default int minPrayerSwapDelay()
    {
        return 2;
    }

    @ConfigItem(
            keyName = "equipmentSwapDelay",
            name = "Equipment Swap Delay",
            description = "Max Amount of ticks before the Dummy swaps gear to attack with what you're not protecting from",
            section = dummySettings,
            position = 3
    )
    default int equipmentSwapDelay()
    {
        return 15;
    }

    @ConfigItem(
            keyName = "minEquipmentSwapDelay",
            name = "Min Equipment Swap Delay",
            description = "Min Amount of ticks before the Dummy swaps gear to attack with what you're not protecting from",
            section = dummySettings,
            position = 4
    )
    default int minEquipmentSwapDelay()
    {
        return 2;
    }

}
