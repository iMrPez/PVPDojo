package com.pvpdojo.overlays;

import com.pvpdojo.character.CharacterObject;
import com.pvpdojo.combatant.Dummy;
import com.pvpdojo.PVPDojoPlugin;
import com.pvpdojo.timesheet.keyframe.settings.OverheadSprite;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class OverheadOverlay extends Overlay
{
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final SpriteManager spriteManager;

    private final int HEIGHT_BUFFER = 18;
    private final int OVERHEAD_Y_BUFFER = -18;
    private final int SKULL_Y_BUFFER = -25;
    private final int TEXT_BUFFER = -12;
    private final int X_BUFFER = -1;

    private OverheadSprite dummyPrayerSprite = OverheadSprite.NONE;
    private OverheadSprite dummySkullSprite = OverheadSprite.NONE;

    private OverheadSprite playerPrayerSprite = OverheadSprite.NONE;
    private OverheadSprite playerSkullSprite = OverheadSprite.NONE;



    @Inject
    private OverheadOverlay(Client client, PVPDojoPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        setPriority(OverlayPriority.HIGHEST);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return null;
        }

        displayDummyOverhead(graphics);
        displayPlayerOverhead(graphics);

        return null;
    }

    private void displayDummyOverhead(Graphics2D graphics)
    {
        Dummy dummy = plugin.dummy;

        if (dummy == null)
        {
            return;
        }

        if (dummy.dummyCharacter == null)
        {
            return;
        }

        if (!dummy.dummyCharacter.isActive())
        {
            return;
        }


        if (dummyPrayerSprite == OverheadSprite.NONE && dummySkullSprite == OverheadSprite.NONE)
        {
            return;
        }

        CharacterObject characterObject = dummy.dummyCharacter.getCharacterObject();
        if (!characterObject.isActive())
        {
            return;
        }

        LocalPoint lp = characterObject.getLocation();
        Model model = characterObject.getModel();
        displayOverhead(graphics, lp, model, dummyPrayerSprite, dummySkullSprite);
    }

    private void displayPlayerOverhead(Graphics2D graphics)
    {

        if (playerPrayerSprite == OverheadSprite.NONE && playerSkullSprite == OverheadSprite.NONE)
        {
            return;
        }

        LocalPoint lp = client.getLocalPlayer().getLocalLocation();
        Model model = client.getLocalPlayer().getModel();
        displayOverhead(graphics, lp, model, playerPrayerSprite, playerSkullSprite);
    }


    private void displayOverhead(Graphics2D graphics, LocalPoint lp, Model model, OverheadSprite prayer, OverheadSprite skull)
    {
        if (lp == null || !lp.isInScene())
        {
            return;
        }

        BufferedImage icon = spriteManager.getSprite(prayer.getSpriteID(), prayer.getFile());
        if (icon == null)
        {
            return;
        }


        if (model == null)
        {
            return;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        int textBuffer = 0;

        Point base = Perspective.getCanvasImageLocation(client, lp, icon, height + HEIGHT_BUFFER);
        if (base == null)
        {
            return;
        }

        int skullBuffer = 0;
        if (skull != OverheadSprite.NONE)
        {
            Point p = new Point(base.getX() + X_BUFFER, base.getY() + OVERHEAD_Y_BUFFER + textBuffer);
            skullBuffer = SKULL_Y_BUFFER;
            BufferedImage skullSprite = spriteManager.getSprite(skull.getSpriteID(), skull.getFile());
            OverlayUtil.renderImageLocation(graphics, p, skullSprite);
        }

        if (prayer != OverheadSprite.NONE)
        {
            Point p = new Point(base.getX() + X_BUFFER, base.getY() + OVERHEAD_Y_BUFFER + skullBuffer + textBuffer);
            OverlayUtil.renderImageLocation(graphics, p, icon);
        }
    }

    public void clearOverheads()
    {
        this.dummyPrayerSprite = OverheadSprite.NONE;
        this.dummySkullSprite = OverheadSprite.NONE;
        this.playerPrayerSprite = OverheadSprite.NONE;
        this.playerSkullSprite = OverheadSprite.NONE;
    }

    public void setDummyPrayerSprite(OverheadSprite prayerSprite)
    {
        this.dummyPrayerSprite = prayerSprite;
    }

    public void setDummySkullSprite(OverheadSprite skullSprite)
    {
        this.dummySkullSprite = skullSprite;
    }

    public void setPlayerPrayerSprite(OverheadSprite playerPrayerSprite)
    {
        this.playerPrayerSprite = playerPrayerSprite;
    }

    public void setPlayerSkullSprite(OverheadSprite playerSkullSprite)
    {
        this.playerSkullSprite = playerSkullSprite;
    }
}