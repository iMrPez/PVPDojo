package com.pvpdojo.fightPanel;

import com.pvpdojo.PVPDojoPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EquipmentItemPanel extends JPanel
{
    private static final Logger log = LoggerFactory.getLogger(EquipmentItemPanel.class);
    private final PVPDojoPlugin plugin;
    private JLabel icon;
    private JLabel itemNameLabel;
    private JLabel equipmentSlotLabel;
    private JButton removeEquipmentButton;

    public EquipmentItemPanel(PVPDojoPlugin plugin, Image itemImage, String itemName, String slotName)
    {
        this.plugin = plugin;
        initializeComponent(itemImage, itemName, slotName);
    }

    public BufferedImage scaleImageUniformly(BufferedImage original, int maxWidth, int maxHeight)
    {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        // Calculate scaling factor (uniform, preserving aspect ratio)
        double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);

        // Don't upscale if image is already smaller
        if (scale >= 1.0)
            return original;

        int newWidth = (int) Math.round(originalWidth * scale);
        int newHeight = (int) Math.round(originalHeight * scale);

        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(original, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resized;
    }

    public String limitLength(String input, int maxLength)
    {
        if (input == null)
            return null;

        return input.length() > maxLength ? input.substring(0, maxLength) : input;
    }

    public void initializeComponent(Image itemImage, String itemName, String slotName)
    {
        icon = new JLabel();
        itemNameLabel = new JLabel();
        equipmentSlotLabel = new JLabel();
        removeEquipmentButton = new JButton();

        removeEquipmentButton.addActionListener(e -> plugin.removeEquippedItemPressed(this));
        itemName = limitLength(itemName, 18);

        setPreferredSize(new Dimension(395, 35));
        setBorder(LineBorder.createGrayLineBorder());
        setBackground(new Color(40, 40, 40));
        setMaximumSize(new Dimension(395, 35));
        setMinimumSize(new Dimension(395, 35));
        setLayout(null);

        //---- icon ----
        icon.setIcon(new ImageIcon(scaleImageUniformly((BufferedImage) itemImage, 25, 25)));
        icon.setHorizontalTextPosition(SwingConstants.CENTER);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        add(icon);
        icon.setBounds(5, 5, 25, 25);

        //---- itemNameLabel ----
        itemNameLabel.setText(itemName);
        itemNameLabel.setFont(new Font("Inter", Font.BOLD, 11));
        add(itemNameLabel);
        itemNameLabel.setBounds(new Rectangle(new Point(35, 5), itemNameLabel.getPreferredSize()));

        //---- equipmentSlotLabel ----
        equipmentSlotLabel.setText(slotName);
        equipmentSlotLabel.setFont(new Font("Inter", Font.PLAIN, 8));
        equipmentSlotLabel.setForeground(new Color(135, 135, 135));
        equipmentSlotLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(equipmentSlotLabel);
        equipmentSlotLabel.setBounds(35, 18, 99, 12);

        //---- removeEquipmentButton ----
        removeEquipmentButton.setText("-");
        removeEquipmentButton.setPreferredSize(new Dimension(20, 20));
        removeEquipmentButton.setBorder(new LineBorder(Color.red));
        removeEquipmentButton.setForeground(Color.red);
        add(removeEquipmentButton);
        removeEquipmentButton.setBounds(new Rectangle(new Point(170, 7), removeEquipmentButton.getPreferredSize()));
    }
}
