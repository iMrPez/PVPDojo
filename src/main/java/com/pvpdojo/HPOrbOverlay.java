package com.pvpdojo;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class HPOrbOverlay extends Overlay
{
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final SpriteManager spriteManager;
    private final PVPDojoConfig config;

    @Inject
    private HPOrbOverlay(Client client, PVPDojoPlugin plugin, SpriteManager spriteManager, PVPDojoConfig config)
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
        Widget hpOrb = client.getWidget(WidgetInfo.MINIMAP_HEALTH_ORB);

        if (hpOrb != null && !hpOrb.isHidden())
        {
            Rectangle bounds = hpOrb.getBounds();

            // Draw your custom orb
            graphics.setColor(new Color(104, 90, 75));
            graphics.fillRect(bounds.x + 4, bounds.y + 15, 20, 12);

            // Draw custom health value
            graphics.setColor(Color.black);
            graphics.drawString(plugin.corePlayer.getHealth() + "", bounds.x + 8.5f, bounds.y + 28);
            graphics.setColor(Color.green);
            graphics.drawString(plugin.corePlayer.getHealth() + "", bounds.x + 8, bounds.y + 27);

        }

        //hpOrb.setHidden(true);

        return null;
    }
}
