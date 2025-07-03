package com.pvpdojo.character;

import com.pvpdojo.animation.AnimationType;
import com.pvpdojo.animation.CKAnimationController;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class CKObject extends RuneLiteObjectController
{
    private static final Logger log = LoggerFactory.getLogger(CKObject.class);
    private final Client client;
    private Model baseModel;
    private boolean freeze;
    private boolean playing;
    private boolean hasAnimKeyFrame = false;
    private boolean despawnOnFinish;
    private Animation activeAnimation;
    private CKAnimationController animationController;
    private CKAnimationController poseAnimationController;

    public CKObject(Client client)
    {
        this.client = client;
    }

    private int startCycle;

    public void setModel(Model baseModel)
    {
        this.baseModel = baseModel;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public void setHasAnimKeyFrame(boolean hasAnimKeyFrame) {
        this.hasAnimKeyFrame = hasAnimKeyFrame;
    }

    public void setDespawnOnFinish(boolean despawnOnFinish) {
        this.despawnOnFinish = despawnOnFinish;
    }

    public Shape getConvexHull(Client client)
    {
        var model = getModel();
        var localX = getLocation().getX();
        var localY = getLocation().getY();
        if (model == null)
            return null;

        GeneralPath path = new GeneralPath();

        float[] verticesX = model.getVerticesX();
        float[] verticesY = model.getVerticesY();
        float[] verticesZ = model.getVerticesZ();

        int[] faceA = model.getFaceIndices1();
        int[] faceB = model.getFaceIndices2();
        int[] faceC = model.getFaceIndices3();

        for (int i = 0; i < faceA.length; i++)
        {
            float ax = verticesX[faceA[i]];
            float ay = verticesY[faceA[i]];
            float az = verticesZ[faceA[i]];

            float bx = verticesX[faceB[i]];
            float by = verticesY[faceB[i]];
            float bz = verticesZ[faceB[i]];

            float cx = verticesX[faceC[i]];
            float cy = verticesY[faceC[i]];
            float cz = verticesZ[faceC[i]];

            Point p1 = Perspective.localToCanvas(client, (int)(localX + ax), (int)(localY + az), (int)ay);
            Point p2 = Perspective.localToCanvas(client, (int)(localX + bx), (int)(localY + bz), (int)by);
            Point p3 = Perspective.localToCanvas(client, (int)(localX + cx), (int)(localY + cz), (int)cy);

            if (p1 != null && p2 != null && p3 != null)
            {
                path.moveTo(p1.getX(), p1.getY());
                path.lineTo(p2.getX(), p2.getY());
                path.lineTo(p3.getX(), p3.getY());
                path.closePath();
            }
        }

        return path;
    }

    public Shape getModelScreenExtents(Client client)
    {
        WorldView worldView = client.getTopLevelWorldView();
        int modelHeight = Perspective.getTileHeight(client, getLocation(), worldView.getPlane());
        var model = getModel();

        var localX = getLocation().getX();
        var localY = getLocation().getY();

        if (model == null)
            return null;

        float[] verticesX = model.getVerticesX();
        float[] verticesY = model.getVerticesY();
        float[] verticesZ = model.getVerticesZ();

        List<Point> screenPoints = new ArrayList<Point>();

        for (int i = 0; i < verticesX.length; i++)
        {
            float vx = localX + verticesX[i];
            float vy = localY + verticesZ[i];
            float vz = verticesY[i] + modelHeight;

            Point canvasPoint = Perspective.localToCanvas(client, (int)vx, (int)vy, (int)vz);
            if (canvasPoint != null)
            {
                screenPoints.add(canvasPoint);
            }
        }

        if (screenPoints.isEmpty())
            return null;

        // Compute convex hull of the projected points
        Polygon hull = computeConvexHull(screenPoints);
        return hull;
    }

    public Polygon computeConvexHull(List<Point> points)
    {
        points.sort(Comparator.comparing(Point::getX).thenComparing(Point::getY));
        List<Point> upper = new ArrayList<>();
        List<Point> lower = new ArrayList<>();

        for (Point p : points)
        {
            while (upper.size() >= 2 && cross(upper.get(upper.size() - 2), upper.get(upper.size() - 1), p) <= 0)
                upper.remove(upper.size() - 1);
            upper.add(p);
        }

        for (int i = points.size() - 1; i >= 0; i--)
        {
            Point p = points.get(i);
            while (lower.size() >= 2 && cross(lower.get(lower.size() - 2), lower.get(lower.size() - 1), p) <= 0)
                lower.remove(lower.size() - 1);
            lower.add(p);
        }

        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);

        Polygon polygon = new Polygon();
        for (Point p : upper)
            polygon.addPoint(p.getX(), p.getY());
        for (Point p : lower)
            polygon.addPoint(p.getX(), p.getY());

        return polygon;
    }

    private int cross(Point o, Point a, Point b)
    {
        return (a.getX() - o.getX()) * (b.getY() - o.getY()) - (a.getY() - o.getY()) * (b.getX() - o.getX());
    }


    @Override
    public void setLocation(LocalPoint point, int level)
    {
        boolean needReregister = isActive() && point.getWorldView() != getWorldView();
        if (needReregister)
        {
            setActive(false);
        }

        super.setLocation(point, level);
        setZ(Perspective.getTileHeight(client, point, level));

        if (needReregister)
        {
            setActive(true);
        }
    }

    public void setupAnimController(AnimationType type, int animId)
    {
        if (type == AnimationType.ACTIVE)
        {
            animationController = new CKAnimationController(client, animId, false);
            animationController.setLoop(true);
            setOnFinished(AnimationType.ACTIVE);
        }
        else
        {
            poseAnimationController = new CKAnimationController(client, animId, true);
            poseAnimationController.setLoop(true);
            setOnFinished(AnimationType.POSE);
        }
    }

    public void setupAnimController(AnimationType type, Animation animation)
    {
        if (type == AnimationType.ACTIVE)
        {
            animationController = new CKAnimationController(client, animation, false);
            animationController.setLoop(true);
            setOnFinished(AnimationType.ACTIVE);
        }
        else
        {
            poseAnimationController = new CKAnimationController(client, animation, true);
            poseAnimationController.setLoop(true);
            setOnFinished(AnimationType.POSE);
        }
    }

    private void setOnFinished(AnimationType type)
    {
        CKAnimationController ac = getController(type);
        ac.setOnFinished(e ->
        {
            if (ac.isLoop())
            {
                setActive(true);
                ac.setFinished(false);
                ac.loop();
            }
            else
            {
                ac.setFinished(true);
                setAnimation(type, -1);
            }

            if (ac.isFinished() && despawnOnFinish)
            {
                setActive(false);
            }
        });
    }

    public void setActive(boolean active)
    {
        if (active)
        {
            client.registerRuneLiteObject(this);
        }
        else
        {
            client.removeRuneLiteObject(this);
        }
    }

    public boolean isActive()
    {
        return client.isRuneLiteObjectRegistered(this);
    }

    public void setLoop(boolean loop)
    {
        if (animationController == null)
        {
            return;
        }

        animationController.setLoop(loop);
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }



    @Override
    public void tick(int ticksSinceLastFrame)
    {
        if (!playing && hasAnimKeyFrame)
        {
            return;
        }

        if (animationController == null)
        {
            setupAnimController(AnimationType.ACTIVE, -1);
        }

        if (poseAnimationController == null)
        {
            setupAnimController(AnimationType.POSE, -1);
        }

        if (!freeze)
        {
            animationController.tick(ticksSinceLastFrame);
            poseAnimationController.tick(ticksSinceLastFrame);
        }
    }

    @Override
    public Model getModel()
    {
        if (animationController != null)
        {
            return animationController.animate(this.baseModel, this.poseAnimationController);
        }
        else if (poseAnimationController != null)
        {
            return poseAnimationController.animate(this.baseModel);
        }
        else
        {
            return baseModel;
        }
    }

    public boolean isFinished()
    {
        if (animationController == null)
        {
            return true;
        }

        return animationController.isFinished();
    }

    public void setFinished(boolean finished)
    {
        if (animationController == null)
        {
            return;
        }

        animationController.setFinished(finished);
    }

    public void unsetAnimation(AnimationType type)
    {
        CKAnimationController ac = getController(type);
        if (ac == null)
        {
            setupAnimController(type, -1);
            return;
        }

        ac.setAnimation(client.loadAnimation(-1));
    }

    public void setAnimation(AnimationType type, Animation animation)
    {
        CKAnimationController ac = getController(type);
        if (ac == null)
        {
            setupAnimController(type, animation);
            return;
        }

        ac.setAnimation(animation);
    }

    public void setAnimation(AnimationType type, int animId)
    {
        CKAnimationController ac = getController(type);
        if (ac == null)
        {
            setupAnimController(type, animId);
            return;
        }

        ac.setAnimation(client.loadAnimation(animId));
    }

    public Animation[] getAnimations()
    {
        if (animationController == null)
        {
            setupAnimController(AnimationType.ACTIVE, -1);
        }

        if (poseAnimationController == null)
        {
            setupAnimController(AnimationType.POSE, -1);
        }

        return new Animation[]{animationController.getAnimation(), poseAnimationController.getAnimation()};
    }

    public void setAnimationFrame(AnimationType type, int animFrame, boolean allowFreeze)
    {

        CKAnimationController ac = getController(type);
        if (ac == null)
        {
            setupAnimController(type, -1);
        }

        Animation animation = ac.getAnimation();
        if (animation == null)
        {
            return;
        }

        if (animFrame >= animation.getDuration())
        {
            animFrame = animation.getDuration() - 1;
        }

        if (allowFreeze)
        {
            if (animFrame == -1)
            {
                freeze = false;
                ac.setFrame(0);
                return;
            }

            freeze = true;
            ac.setFrame(animFrame);
            return;
        }

        freeze = false;
        ac.setFrame(animFrame);
    }

    public int getAnimationFrame(AnimationType type)
    {
        CKAnimationController ac = getController(type);
        if (ac == null)
        {
            return 0;
        }

        Animation animation = ac.getAnimation();
        if (animation == null)
        {
            return 0;
        }

        return animationController.getFrame();
    }

    public int getMaxAnimFrames(AnimationType type)
    {
        CKAnimationController ac = getController(type);
        if (ac == null)
        {
            return 0;
        }

        Animation animation = ac.getAnimation();
        if (animation == null)
        {
            return 0;
        }

        return animation.getDuration();
    }

    public int getAnimationId()
    {
        if (animationController != null)
        {
            Animation animation = animationController.getAnimation();
            if (animation != null)
            {
                return animation.getId();
            }
        }

        if (poseAnimationController != null)
        {
            Animation animation = poseAnimationController.getAnimation();
            if (animation != null)
            {
                return animation.getId();
            }
        }

        return -1;
    }

    private CKAnimationController getController(AnimationType type)
    {
        if (type == AnimationType.ACTIVE)
        {
            return animationController;
        }

        return poseAnimationController;
    }
}
