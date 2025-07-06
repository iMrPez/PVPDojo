import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import net.miginfocom.swing.*;
import org.jdesktop.swingx.*;
/*
 * Created by JFormDesigner on Fri Jul 04 11:51:24 EDT 2025
 */



/**
 * @author Owner
 */
public class Testing extends JPanel
{
    public Testing() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        scrollPanel = new JScrollPane();
        contents = new JPanel();
        header = new JLabel();
        separator1 = new JSeparator();
        playerStatsPanel = new JPanel();
        playerStatsBackground = new JPanel();
        playerStats = new JPanel();
        playerHeaderPanel = new JPanel();
        playerHeaderLabel = new JLabel();
        playerHitpointsPanel = new JPanel();
        playerHitpointsIcon = new JLabel();
        playerHitpointsSpinner = new JSpinner();
        playerAttackPanel = new JPanel();
        playerAttackIcon = new JLabel();
        playerAttackSpinner = new JSpinner();
        playerStrengthPanel = new JPanel();
        playerStrengthIcon = new JLabel();
        playerStrengthSpinner = new JSpinner();
        playerDefencePanel = new JPanel();
        playerDefenceIcon = new JLabel();
        playerDefenceSpinner = new JSpinner();
        playerRangePanel = new JPanel();
        playerRangeIcon = new JLabel();
        playerRangeSpinner = new JSpinner();
        playerPrayerPanel = new JPanel();
        playerPrayerIcon = new JLabel();
        playerPrayerSpinner = new JSpinner();
        playerMagicPanel = new JPanel();
        playerMagicIcon = new JLabel();
        playerMagicSpinner = new JSpinner();
        dummyStatsPanel = new JPanel();
        dummyStatsBackground = new JPanel();
        dummyStats = new JPanel();
        dummyHeaderPanel = new JPanel();
        dummyHeaderLabel = new JLabel();
        dummyHitpointsPanel = new JPanel();
        dummyHitpointsIcon = new JLabel();
        dummyHitpointsSpinner = new JSpinner();
        dummyAttackPanel = new JPanel();
        dummyAttackIcon = new JLabel();
        dummyAttackSpinner = new JSpinner();
        dummyStrengthPanel = new JPanel();
        dummyStrengthIcon = new JLabel();
        dummyStrengthSpinner = new JSpinner();
        dummyDefencePanel = new JPanel();
        dummyDefenceIcon = new JLabel();
        dummyDefenceSpinner = new JSpinner();
        dummyRangePanel = new JPanel();
        dummyRangeIcon = new JLabel();
        dummyRangeSpinner = new JSpinner();
        dummyPrayerPanel = new JPanel();
        dummyPrayerIcon = new JLabel();
        dummyPrayerSpinner = new JSpinner();
        dummyMagicPanel = new JPanel();
        dummyMagicIcon = new JLabel();
        dummyMagicSpinner = new JSpinner();
        dummyEquipmentOptionsPanel = new JPanel();
        dummyEquipmentOptionsBox = new JPanel();
        optionHeaderPanel = new JPanel();
        optionHeaderLabel = new JLabel();
        dummyEquipmentOptions = new JPanel();
        meleeEquipmentPanel = new JPanel();
        meleeButton = new JButton();
        rangeEquipmentPanel = new JPanel();
        rangeButton = new JButton();
        magicEquipmentPanel = new JPanel();
        magicButton = new JButton();
        specEquipmentPanel = new JPanel();
        specButton = new JButton();
        separator2 = new JSeparator();
        equipmentPanel = new JPanel();
        equipmentOptions = new JPanel();
        optionButtons = new JPanel();
        addEquippedButton = new JButton();
        clearButton = new JButton();
        equipmentListPanel = new JPanel();
        selectedEquipment = new JLabel();
        equipmentScrollPanel = new JScrollPane();
        equipmentContent = new JPanel();
        equipmentItem = new JPanel();
        icon = new JLabel();
        itemNameLabel = new JLabel();
        equipmentSlotLabel = new JLabel();
        removeEquipmentButton = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(100, 743));
        setMaximumSize(new Dimension(100, 2147483647));
        setLayout(new CardLayout());

        //======== scrollPanel ========
        {
            scrollPanel.setMaximumSize(null);
            scrollPanel.setPreferredSize(null);

            //======== contents ========
            {
                contents.setMaximumSize(new Dimension(100, 1000));
                contents.setPreferredSize(new Dimension(100, 800));
                contents.setMinimumSize(new Dimension(100, 1000));
                contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));

                //---- header ----
                header.setText("PVP Dojo");
                header.setAlignmentX(0.5F);
                header.setHorizontalTextPosition(SwingConstants.CENTER);
                header.setHorizontalAlignment(SwingConstants.CENTER);
                header.setFont(header.getFont().deriveFont(header.getFont().getStyle() | Font.BOLD, header.getFont().getSize() + 11f));
                header.setMaximumSize(new Dimension(110, 40));
                header.setMinimumSize(new Dimension(110, 40));
                header.setPreferredSize(new Dimension(110, 40));
                contents.add(header);

                //---- separator1 ----
                separator1.setPreferredSize(new Dimension(0, 6));
                contents.add(separator1);

                //======== playerStatsPanel ========
                {
                    playerStatsPanel.setBackground(null);
                    playerStatsPanel.setPreferredSize(new Dimension(120, 130));
                    playerStatsPanel.setMinimumSize(new Dimension(120, 130));
                    playerStatsPanel.setLayout(new FlowLayout());

                    //======== playerStatsBackground ========
                    {
                        playerStatsBackground.setBackground(Color.darkGray);
                        playerStatsBackground.setPreferredSize(new Dimension(210, 120));
                        playerStatsBackground.setBorder(LineBorder.createBlackLineBorder());
                        playerStatsBackground.setLayout(null);

                        //======== playerStats ========
                        {
                            playerStats.setPreferredSize(new Dimension(190, 100));
                            playerStats.setMinimumSize(new Dimension(240, 96));
                            playerStats.setBorder(null);
                            playerStats.setBackground(null);
                            playerStats.setLayout(new GridLayout(4, 3));

                            //======== playerHeaderPanel ========
                            {
                                playerHeaderPanel.setMinimumSize(new Dimension(60, 17));
                                playerHeaderPanel.setBackground(null);
                                playerHeaderPanel.setLayout(new BorderLayout());

                                //---- playerHeaderLabel ----
                                playerHeaderLabel.setText("Player Stats");
                                playerHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                playerHeaderLabel.setPreferredSize(new Dimension(60, 17));
                                playerHeaderLabel.setMaximumSize(new Dimension(60, 17));
                                playerHeaderLabel.setVerticalAlignment(SwingConstants.TOP);
                                playerHeaderLabel.setFont(new Font("Inter", Font.BOLD, 13));
                                playerHeaderPanel.add(playerHeaderLabel, BorderLayout.CENTER);
                            }
                            playerStats.add(playerHeaderPanel);

                            //======== playerHitpointsPanel ========
                            {
                                playerHitpointsPanel.setBackground(null);
                                playerHitpointsPanel.setMinimumSize(new Dimension(80, 24));
                                playerHitpointsPanel.setPreferredSize(new Dimension(80, 24));
                                playerHitpointsPanel.setLayout(null);

                                //---- playerHitpointsIcon ----
                                playerHitpointsIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Hitpoints_icon.png")));
                                playerHitpointsPanel.add(playerHitpointsIcon);
                                playerHitpointsIcon.setBounds(new Rectangle(new Point(3, 3), playerHitpointsIcon.getPreferredSize()));

                                //---- playerHitpointsSpinner ----
                                playerHitpointsSpinner.setPreferredSize(new Dimension(65, 24));
                                playerHitpointsSpinner.setModel(new SpinnerNumberModel(99, 10, 99, 1));
                                playerHitpointsPanel.add(playerHitpointsSpinner);
                                playerHitpointsSpinner.setBounds(new Rectangle(new Point(30, 0), playerHitpointsSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerHitpointsPanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerHitpointsPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerHitpointsPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerHitpointsPanel.setMinimumSize(preferredSize);
                                    playerHitpointsPanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerHitpointsPanel);

                            //======== playerAttackPanel ========
                            {
                                playerAttackPanel.setBackground(null);
                                playerAttackPanel.setLayout(null);

                                //---- playerAttackIcon ----
                                playerAttackIcon.setPreferredSize(new Dimension(24, 24));
                                playerAttackIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Attack_icon.png")));
                                playerAttackIcon.setOpaque(false);
                                playerAttackIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                playerAttackPanel.add(playerAttackIcon);
                                playerAttackIcon.setBounds(new Rectangle(new Point(3, 0), playerAttackIcon.getPreferredSize()));

                                //---- playerAttackSpinner ----
                                playerAttackSpinner.setPreferredSize(new Dimension(65, 24));
                                playerAttackSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                playerAttackPanel.add(playerAttackSpinner);
                                playerAttackSpinner.setBounds(new Rectangle(new Point(30, 0), playerAttackSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerAttackPanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerAttackPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerAttackPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerAttackPanel.setMinimumSize(preferredSize);
                                    playerAttackPanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerAttackPanel);

                            //======== playerStrengthPanel ========
                            {
                                playerStrengthPanel.setBackground(null);
                                playerStrengthPanel.setLayout(null);

                                //---- playerStrengthIcon ----
                                playerStrengthIcon.setPreferredSize(new Dimension(24, 24));
                                playerStrengthIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Strength_icon.png")));
                                playerStrengthIcon.setOpaque(false);
                                playerStrengthIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                playerStrengthPanel.add(playerStrengthIcon);
                                playerStrengthIcon.setBounds(new Rectangle(new Point(3, 0), playerStrengthIcon.getPreferredSize()));

                                //---- playerStrengthSpinner ----
                                playerStrengthSpinner.setPreferredSize(new Dimension(65, 24));
                                playerStrengthSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                playerStrengthPanel.add(playerStrengthSpinner);
                                playerStrengthSpinner.setBounds(new Rectangle(new Point(30, 0), playerStrengthSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerStrengthPanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerStrengthPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerStrengthPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerStrengthPanel.setMinimumSize(preferredSize);
                                    playerStrengthPanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerStrengthPanel);

                            //======== playerDefencePanel ========
                            {
                                playerDefencePanel.setBackground(null);
                                playerDefencePanel.setLayout(null);

                                //---- playerDefenceIcon ----
                                playerDefenceIcon.setPreferredSize(new Dimension(24, 24));
                                playerDefenceIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Defence_icon.png")));
                                playerDefenceIcon.setOpaque(false);
                                playerDefenceIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                playerDefencePanel.add(playerDefenceIcon);
                                playerDefenceIcon.setBounds(new Rectangle(new Point(3, 0), playerDefenceIcon.getPreferredSize()));

                                //---- playerDefenceSpinner ----
                                playerDefenceSpinner.setPreferredSize(new Dimension(65, 24));
                                playerDefenceSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                playerDefencePanel.add(playerDefenceSpinner);
                                playerDefenceSpinner.setBounds(new Rectangle(new Point(30, 0), playerDefenceSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerDefencePanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerDefencePanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerDefencePanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerDefencePanel.setMinimumSize(preferredSize);
                                    playerDefencePanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerDefencePanel);

                            //======== playerRangePanel ========
                            {
                                playerRangePanel.setBackground(null);
                                playerRangePanel.setPreferredSize(new Dimension(60, 24));
                                playerRangePanel.setMinimumSize(new Dimension(60, 24));
                                playerRangePanel.setLayout(null);

                                //---- playerRangeIcon ----
                                playerRangeIcon.setPreferredSize(new Dimension(24, 24));
                                playerRangeIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Ranged_icon.png")));
                                playerRangeIcon.setOpaque(false);
                                playerRangeIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                playerRangePanel.add(playerRangeIcon);
                                playerRangeIcon.setBounds(new Rectangle(new Point(3, 0), playerRangeIcon.getPreferredSize()));

                                //---- playerRangeSpinner ----
                                playerRangeSpinner.setPreferredSize(new Dimension(65, 24));
                                playerRangeSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                playerRangePanel.add(playerRangeSpinner);
                                playerRangeSpinner.setBounds(new Rectangle(new Point(30, 0), playerRangeSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerRangePanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerRangePanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerRangePanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerRangePanel.setMinimumSize(preferredSize);
                                    playerRangePanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerRangePanel);

                            //======== playerPrayerPanel ========
                            {
                                playerPrayerPanel.setBackground(null);
                                playerPrayerPanel.setLayout(null);

                                //---- playerPrayerIcon ----
                                playerPrayerIcon.setPreferredSize(new Dimension(24, 24));
                                playerPrayerIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Prayer_icon.png")));
                                playerPrayerIcon.setOpaque(false);
                                playerPrayerIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                playerPrayerPanel.add(playerPrayerIcon);
                                playerPrayerIcon.setBounds(new Rectangle(new Point(3, 0), playerPrayerIcon.getPreferredSize()));

                                //---- playerPrayerSpinner ----
                                playerPrayerSpinner.setPreferredSize(new Dimension(65, 24));
                                playerPrayerSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                playerPrayerPanel.add(playerPrayerSpinner);
                                playerPrayerSpinner.setBounds(new Rectangle(new Point(30, 0), playerPrayerSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerPrayerPanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerPrayerPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerPrayerPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerPrayerPanel.setMinimumSize(preferredSize);
                                    playerPrayerPanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerPrayerPanel);

                            //======== playerMagicPanel ========
                            {
                                playerMagicPanel.setBackground(null);
                                playerMagicPanel.setLayout(null);

                                //---- playerMagicIcon ----
                                playerMagicIcon.setPreferredSize(new Dimension(24, 24));
                                playerMagicIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Magic_icon.png")));
                                playerMagicIcon.setOpaque(false);
                                playerMagicIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                playerMagicPanel.add(playerMagicIcon);
                                playerMagicIcon.setBounds(new Rectangle(new Point(3, 0), playerMagicIcon.getPreferredSize()));

                                //---- playerMagicSpinner ----
                                playerMagicSpinner.setPreferredSize(new Dimension(65, 24));
                                playerMagicSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                playerMagicPanel.add(playerMagicSpinner);
                                playerMagicSpinner.setBounds(new Rectangle(new Point(30, 0), playerMagicSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < playerMagicPanel.getComponentCount(); i++) {
                                        Rectangle bounds = playerMagicPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = playerMagicPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    playerMagicPanel.setMinimumSize(preferredSize);
                                    playerMagicPanel.setPreferredSize(preferredSize);
                                }
                            }
                            playerStats.add(playerMagicPanel);
                        }
                        playerStatsBackground.add(playerStats);
                        playerStats.setBounds(10, 10, 190, 100);
                    }
                    playerStatsPanel.add(playerStatsBackground);
                }
                contents.add(playerStatsPanel);

                //======== dummyStatsPanel ========
                {
                    dummyStatsPanel.setBackground(null);
                    dummyStatsPanel.setPreferredSize(new Dimension(120, 130));
                    dummyStatsPanel.setMinimumSize(new Dimension(120, 130));
                    dummyStatsPanel.setLayout(new FlowLayout());

                    //======== dummyStatsBackground ========
                    {
                        dummyStatsBackground.setBackground(Color.darkGray);
                        dummyStatsBackground.setPreferredSize(new Dimension(210, 120));
                        dummyStatsBackground.setBorder(LineBorder.createBlackLineBorder());
                        dummyStatsBackground.setLayout(null);

                        //======== dummyStats ========
                        {
                            dummyStats.setPreferredSize(new Dimension(190, 100));
                            dummyStats.setMinimumSize(new Dimension(240, 96));
                            dummyStats.setBorder(null);
                            dummyStats.setBackground(null);
                            dummyStats.setLayout(new GridLayout(4, 3));

                            //======== dummyHeaderPanel ========
                            {
                                dummyHeaderPanel.setMinimumSize(new Dimension(60, 17));
                                dummyHeaderPanel.setBackground(null);
                                dummyHeaderPanel.setLayout(new BorderLayout());

                                //---- dummyHeaderLabel ----
                                dummyHeaderLabel.setText("Dummy Stats");
                                dummyHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyHeaderLabel.setPreferredSize(new Dimension(60, 17));
                                dummyHeaderLabel.setMaximumSize(new Dimension(60, 17));
                                dummyHeaderLabel.setVerticalAlignment(SwingConstants.TOP);
                                dummyHeaderLabel.setFont(new Font("Inter", Font.BOLD, 13));
                                dummyHeaderPanel.add(dummyHeaderLabel, BorderLayout.CENTER);
                            }
                            dummyStats.add(dummyHeaderPanel);

                            //======== dummyHitpointsPanel ========
                            {
                                dummyHitpointsPanel.setBackground(null);
                                dummyHitpointsPanel.setMinimumSize(new Dimension(80, 24));
                                dummyHitpointsPanel.setPreferredSize(new Dimension(80, 24));
                                dummyHitpointsPanel.setLayout(null);

                                //---- dummyHitpointsIcon ----
                                dummyHitpointsIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Hitpoints_icon.png")));
                                dummyHitpointsPanel.add(dummyHitpointsIcon);
                                dummyHitpointsIcon.setBounds(new Rectangle(new Point(3, 3), dummyHitpointsIcon.getPreferredSize()));

                                //---- dummyHitpointsSpinner ----
                                dummyHitpointsSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyHitpointsSpinner.setModel(new SpinnerNumberModel(99, 10, 99, 1));
                                dummyHitpointsPanel.add(dummyHitpointsSpinner);
                                dummyHitpointsSpinner.setBounds(new Rectangle(new Point(30, 0), dummyHitpointsSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyHitpointsPanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyHitpointsPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyHitpointsPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyHitpointsPanel.setMinimumSize(preferredSize);
                                    dummyHitpointsPanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyHitpointsPanel);

                            //======== dummyAttackPanel ========
                            {
                                dummyAttackPanel.setBackground(null);
                                dummyAttackPanel.setLayout(null);

                                //---- dummyAttackIcon ----
                                dummyAttackIcon.setPreferredSize(new Dimension(24, 24));
                                dummyAttackIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Attack_icon.png")));
                                dummyAttackIcon.setOpaque(false);
                                dummyAttackIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyAttackPanel.add(dummyAttackIcon);
                                dummyAttackIcon.setBounds(new Rectangle(new Point(3, 0), dummyAttackIcon.getPreferredSize()));

                                //---- dummyAttackSpinner ----
                                dummyAttackSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyAttackSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                dummyAttackPanel.add(dummyAttackSpinner);
                                dummyAttackSpinner.setBounds(new Rectangle(new Point(30, 0), dummyAttackSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyAttackPanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyAttackPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyAttackPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyAttackPanel.setMinimumSize(preferredSize);
                                    dummyAttackPanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyAttackPanel);

                            //======== dummyStrengthPanel ========
                            {
                                dummyStrengthPanel.setBackground(null);
                                dummyStrengthPanel.setLayout(null);

                                //---- dummyStrengthIcon ----
                                dummyStrengthIcon.setPreferredSize(new Dimension(24, 24));
                                dummyStrengthIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Strength_icon.png")));
                                dummyStrengthIcon.setOpaque(false);
                                dummyStrengthIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyStrengthPanel.add(dummyStrengthIcon);
                                dummyStrengthIcon.setBounds(new Rectangle(new Point(3, 0), dummyStrengthIcon.getPreferredSize()));

                                //---- dummyStrengthSpinner ----
                                dummyStrengthSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyStrengthSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                dummyStrengthPanel.add(dummyStrengthSpinner);
                                dummyStrengthSpinner.setBounds(new Rectangle(new Point(30, 0), dummyStrengthSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyStrengthPanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyStrengthPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyStrengthPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyStrengthPanel.setMinimumSize(preferredSize);
                                    dummyStrengthPanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyStrengthPanel);

                            //======== dummyDefencePanel ========
                            {
                                dummyDefencePanel.setBackground(null);
                                dummyDefencePanel.setLayout(null);

                                //---- dummyDefenceIcon ----
                                dummyDefenceIcon.setPreferredSize(new Dimension(24, 24));
                                dummyDefenceIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Defence_icon.png")));
                                dummyDefenceIcon.setOpaque(false);
                                dummyDefenceIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyDefencePanel.add(dummyDefenceIcon);
                                dummyDefenceIcon.setBounds(new Rectangle(new Point(3, 0), dummyDefenceIcon.getPreferredSize()));

                                //---- dummyDefenceSpinner ----
                                dummyDefenceSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyDefenceSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                dummyDefencePanel.add(dummyDefenceSpinner);
                                dummyDefenceSpinner.setBounds(new Rectangle(new Point(30, 0), dummyDefenceSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyDefencePanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyDefencePanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyDefencePanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyDefencePanel.setMinimumSize(preferredSize);
                                    dummyDefencePanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyDefencePanel);

                            //======== dummyRangePanel ========
                            {
                                dummyRangePanel.setBackground(null);
                                dummyRangePanel.setPreferredSize(new Dimension(60, 24));
                                dummyRangePanel.setMinimumSize(new Dimension(60, 24));
                                dummyRangePanel.setLayout(null);

                                //---- dummyRangeIcon ----
                                dummyRangeIcon.setPreferredSize(new Dimension(24, 24));
                                dummyRangeIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Ranged_icon.png")));
                                dummyRangeIcon.setOpaque(false);
                                dummyRangeIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyRangePanel.add(dummyRangeIcon);
                                dummyRangeIcon.setBounds(new Rectangle(new Point(3, 0), dummyRangeIcon.getPreferredSize()));

                                //---- dummyRangeSpinner ----
                                dummyRangeSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyRangeSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                dummyRangePanel.add(dummyRangeSpinner);
                                dummyRangeSpinner.setBounds(new Rectangle(new Point(30, 0), dummyRangeSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyRangePanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyRangePanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyRangePanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyRangePanel.setMinimumSize(preferredSize);
                                    dummyRangePanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyRangePanel);

                            //======== dummyPrayerPanel ========
                            {
                                dummyPrayerPanel.setBackground(null);
                                dummyPrayerPanel.setLayout(null);

                                //---- dummyPrayerIcon ----
                                dummyPrayerIcon.setPreferredSize(new Dimension(24, 24));
                                dummyPrayerIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Prayer_icon.png")));
                                dummyPrayerIcon.setOpaque(false);
                                dummyPrayerIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyPrayerPanel.add(dummyPrayerIcon);
                                dummyPrayerIcon.setBounds(new Rectangle(new Point(3, 0), dummyPrayerIcon.getPreferredSize()));

                                //---- dummyPrayerSpinner ----
                                dummyPrayerSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyPrayerSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                dummyPrayerPanel.add(dummyPrayerSpinner);
                                dummyPrayerSpinner.setBounds(new Rectangle(new Point(30, 0), dummyPrayerSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyPrayerPanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyPrayerPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyPrayerPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyPrayerPanel.setMinimumSize(preferredSize);
                                    dummyPrayerPanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyPrayerPanel);

                            //======== dummyMagicPanel ========
                            {
                                dummyMagicPanel.setBackground(null);
                                dummyMagicPanel.setLayout(null);

                                //---- dummyMagicIcon ----
                                dummyMagicIcon.setPreferredSize(new Dimension(24, 24));
                                dummyMagicIcon.setIcon(new ImageIcon(getClass().getResource("/skills/Magic_icon.png")));
                                dummyMagicIcon.setOpaque(false);
                                dummyMagicIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                dummyMagicPanel.add(dummyMagicIcon);
                                dummyMagicIcon.setBounds(new Rectangle(new Point(3, 0), dummyMagicIcon.getPreferredSize()));

                                //---- dummyMagicSpinner ----
                                dummyMagicSpinner.setPreferredSize(new Dimension(65, 24));
                                dummyMagicSpinner.setModel(new SpinnerNumberModel(99, 1, 99, 1));
                                dummyMagicPanel.add(dummyMagicSpinner);
                                dummyMagicSpinner.setBounds(new Rectangle(new Point(30, 0), dummyMagicSpinner.getPreferredSize()));

                                {
                                    // compute preferred size
                                    Dimension preferredSize = new Dimension();
                                    for(int i = 0; i < dummyMagicPanel.getComponentCount(); i++) {
                                        Rectangle bounds = dummyMagicPanel.getComponent(i).getBounds();
                                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                                    }
                                    Insets insets = dummyMagicPanel.getInsets();
                                    preferredSize.width += insets.right;
                                    preferredSize.height += insets.bottom;
                                    dummyMagicPanel.setMinimumSize(preferredSize);
                                    dummyMagicPanel.setPreferredSize(preferredSize);
                                }
                            }
                            dummyStats.add(dummyMagicPanel);
                        }
                        dummyStatsBackground.add(dummyStats);
                        dummyStats.setBounds(10, 10, 190, 100);
                    }
                    dummyStatsPanel.add(dummyStatsBackground);
                }
                contents.add(dummyStatsPanel);

                //======== dummyEquipmentOptionsPanel ========
                {
                    dummyEquipmentOptionsPanel.setPreferredSize(new Dimension(120, 120));
                    dummyEquipmentOptionsPanel.setMinimumSize(new Dimension(120, 120));
                    dummyEquipmentOptionsPanel.setLayout(new FlowLayout());

                    //======== dummyEquipmentOptionsBox ========
                    {
                        dummyEquipmentOptionsBox.setMinimumSize(new Dimension(210, 100));
                        dummyEquipmentOptionsBox.setPreferredSize(new Dimension(210, 100));
                        dummyEquipmentOptionsBox.setLayout(new BoxLayout(dummyEquipmentOptionsBox, BoxLayout.Y_AXIS));

                        //======== optionHeaderPanel ========
                        {
                            optionHeaderPanel.setFont(new Font("Inter", Font.PLAIN, 16));
                            optionHeaderPanel.setPreferredSize(new Dimension(66, 15));
                            optionHeaderPanel.setLayout(new BorderLayout());

                            //---- optionHeaderLabel ----
                            optionHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            optionHeaderLabel.setText("Equipment");
                            optionHeaderLabel.setPreferredSize(new Dimension(66, 15));
                            optionHeaderPanel.add(optionHeaderLabel, BorderLayout.CENTER);
                        }
                        dummyEquipmentOptionsBox.add(optionHeaderPanel);

                        //======== dummyEquipmentOptions ========
                        {
                            dummyEquipmentOptions.setPreferredSize(new Dimension(340, 75));
                            dummyEquipmentOptions.setLayout(new GridLayout(2, 4, 5, 5));

                            //======== meleeEquipmentPanel ========
                            {
                                meleeEquipmentPanel.setBackground(Color.darkGray);
                                meleeEquipmentPanel.setOpaque(false);
                                meleeEquipmentPanel.setPreferredSize(new Dimension(82, 34));
                                meleeEquipmentPanel.setMinimumSize(new Dimension(82, 30));
                                meleeEquipmentPanel.setLayout(new BorderLayout());

                                //---- meleeButton ----
                                meleeButton.setText("MELEE");
                                meleeButton.setForeground(Color.red);
                                meleeButton.setFont(new Font("Inter", Font.BOLD, 13));
                                meleeButton.setBorder(LineBorder.createBlackLineBorder());
                                meleeButton.setBackground(new Color(0x333333));
                                meleeButton.setMaximumSize(new Dimension(82, 34));
                                meleeButton.setMinimumSize(new Dimension(82, 34));
                                meleeButton.setPreferredSize(new Dimension(82, 34));
                                meleeEquipmentPanel.add(meleeButton, BorderLayout.NORTH);
                            }
                            dummyEquipmentOptions.add(meleeEquipmentPanel);

                            //======== rangeEquipmentPanel ========
                            {
                                rangeEquipmentPanel.setBackground(Color.darkGray);
                                rangeEquipmentPanel.setOpaque(false);
                                rangeEquipmentPanel.setPreferredSize(new Dimension(82, 34));
                                rangeEquipmentPanel.setLayout(new BorderLayout(5, 5));

                                //---- rangeButton ----
                                rangeButton.setText("RANGE");
                                rangeButton.setForeground(new Color(0x33ff00));
                                rangeButton.setFont(new Font("Inter", Font.BOLD, 13));
                                rangeButton.setBorder(LineBorder.createBlackLineBorder());
                                rangeButton.setBackground(new Color(0x333333));
                                rangeEquipmentPanel.add(rangeButton, BorderLayout.CENTER);
                            }
                            dummyEquipmentOptions.add(rangeEquipmentPanel);

                            //======== magicEquipmentPanel ========
                            {
                                magicEquipmentPanel.setBackground(Color.darkGray);
                                magicEquipmentPanel.setOpaque(false);
                                magicEquipmentPanel.setLayout(new BorderLayout());

                                //---- magicButton ----
                                magicButton.setText("MAGIC");
                                magicButton.setForeground(new Color(0x0066ff));
                                magicButton.setFont(new Font("Inter", Font.BOLD, 13));
                                magicButton.setBorder(LineBorder.createBlackLineBorder());
                                magicButton.setBackground(new Color(0x333333));
                                magicEquipmentPanel.add(magicButton, BorderLayout.CENTER);
                            }
                            dummyEquipmentOptions.add(magicEquipmentPanel);

                            //======== specEquipmentPanel ========
                            {
                                specEquipmentPanel.setBackground(Color.darkGray);
                                specEquipmentPanel.setOpaque(false);
                                specEquipmentPanel.setLayout(new BorderLayout());

                                //---- specButton ----
                                specButton.setText("SPEC");
                                specButton.setForeground(Color.yellow);
                                specButton.setFont(new Font("Inter", Font.BOLD, 13));
                                specButton.setBorder(LineBorder.createBlackLineBorder());
                                specButton.setBackground(new Color(0x333333));
                                specEquipmentPanel.add(specButton, BorderLayout.CENTER);
                            }
                            dummyEquipmentOptions.add(specEquipmentPanel);
                        }
                        dummyEquipmentOptionsBox.add(dummyEquipmentOptions);
                    }
                    dummyEquipmentOptionsPanel.add(dummyEquipmentOptionsBox);
                }
                contents.add(dummyEquipmentOptionsPanel);
                contents.add(separator2);

                //======== equipmentPanel ========
                {
                    equipmentPanel.setLayout(new BoxLayout(equipmentPanel, BoxLayout.Y_AXIS));

                    //======== equipmentOptions ========
                    {
                        equipmentOptions.setMinimumSize(new Dimension(150, 85));
                        equipmentOptions.setPreferredSize(new Dimension(150, 85));
                        equipmentOptions.setMaximumSize(new Dimension(32767, 85));
                        equipmentOptions.setLayout(new FlowLayout());

                        //======== optionButtons ========
                        {
                            optionButtons.setLayout(new BoxLayout(optionButtons, BoxLayout.Y_AXIS));

                            //---- addEquippedButton ----
                            addEquippedButton.setText("ADD EQUIPPED");
                            addEquippedButton.setForeground(new Color(0x3e9a3e));
                            addEquippedButton.setFont(new Font("Inter", Font.BOLD, 13));
                            addEquippedButton.setMaximumSize(new Dimension(140, 34));
                            addEquippedButton.setMinimumSize(new Dimension(140, 34));
                            addEquippedButton.setPreferredSize(new Dimension(140, 34));
                            addEquippedButton.setBorder(LineBorder.createBlackLineBorder());
                            addEquippedButton.setBackground(new Color(0x333333));
                            optionButtons.add(addEquippedButton);

                            //---- clearButton ----
                            clearButton.setText("CLEAR");
                            clearButton.setForeground(new Color(0xc53535));
                            clearButton.setFont(new Font("Inter", Font.BOLD, 13));
                            clearButton.setPreferredSize(new Dimension(140, 34));
                            clearButton.setMaximumSize(new Dimension(140, 34));
                            clearButton.setMinimumSize(new Dimension(140, 34));
                            clearButton.setBorder(LineBorder.createBlackLineBorder());
                            clearButton.setBackground(new Color(0x333333));
                            optionButtons.add(clearButton);
                        }
                        equipmentOptions.add(optionButtons);
                    }
                    equipmentPanel.add(equipmentOptions);
                }
                contents.add(equipmentPanel);

                //======== equipmentListPanel ========
                {
                    equipmentListPanel.setPreferredSize(new Dimension(200, 400));
                    equipmentListPanel.setMinimumSize(new Dimension(26, 400));
                    equipmentListPanel.setLayout(new FlowLayout());

                    //---- selectedEquipment ----
                    selectedEquipment.setHorizontalAlignment(SwingConstants.CENTER);
                    selectedEquipment.setText("MELEE");
                    selectedEquipment.setPreferredSize(new Dimension(66, 15));
                    equipmentListPanel.add(selectedEquipment);

                    //======== equipmentScrollPanel ========
                    {
                        equipmentScrollPanel.setMaximumSize(new Dimension(300, 400));
                        equipmentScrollPanel.setMinimumSize(new Dimension(16, 400));
                        equipmentScrollPanel.setPreferredSize(new Dimension(200, 400));
                        equipmentScrollPanel.setBorder(LineBorder.createBlackLineBorder());

                        //======== equipmentContent ========
                        {
                            equipmentContent.setPreferredSize(new Dimension(195, 35));
                            equipmentContent.setMaximumSize(new Dimension(195, 35));
                            equipmentContent.setMinimumSize(new Dimension(198, 35));
                            equipmentContent.setLayout(new BoxLayout(equipmentContent, BoxLayout.Y_AXIS));

                            //======== equipmentItem ========
                            {
                                equipmentItem.setPreferredSize(new Dimension(395, 60));
                                equipmentItem.setBorder(LineBorder.createGrayLineBorder());
                                equipmentItem.setBackground(new Color(0x333333));
                                equipmentItem.setMaximumSize(new Dimension(395, 35));
                                equipmentItem.setLayout(null);

                                //---- icon ----
                                icon.setIcon(new ImageIcon(getClass().getResource("/skull_blue.png")));
                                icon.setHorizontalTextPosition(SwingConstants.CENTER);
                                icon.setHorizontalAlignment(SwingConstants.CENTER);
                                equipmentItem.add(icon);
                                icon.setBounds(5, 5, 25, 25);

                                //---- itemNameLabel ----
                                itemNameLabel.setText("Dragon Scimitar");
                                itemNameLabel.setFont(new Font("Inter", Font.PLAIN, 11));
                                equipmentItem.add(itemNameLabel);
                                itemNameLabel.setBounds(new Rectangle(new Point(35, 5), itemNameLabel.getPreferredSize()));

                                //---- equipmentSlotLabel ----
                                equipmentSlotLabel.setText("Main-Hand");
                                equipmentSlotLabel.setFont(new Font("Inter", Font.PLAIN, 8));
                                equipmentSlotLabel.setForeground(new Color(0x999999));
                                equipmentSlotLabel.setHorizontalAlignment(SwingConstants.LEFT);
                                equipmentItem.add(equipmentSlotLabel);
                                equipmentSlotLabel.setBounds(35, 18, 99, 12);

                                //---- removeEquipmentButton ----
                                removeEquipmentButton.setText("-");
                                removeEquipmentButton.setPreferredSize(new Dimension(20, 20));
                                removeEquipmentButton.setBorder(new LineBorder(Color.red));
                                removeEquipmentButton.setForeground(Color.red);
                                equipmentItem.add(removeEquipmentButton);
                                removeEquipmentButton.setBounds(new Rectangle(new Point(170, 7), removeEquipmentButton.getPreferredSize()));
                            }
                            equipmentContent.add(equipmentItem);
                        }
                        equipmentScrollPanel.setViewportView(equipmentContent);
                    }
                    equipmentListPanel.add(equipmentScrollPanel);
                }
                contents.add(equipmentListPanel);
            }
            scrollPanel.setViewportView(contents);
        }
        add(scrollPanel, "card1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JScrollPane scrollPanel;
    private JPanel contents;
    private JLabel header;
    private JSeparator separator1;
    private JPanel playerStatsPanel;
    private JPanel playerStatsBackground;
    private JPanel playerStats;
    private JPanel playerHeaderPanel;
    private JLabel playerHeaderLabel;
    private JPanel playerHitpointsPanel;
    private JLabel playerHitpointsIcon;
    private JSpinner playerHitpointsSpinner;
    private JPanel playerAttackPanel;
    private JLabel playerAttackIcon;
    private JSpinner playerAttackSpinner;
    private JPanel playerStrengthPanel;
    private JLabel playerStrengthIcon;
    private JSpinner playerStrengthSpinner;
    private JPanel playerDefencePanel;
    private JLabel playerDefenceIcon;
    private JSpinner playerDefenceSpinner;
    private JPanel playerRangePanel;
    private JLabel playerRangeIcon;
    private JSpinner playerRangeSpinner;
    private JPanel playerPrayerPanel;
    private JLabel playerPrayerIcon;
    private JSpinner playerPrayerSpinner;
    private JPanel playerMagicPanel;
    private JLabel playerMagicIcon;
    private JSpinner playerMagicSpinner;
    private JPanel dummyStatsPanel;
    private JPanel dummyStatsBackground;
    private JPanel dummyStats;
    private JPanel dummyHeaderPanel;
    private JLabel dummyHeaderLabel;
    private JPanel dummyHitpointsPanel;
    private JLabel dummyHitpointsIcon;
    private JSpinner dummyHitpointsSpinner;
    private JPanel dummyAttackPanel;
    private JLabel dummyAttackIcon;
    private JSpinner dummyAttackSpinner;
    private JPanel dummyStrengthPanel;
    private JLabel dummyStrengthIcon;
    private JSpinner dummyStrengthSpinner;
    private JPanel dummyDefencePanel;
    private JLabel dummyDefenceIcon;
    private JSpinner dummyDefenceSpinner;
    private JPanel dummyRangePanel;
    private JLabel dummyRangeIcon;
    private JSpinner dummyRangeSpinner;
    private JPanel dummyPrayerPanel;
    private JLabel dummyPrayerIcon;
    private JSpinner dummyPrayerSpinner;
    private JPanel dummyMagicPanel;
    private JLabel dummyMagicIcon;
    private JSpinner dummyMagicSpinner;
    private JPanel dummyEquipmentOptionsPanel;
    private JPanel dummyEquipmentOptionsBox;
    private JPanel optionHeaderPanel;
    private JLabel optionHeaderLabel;
    private JPanel dummyEquipmentOptions;
    private JPanel meleeEquipmentPanel;
    private JButton meleeButton;
    private JPanel rangeEquipmentPanel;
    private JButton rangeButton;
    private JPanel magicEquipmentPanel;
    private JButton magicButton;
    private JPanel specEquipmentPanel;
    private JButton specButton;
    private JSeparator separator2;
    private JPanel equipmentPanel;
    private JPanel equipmentOptions;
    private JPanel optionButtons;
    private JButton addEquippedButton;
    private JButton clearButton;
    private JPanel equipmentListPanel;
    private JLabel selectedEquipment;
    private JScrollPane equipmentScrollPanel;
    private JPanel equipmentContent;
    private JPanel equipmentItem;
    private JLabel icon;
    private JLabel itemNameLabel;
    private JLabel equipmentSlotLabel;
    private JButton removeEquipmentButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
