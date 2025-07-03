package com.pvpdojo;

public class DamageDisplayData
{
    public String name;
    public int hitDelay = 1;
    public DamageData damageData;

    public DamageDisplayData(String name, int hitDelay, DamageData damageData)
    {
        this.name = name;
        this.hitDelay = hitDelay;
        this.damageData = damageData;
    }
}
