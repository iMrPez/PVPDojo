package com.pvpdojo;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FightSetupPanel extends PluginPanel
{
    //private final JPanel panel = new JPanel();

    private final PVPDojoPlugin plugin;
    private final PVPDojoConfig config;

    @Inject
    public FightSetupPanel(PVPDojoPlugin plugin, PVPDojoConfig config)
    {
        super(false);
        this.plugin = plugin;
        this.config = config;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(8, 8, 8, 8));
        JPanel mainContent = new JPanel(new GridLayout(1, 1));

        JButton getGearButton = new JButton("Get Gear");
        getGearButton.setHorizontalAlignment(SwingConstants.CENTER);
        getGearButton.setVerticalAlignment(SwingConstants.TOP);
        getGearButton.setSize(5, 10);

        mainContent.add(getGearButton);



        add(mainContent);
       /* JScrollPane scrollableContainer = new JScrollPane(mainContent);
        scrollableContainer.setBackground(ColorScheme.DARK_GRAY_COLOR);
        scrollableContainer.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));

        scrollableContainer.add(getGearButton);

        add(scrollableContainer);*/
    }


    public void rebuild()
    {
        SwingUtilities.invokeLater(this::updateUI);
    }

}
