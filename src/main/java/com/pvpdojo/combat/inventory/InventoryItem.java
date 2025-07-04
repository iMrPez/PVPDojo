package com.pvpdojo.combat.inventory;

public enum InventoryItem
{
    ANGLERFISH("Anglerfish", InventoryItemType.HARD_FOOD,
            new InventoryUseProperties(22, true, false, 0, 0, 0, 0, 0)),
    KARAMBWAN("Cooked karambwan", InventoryItemType.COMBO_FOOD,
            new InventoryUseProperties(18, false, false, 0, 0, 0, 0, 0)),
    SARADOMIN_BREW("Saradomin brew", InventoryItemType.SARA_BREW,
            new InventoryUseProperties(16, true, true, -14, -14, 21, -14, -14)),
    SUPER_RESTORE("Super restore", InventoryItemType.SUPER_RESTORE,
                        new InventoryUseProperties(0, false, false, 32, 32, 32, 32, 32));

    public final String itemName;
    public final InventoryItemType type;

    public final InventoryUseProperties useProperties;

    InventoryItem(String itemName, InventoryItemType type, InventoryUseProperties useProperties)
    {
        this.itemName = itemName;
        this.type = type;
        this.useProperties = useProperties;
    }

    public String getActionName()
    {
        switch (type) {
            case OTHER:
                return "IGNORE";
            case HARD_FOOD:
            case COMBO_FOOD:
                return "Eat";
            case SARA_BREW:
            case SUPER_RESTORE:
                return "Drink";
        }

        return "NULL";
    }
}
