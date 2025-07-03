package com.pvpdojo;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.*;

public class SpecialBarOverlay extends Overlay
{

    private static final Logger log = LoggerFactory.getLogger(SpecialBarOverlay.class);
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final SpriteManager spriteManager;
    private final PVPDojoConfig config;

    private final int MAX_WIDTH = 144;
    private int currentWidth = 144;

    private boolean specialBarSelected = false;

    @Inject
    private SpecialBarOverlay(Client client, PVPDojoPlugin plugin, SpriteManager spriteManager, PVPDojoConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        /*setPriority(OverlayPriority.HIGHEST);*/
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {

        Widget specialAttackWidget = client.getWidget(593, 38);
        if (specialAttackWidget != null && !specialAttackWidget.isHidden())
        {
            currentWidth = (int)(((float)getSpecialEnergy() / 100) * (float)MAX_WIDTH);

            Rectangle bounds = specialAttackWidget.getBounds();

            graphics.setColor(new Color(200, 50, 50));
            graphics.fillRect(bounds.x + 3, bounds.y + 7, 144, 12);

            graphics.setColor(new Color(50, 150, 200));
            graphics.fillRect(bounds.x + 3, bounds.y + 7, currentWidth, 12);

            graphics.setColor(specialBarSelected ? Color.yellow : Color.black);
            graphics.drawString("Special Attack: " + getSpecialEnergy() + "%", bounds.x + 22, bounds.y + 18);
        }


        // Draw custom health value
        /**/
        return null;
    }


    public int getSpecialEnergy()
    {
        return plugin.corePlayer.specialAttackEnergy;
    }

    public void activeSpecial()
    {
        var playerWeaponData = plugin.corePlayer.getWeaponData();
        if (playerWeaponData.getCanSpec() && playerWeaponData.getSpecData().energy <= getSpecialEnergy())
        {
            specialBarSelected = true;
        }
    }

    public void setSpecialBarSelected(boolean value)
    {
        specialBarSelected = value;
    }

    public boolean isSpecialBarSelected()
    {
        return specialBarSelected;
    }
}
