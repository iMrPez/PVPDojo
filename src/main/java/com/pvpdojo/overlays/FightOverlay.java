package com.pvpdojo.overlays;

import com.pvpdojo.PVPDojoPlugin;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

public class FightOverlay extends Overlay
{

    private final Client client;
    private final PVPDojoPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public FightOverlay(Client client, PVPDojoPlugin plugin)
    {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();
        String overlayTitle = "- Fight Data -";

        // Build overlay title
        panelComponent.getChildren().add(TitleComponent.builder()
                .text(overlayTitle)
                .color(Color.GREEN)
                .build());

        // Set the size of the overlay (width)
        panelComponent.setPreferredSize(new Dimension(
                graphics.getFontMetrics().stringWidth(overlayTitle) + 30,
                0));

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Player")
                .right("Dummy")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left(plugin.totalOffPrayerHitsOnDummy + "/" + plugin.totalHitsOnDummy)
                .leftColor(plugin.totalOffPrayerHitsOnDummy > plugin.totalOffPrayerHitsOnPlayer ? Color.green : Color.white)
                .right(plugin.totalOffPrayerHitsOnPlayer + "/" + plugin.totalHitsOnPlayer)
                .rightColor(plugin.totalOffPrayerHitsOnDummy < plugin.totalOffPrayerHitsOnPlayer ? Color.green : Color.white)
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("" + plugin.totalDamageOnDummy)
                .leftColor(plugin.totalDamageOnDummy > plugin.totalDamageOnPlayer ? Color.green : Color.white)
                .right("" + plugin.totalDamageOnPlayer)
                .rightColor(plugin.totalDamageOnPlayer > plugin.totalDamageOnDummy ? Color.green : Color.white)
                .build());

        /*panelComponent.getChildren().add(LineComponent.builder()
                .left("Combat Ticks: ")
                .right("" + plugin.corePlayer.combatActionTicks)
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Auto Attacking: ")
                .right("" + plugin.corePlayer.shouldAutoAttack)
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Swapped Weapons: ")
                .right("" + plugin.corePlayer.swappedWeaponThisTick)
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Attack Requested: ")
                .right("" + plugin.corePlayer.clickedAttackThisTick)
                .build());*/

        // Show world type goes here ...

        return panelComponent.render(graphics);
    }
}
