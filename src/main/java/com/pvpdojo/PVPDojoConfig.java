package com.pvpdojo;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("pvpdojo")
public interface PVPDojoConfig extends Config
{

    @ConfigSection(
            name = "Consumable Settings",
            description = "Consumable Settings",
            position = 0
    )
    String consumableSettings = "consumableSettings";

    @ConfigItem(
            keyName = "playerHardFood",
            name = "Player Hard Food",
            description = "Amount of hard food the player will have for each fight.",
            section = consumableSettings,
            position = 0
    )
    default int playerHardFood()
    {
        return 8;
    }

    @ConfigItem(
            keyName = "playerComboFood",
            name = "Player Combo Food",
            description = "Amount of combo food the player will have for each fight.",
            section = consumableSettings,
            position = 1
    )
    default int playerComboFood()
    {
        return 4;
    }

    @ConfigItem(
            keyName = "playerBrewDoses",
            name = "Player Brew Doses",
            description = "Amount of brew doses the player will have for each fight.",
            section = consumableSettings,
            position = 2
    )
    default int playerBrewDoses()
    {
        return 2;
    }

    @ConfigItem(
            keyName = "dummyHardFood",
            name = "Dummy Hard Food",
            description = "Amount of hard food the dummy will have for each fight.",
            section = consumableSettings,
            position = 3
    )
    default int dummyHardFood()
    {
        return 8;
    }

    @ConfigItem(
            keyName = "dummyComboFood",
            name = "Dummy Combo Food",
            description = "Amount of combo food the dummy will have for each fight.",
            section = consumableSettings,
            position = 4
    )
    default int dummyComboFood()
    {
        return 4;
    }

    @ConfigItem(
            keyName = "dummyBrewDoses",
            name = "Dummy Brew Doses",
            description = "Amount of brew doses the dummy will have for each fight.",
            section = consumableSettings,
            position = 5
    )
    default int dummyBrewDoses()
    {
        return 2;
    }

    @ConfigSection(
            name = "Player Settings",
            description = "Player Settings",
            position = 6
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
    default boolean useProtectionPrayers() { return true; }

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
            keyName = "useIceBarrage",
            name = "Use Ice Barrage",
            description = "Should the Dummy be able to freeze you.",
            section = dummySettings,
            position = 0
    )
    default boolean useIceBarrage()
    {
        return true;
    }

    @ConfigItem(
            keyName = "useDD",
            name = "Use DD",
            description = "Should the Dummy step under you while you're frozen.",
            section = dummySettings,
            position = 0
    )
    default boolean useDD()
    {
        return true;
    }


    @ConfigItem(
            keyName = "useRandomMovement",
            name = "Use Random Movement",
            description = "Should the Dummy move randomly when not doing other actions?",
            section = dummySettings,
            position = 0
    )
    default boolean useRandomMovement()
    {
        return true;
    }

    @ConfigItem(
            keyName = "useConsumables",
            name = "Use Consumables",
            description = "Should the Dummy be able to eat food and drink potions.",
            section = dummySettings,
            position = 0
    )
    default boolean useConsumables()
    {
        return true;
    }


    @ConfigItem(
            keyName = "consumableUseRange",
            name = "Consumable Use Range",
            description = "This provides a random range that is used in conjunction with other consumable options. For example, having a range of 10, when eating a hard food is set to 80, the dummy will eat at 70-80 hp.",
            section = dummySettings,
            position = 1
    )

    default int consumableUseRange()
    {
        return 15;
    }

    @ConfigItem(
            keyName = "singleHardFoodAt",
            name = "Single Hard Food At",
            description = "At roughly what hp should the dummy eat a hard food at? if the dummy shouldn't eat a hard food outside a triple eat, set it to -1.",
            section = dummySettings,
            position = 1
    )

    default int singleHardFoodAt()
    {
        return 70;
    }

    @ConfigItem(
            keyName = "singleBrewDrinkAt",
            name = "Single Brew Drink At",
            description = "At roughly what hp should the dummy drink a brew dose at? if the dummy shouldn't drink a dose outside a triple eat, set it to -1.",
            section = dummySettings,
            position = 1
    )

    default int singleBrewDrinkAt()
    {
        return 85;
    }

    @ConfigItem(
            keyName = "tripleEatAt",
            name = "Triple Eat At",
            description = "At roughly what hp should the dummy triple eat at? if the dummy shouldn't triple eat, set it to -1.",
            section = dummySettings,
            position = 1
    )

    default int tripleEatAt()
    {
        return 50;
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
