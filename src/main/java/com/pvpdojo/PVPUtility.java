package com.pvpdojo;

import com.pvpdojo.character.SpellData;
import com.pvpdojo.timesheet.keyframe.settings.HitsplatSprite;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class PVPUtility
{
    private static final Logger log = LoggerFactory.getLogger(PVPUtility.class);

    public static boolean isInRange(WorldPoint start, WorldPoint end, int range)
    {
        var distance = start.distanceTo(end);

        if (distance == 0)
        {
            return false;
        }

        var isDiagonal = isDiagonal(start, end);
        if (range == 1 && isDiagonal)
        {
            return false;
        }

        return distance <= range;
    }

    public static boolean isDiagonal(WorldPoint a, WorldPoint b)
    {
        if (a.getPlane() != b.getPlane())
            return false;

        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());

        return dx == 1 && dy == 1;
    }

    public static int getWeaponID(Client client)
    {
        ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipment == null)
        {
            return -1;
        }

        Item weapon = equipment.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
        if (weapon == null)
        {
            return -1;
        }

        return weapon.getId();
    }

    public static WorldPoint getNearestAdjacentTile(WorldPoint origin, WorldPoint target, int plane)
    {
        var northTile = new WorldPoint(target.getX(), target.getY() + 1, plane);
        var southTile = new WorldPoint(target.getX(), target.getY() - 1, plane);
        var westTile = new WorldPoint(target.getX() - 1, target.getY(), plane);
        var eastTile = new WorldPoint(target.getX() + 1, target.getY(), plane);

        var northDistance = origin.distanceTo(northTile);
        var southDistance = origin.distanceTo(southTile);
        var westDistance = origin.distanceTo(westTile);
        var eastDistance = origin.distanceTo(eastTile);

        var shortestDistanceTile = northTile;
        var shortestDistance = 100000;

        if (northDistance < shortestDistance)
        {
            shortestDistance = northDistance;
            shortestDistanceTile = northTile;
        }

        if (southDistance < shortestDistance)
        {
            shortestDistance = southDistance;
            shortestDistanceTile = southTile;
        }

        if (westDistance < shortestDistance)
        {
            shortestDistance = westDistance;
            shortestDistanceTile = westTile;
        }

        if (eastDistance < shortestDistance)
        {
            shortestDistance = eastDistance;
            shortestDistanceTile = eastTile;
        }

        return shortestDistanceTile;
    }

    public static void playSound(Client client, int soundID)
    {
        //log.info("PLAYING ATTACK SOUND: " + combatStyle);
        client.playSoundEffect(soundID);
    }

    public static void playCombatStyleHitSound(Client client, CombatStyle combatStyle)
    {
        //log.info("PLAYING HIT SOUND: " + combatStyle);

        switch (combatStyle)
        {
            case MAGIC:
                client.playSoundEffect(226);
            case MELEE:
                client.playSoundEffect(2521);
            case RANGE:
                client.playSoundEffect(2694);
        }
    }

    public static WorldPoint getRandomWalkableWorldPoint(Client client, WorldPoint center, int radius)
    {
        Random random = new Random();
        CollisionData[] collisionMaps = client.getCollisionMaps();
        int plane = center.getPlane();

        for (int attempts = 0; attempts < 100; attempts++)
        {
            int dx = random.nextInt(radius * 2 + 1) - radius;
            int dy = random.nextInt(radius * 2 + 1) - radius;

            // Optional: enforce circular range
            if (dx * dx + dy * dy > radius * radius)
                continue;

            WorldPoint candidate = center.dx(dx).dy(dy);
            LocalPoint local = LocalPoint.fromWorld(client, candidate);

            if (local == null || plane >= collisionMaps.length)
                continue;

            int x = local.getSceneX();
            int y = local.getSceneY();

            // Check for out-of-bounds
            if (x < 0 || y < 0 || x >= 104 || y >= 104)
                continue;

            CollisionData map = collisionMaps[plane];
            int flags = map.getFlags()[x][y];

            boolean blocked = (flags & CollisionDataFlag.BLOCK_MOVEMENT_FULL) != 0;

            if (!blocked)
            {
                return candidate;
            }
        }

        return null; // no valid tile found
    }

    public static HitsplatSprite getHitsplatSprite(int damage)
    {
        return damage > 0 ? HitsplatSprite.DAMAGE : HitsplatSprite.BLOCK;
    }

    public static CombatStyle getCombatStyle(SpellData spellData, WeaponData weaponData)
    {
        if (spellData != null)
        {
            return CombatStyle.MAGIC;
        }
        switch (weaponData.getStyleType()) {
            case NONE:
            case STAB:
            case SLASH:
            case CRUSH:
                return CombatStyle.MELEE;
            case MAGIC:
                return CombatStyle.MAGIC;
            case RANGE:
                return CombatStyle.RANGE;
        }
        return CombatStyle.MAGIC;
    }



    private String getCombatStyleText(Client client, PVPDojoPlugin plugin, CombatStyle combatStyle)
    {

        switch (combatStyle)
        {
            case MAGIC:
                return "Magic";
            case MELEE:
                return "Melee";
            case RANGE:
                return "Range";
        }

        return "Other";
    }
}
