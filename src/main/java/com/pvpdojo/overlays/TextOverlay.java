package com.pvpdojo.overlays;


import com.pvpdojo.character.CharacterObject;
import com.pvpdojo.combatant.Dummy;
import com.pvpdojo.PVPDojoPlugin;
import com.pvpdojo.timesheet.keyframe.TextKeyFrame;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;

public class TextOverlay extends Overlay
{
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final int TEXT_BUFFER = 4;

    private TextKeyFrame textKeyFrame;

    private TextKeyFrame dummyAttackTicksFrame;
    private TextKeyFrame playerAttackTicksFrame;


    @Inject
    private TextOverlay(Client client, PVPDojoPlugin plugin)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
    }


    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return null;
        }

        graphics.setFont(FontManager.getRunescapeBoldFont());


        displayDummyText(graphics);
        displayPlayerText(graphics);

        return null;
    }

    private boolean displayDummyText(Graphics2D graphics) {

        Dummy dummy = plugin.dummy;
        if (dummy == null)
        {
            return false;
        }

        if (dummy.dummyCharacter == null)
        {
            return false;
        }

        if (!dummy.dummyCharacter.isActive())
        {
            return false;
        }
        CharacterObject dummyObject = dummy.dummyCharacter.getCharacterObject();
        if (!dummyObject.isActive())
        {
            return false;
        }

        LocalPoint lp = dummyObject.getLocation();
        if (lp == null || !lp.isInScene())
        {
            return false;
        }


        Model model = dummyObject.getModel();
        if (model == null)
        {
            return false;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        if (textKeyFrame != null)
        {
            String displayText = textKeyFrame.getText();
            if (displayText(graphics, textKeyFrame, lp, displayText, height))
            {
                textKeyFrame = null;
            }
        }

        if (dummyAttackTicksFrame != null)
        {
            String attackText = dummyAttackTicksFrame.getText();
            if (displayText(graphics, dummyAttackTicksFrame, lp, attackText, height / 2))
            {
                dummyAttackTicksFrame = null;
            }

        }

        return true;
    }



    private boolean displayPlayerText(Graphics2D graphics) {


        LocalPoint lp = client.getLocalPlayer().getLocalLocation();
        if (lp == null || !lp.isInScene())
        {
            return false;
        }


        Model model = client.getLocalPlayer().getModel();
        if (model == null)
        {
            return false;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        if (playerAttackTicksFrame != null)
        {
            String attackText = playerAttackTicksFrame.getText();

            if (displayText(graphics, playerAttackTicksFrame, lp, attackText, (int)((double)height * 1.25)))
            {
                playerAttackTicksFrame = null;
            }
        }

        return true;
    }

    private boolean displayText(Graphics2D graphics, TextKeyFrame kf, LocalPoint lp, String text, int height) {
        double duration = kf.getDuration();
        double startTick = kf.getTick();
        double currentTick = plugin.getCurrentTick();
        if (currentTick > duration + startTick)
        {

            return true;
        }


        Point point = Perspective.getCanvasTextLocation(client, graphics, lp, text, height);
        if (point == null)
        {
            return false;
        }

        Point p = new Point(point.getX(), point.getY() + TEXT_BUFFER);
        OverlayUtil.renderTextLocation(graphics, p, text, Color.YELLOW);
        return false;
    }

    public void setTextKeyFrame(TextKeyFrame textKeyFrame) {
        this.textKeyFrame = textKeyFrame;
    }

    public void setDummyAttackTicksFrame(TextKeyFrame dummyAttackTicksFrame) {
        this.dummyAttackTicksFrame = dummyAttackTicksFrame;
    }

    public void setPlayerAttackTicksFrame(TextKeyFrame playerAttackTicksFrame) {
        this.playerAttackTicksFrame = playerAttackTicksFrame;
    }

    public boolean hasText() {
        return textKeyFrame != null;
    }
}