package com.pvpdojo;

import com.pvpdojo.character.CKObject;
import com.pvpdojo.timesheet.keyframe.HealthKeyFrame;
import com.pvpdojo.timesheet.keyframe.KeyFrameType;
import com.pvpdojo.timesheet.keyframe.TextKeyFrame;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthOverlay extends Overlay {
    private static final Logger log = LoggerFactory.getLogger(HealthOverlay.class);
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final SpriteManager spriteManager;

    private final int MODEL_HEIGHT_BUFFER = 18;
    private final int Y_BUFFER = -1;
    private final int TEXT_BUFFER = -12;
    private final int X_BUFFER = -1;

    private HealthKeyFrame healthKeyFrame;
    private HealthKeyFrame playerHealthKeyFrame;

    @Inject
    private HealthOverlay(Client client, PVPDojoPlugin plugin, SpriteManager spriteManager) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        setPriority(OverlayPriority.HIGHEST);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;

    }

    public void setDummyHealthKey(HealthKeyFrame healthKeyFrame) {
        this.healthKeyFrame = healthKeyFrame;
    }

    public void setPlayerHealthKeyFrame(HealthKeyFrame playerHealthKeyFrame) {
        this.playerHealthKeyFrame = playerHealthKeyFrame;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return null;
        }

        graphics.setFont(FontManager.getRunescapeSmallFont());

        DrawDummyHealthBar(graphics);

        DrawPlayerHealthBar(graphics);

        return null;
    }

    private void DrawPlayerHealthBar(Graphics2D graphics)
    {
        if (playerHealthKeyFrame == null) {
            return;
        }

        LocalPoint lp = client.getLocalPlayer().getLocalLocation();
        if (lp == null || !lp.isInScene()) {
            return;
        }

        BufferedImage redBar = spriteManager.getSprite(playerHealthKeyFrame.getHealthbarSprite().getBackgroundSpriteID(), 0);
        if (redBar == null) {
            return;
        }

        BufferedImage greenBar = spriteManager.getSprite(playerHealthKeyFrame.getHealthbarSprite().getForegroundSpriteID(), 0);
        if (greenBar == null) {
            return;
        }

        double duration = playerHealthKeyFrame.getDuration();
        double startTick = playerHealthKeyFrame.getTick();
        double currentTick = plugin.getCurrentTick();
        if (currentTick > duration + startTick) {
            return;
        }

        double ratio = (double) playerHealthKeyFrame.getCurrentHealth() / (double) playerHealthKeyFrame.getMaxHealth();
        int barWidth = (int) (ratio * redBar.getWidth());
        if (barWidth == 0 && ratio > 0) {
            barWidth = 1;
        }

        if (barWidth > greenBar.getWidth()) {
            barWidth = greenBar.getWidth();
        }

        Model model = client.getLocalPlayer().getModel();
        if (model == null) {
            return;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        int textBuffer = 0;

        Point base = Perspective.getCanvasImageLocation(client, lp, redBar, height + MODEL_HEIGHT_BUFFER);
        if (base == null)
        {
            return;
        }

        Point p = new Point(base.getX() + X_BUFFER, base.getY() + Y_BUFFER + textBuffer);

        OverlayUtil.renderImageLocation(graphics, p, redBar);

        if (barWidth > 0)
        {
            BufferedImage subImage = greenBar.getSubimage(0, 0, barWidth, redBar.getHeight());
            OverlayUtil.renderImageLocation(graphics, p, subImage);
        }
    }

    private void DrawDummyHealthBar(Graphics2D graphics)
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


        if (!dummy.dummyCharacter.isActive()) {
            return;
        }

        if (healthKeyFrame == null) {
            return;
        }

        CharacterObject characterObject = dummy.dummyCharacter.getCharacterObject();
        if (!characterObject.isActive()) {
            return;
        }

        LocalPoint lp = characterObject.getLocation();
        if (lp == null || !lp.isInScene()) {
            return;
        }

        BufferedImage redBar = spriteManager.getSprite(healthKeyFrame.getHealthbarSprite().getBackgroundSpriteID(), 0);
        if (redBar == null) {
            return;
        }

        BufferedImage greenBar = spriteManager.getSprite(healthKeyFrame.getHealthbarSprite().getForegroundSpriteID(), 0);
        if (greenBar == null) {
            return;
        }

        double duration = healthKeyFrame.getDuration();
        double startTick = healthKeyFrame.getTick();
        double currentTick = plugin.getCurrentTick();
        if (currentTick > duration + startTick) {
            return;
        }

        double ratio = (double) healthKeyFrame.getCurrentHealth() / (double) healthKeyFrame.getMaxHealth();
        int barWidth = (int) (ratio * redBar.getWidth());
        if (barWidth == 0 && ratio > 0) {
            barWidth = 1;
        }

        if (barWidth > greenBar.getWidth()) {
            barWidth = greenBar.getWidth();
        }

        Model model = characterObject.getModel();
        if (model == null) {
            return;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        int textBuffer = 0;
        /*TextKeyFrame textKeyFrame = (TextKeyFrame) dummy.dummyCharacter.getCurrentKeyFrame(KeyFrameType.TEXT);
        if (textKeyFrame != null) {
            double textDuration = textKeyFrame.getDuration();
            double textStartTick = textKeyFrame.getTick();
            if (currentTick <= textDuration + textStartTick) {
                textBuffer = TEXT_BUFFER;
            }
        }*/

        Point base = Perspective.getCanvasImageLocation(client, lp, redBar, height + MODEL_HEIGHT_BUFFER);
        if (base == null)
        {
            return;
        }

        Point p = new Point(base.getX() + X_BUFFER, base.getY() + Y_BUFFER + textBuffer);

        OverlayUtil.renderImageLocation(graphics, p, redBar);

        if (barWidth > 0)
        {
            BufferedImage subImage = greenBar.getSubimage(0, 0, barWidth, redBar.getHeight());
            OverlayUtil.renderImageLocation(graphics, p, subImage);
        }
    }
}