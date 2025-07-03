package com.pvpdojo;

import com.pvpdojo.character.CKObject;
import com.pvpdojo.timesheet.keyframe.HitsplatKeyFrame;
import com.pvpdojo.timesheet.keyframe.KeyFrameType;
import com.pvpdojo.timesheet.keyframe.settings.HitsplatSprite;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Model;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HitsplatOverlay extends Overlay
{
    private static final Logger log = LoggerFactory.getLogger(HitsplatOverlay.class);
    private final Client client;
    private final PVPDojoPlugin plugin;
    private final SpriteManager spriteManager;

    private final int Y_BUFFER = -1;
    private final int X_BUFFER = -1;
    private final int[][] buffers = { {0, 0}, {0, -20}, {-15, -10}, {15, -10} };

    private HitsplatKeyFrame[] dummyHitsplatFrame = new HitsplatKeyFrame[4];
    private HitsplatKeyFrame[] playerHitsplatFrames = new HitsplatKeyFrame[4];

    @Inject
    private HitsplatOverlay(Client client, PVPDojoPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        setPriority(OverlayPriority.HIGHEST);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
    }


    public void HitDummy(double tick, HitsplatSprite sprite, int damage)
    {

        var renderPos = getFreeSlot(dummyHitsplatFrame);
        dummyHitsplatFrame[renderPos] = new HitsplatKeyFrame(tick, renderPos, getHitSplatSprite(renderPos), -1, sprite, damage);
    }

    public void HitPlayer(double tick, HitsplatSprite sprite, int damage)
    {

        var renderPos = getFreeSlot(playerHitsplatFrames);
        playerHitsplatFrames[renderPos] = new HitsplatKeyFrame(tick, renderPos, getHitSplatSprite(renderPos), -1, sprite, damage);
    }

    private KeyFrameType getHitSplatSprite(int hitSplat)
    {
        switch (hitSplat)
        {
            case 0:
                return KeyFrameType.HITSPLAT_1;
            case 1:
                return KeyFrameType.HITSPLAT_2;
            case 2:
                return KeyFrameType.HITSPLAT_3;
            case 3:
                return KeyFrameType.HITSPLAT_4;
        }

        return KeyFrameType.HITSPLAT_1;
    }

    private int getFreeSlot(HitsplatKeyFrame[] frames)
    {
        for (int i = 0; i < frames.length; i++)
        {
            HitsplatKeyFrame frame = frames[i];

            if (frame == null) return i;

            double duration = frame.getDuration();
            if (duration == -1)
            {
                duration = HitsplatKeyFrame.DEFAULT_DURATION;
            }

            double startTick = frame.getTick();
            double currentTick = plugin.getCurrentTick();
            if (currentTick > duration + startTick)
            {
                return i;
            }
        }

        return 0;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return null;
        }

        BufferedImage dummySpriteBase = spriteManager.getSprite(HitsplatSprite.BLOCK.getSpriteID(), 0);
        BufferedImage playerSpriteBase = spriteManager.getSprite(HitsplatSprite.BLOCK.getSpriteID(), 0);


        displayDummyHitSplats(graphics, dummySpriteBase);
        displayPlayerHitSplats(graphics, playerSpriteBase);

        return null;
    }

    private void displayDummyHitSplats(Graphics2D graphics, BufferedImage spriteBase)
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

        CharacterObject characterObject = dummy.dummyCharacter.getCharacterObject();
        if (!characterObject.isActive())
        {
            return;
        }

        LocalPoint lp = characterObject.getLocation();
        if (lp == null || !lp.isInScene())
        {
            return;
        }

        Model model = characterObject.getModel();
        if (model == null)
        {
            return;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        Point point = Perspective.getCanvasImageLocation(client, lp, spriteBase, height / 2);
        if (point == null)
        {
            return;
        }

        for (HitsplatKeyFrame frame : dummyHitsplatFrame)
        {
            renderFrame(graphics, frame, height, point, lp);
        }
    }


    private void displayPlayerHitSplats(Graphics2D graphics, BufferedImage spriteBase)
    {


        LocalPoint lp = client.getLocalPlayer().getLocalLocation();
        if (lp == null || !lp.isInScene())
        {
            return;
        }

        Model model = client.getLocalPlayer().getModel();
        if (model == null)
        {
            return;
        }

        model.calculateBoundsCylinder();
        int height = model.getModelHeight();

        Point point = Perspective.getCanvasImageLocation(client, lp, spriteBase, height / 2);
        if (point == null)
        {
            return;
        }


        for (HitsplatKeyFrame frame : playerHitsplatFrames)
        {
            renderFrame(graphics, frame, height, point, lp);
        }
    }

    private void renderFrame(Graphics2D graphics, HitsplatKeyFrame frame, int height, Point point, LocalPoint lp)
    {
        if (frame == null)
        {
            return;
        }

        double duration = frame.getDuration();
        if (duration == -1)
        {
            duration = HitsplatKeyFrame.DEFAULT_DURATION;
        }

        double startTick = frame.getTick();
        double currentTick = plugin.getCurrentTick();
        if (currentTick > duration + startTick)
        {
            return;
        }

        HitsplatSprite sprite = frame.getSprite();
        int damage = frame.getDamage();

        renderHitsplat(graphics, sprite, height, buffers[frame.getRenderPos()][0], buffers[frame.getRenderPos()][1], point, damage, lp);
    }

    private void renderHitsplat(Graphics2D graphics, HitsplatSprite sprite, int height, int xBuffer, int yBuffer, Point base, int damage, LocalPoint lp)
    {
        if (sprite == HitsplatSprite.NONE)
        {
            return;
        }

        BufferedImage spriteImage = spriteManager.getSprite(sprite.getSpriteID(), 0);
        Point p = new Point(base.getX() + xBuffer, base.getY() + yBuffer + Y_BUFFER);
        OverlayUtil.renderImageLocation(graphics, p, spriteImage);

        String text = "" + damage;
        FontMetrics metrics = graphics.getFontMetrics();
        int textHeight = metrics.getHeight() / 2;

        Point textPoint = Perspective.getCanvasTextLocation(client, graphics, lp, text, height / 2);
        Point textP = new Point(textPoint.getX() + xBuffer + X_BUFFER, textPoint.getY() + textHeight + yBuffer + Y_BUFFER);
        OverlayUtil.renderTextLocation(graphics, textP, text, Color.WHITE);
    }
}