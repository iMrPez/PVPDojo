package com.pvpdojo;

import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.widgets.Widget;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.*;

public class PrayerOverlay extends Overlay
{

    private static final Logger log = LoggerFactory.getLogger(PrayerOverlay.class);
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final SpriteManager spriteManager;

    @Inject
    public PrayerOverlay(Client client, PVPDojoPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);

        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        for (Prayers prayer : Prayers.values())
        {
            if (plugin.corePlayer.prayers.contains(prayer.prayer))
            {
                Widget prayerWidget = client.getWidget(prayer.groupId, prayer.childId);
                if (prayerWidget != null && !prayerWidget.isHidden())
                {

                    Rectangle bounds = prayerWidget.getBounds();

                    graphics.setColor(new Color(204, 183, 123, 50));
                    graphics.fillOval((int) bounds.getX(), (int) bounds.getY(), bounds.width, bounds.height);

                }
            }
        }


        return null;
    }
}
