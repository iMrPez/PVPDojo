package com.pvpdojo;

public class InventoryItem
{
    public final String itemName;
    public final InventoryItemType type;

    public final InventoryUseProperties useProperties;

    public InventoryItem(String itemName, InventoryItemType type, InventoryUseProperties useProperties)
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
