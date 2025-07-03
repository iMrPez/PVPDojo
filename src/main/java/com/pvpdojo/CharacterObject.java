package com.pvpdojo;

import com.pvpdojo.animation.AnimationType;
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

public class CharacterObject extends RuneLiteObjectController
{

    private static final Logger log = LoggerFactory.getLogger(CharacterObject.class);
    private final Client client;
    private Model baseModel;
    private boolean freeze;
    public boolean isMoving = false;
    public boolean isRunning = false;

    private CharacterAnimationController animationController;
    private WeaponAnimationData weaponAnimationData;

    public CharacterObject(Client client)
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

    public void setWeaponAnimData(WeaponAnimationData weaponAnimationData)
    {
       this.weaponAnimationData = weaponAnimationData;
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

    public void setupAnimController(int animId)
    {
        animationController = new CharacterAnimationController(client, animId, false);
        animationController.setLoop(true);
        setOnFinished();
    }

    public void setupAnimController(Animation animation)
    {
        animationController = new CharacterAnimationController(client, animation, false);
        animationController.setLoop(true);
        setOnFinished();
    }

    private void setOnFinished()
    {
        CharacterAnimationController ac = getController();
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
                setAnimation(getBaseAnimation());
                setLoop(true);
            }

            /*if (ac.isFinished() && despawnOnFinish)
            {
                setActive(false);
            }*/
        });
    }

    public int getBaseAnimation()
    {
        if (weaponAnimationData != null)
        {
            if (isMoving)
            {
                //log.info("Returning: " + (isRunning ? weaponAnimationData.runID : weaponAnimationData.walkID));
                return isRunning ? weaponAnimationData.runID : weaponAnimationData.walkID;
            }
            else
            {
                return weaponAnimationData.idleID;
            }
        }

        return 808;
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



    @Override
    public void tick(int ticksSinceLastFrame)
    {

        if (animationController == null)
        {
            setupAnimController(getBaseAnimation());
        }


        if (!freeze)
        {
            animationController.tick(ticksSinceLastFrame);
        }
    }

    @Override
    public Model getModel()
    {
        if (animationController != null)
        {
            return animationController.animate(this.baseModel);
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

    public void setAnimation(Animation animation)
    {
        CharacterAnimationController ac = getController();
        if (ac == null)
        {
            setupAnimController(animation);
            return;
        }

        ac.setAnimation(animation);
    }

    public void setAnimation(int animId)
    {
        //log.info("ID: " + animId);
        CharacterAnimationController ac = getController();
        if (ac == null)
        {
            setupAnimController( animId);
            return;
        }

        if (getAnimationId() == animId)
        {
            return;
        }

        ac.setAnimation(client.loadAnimation(animId));
    }


    public void setAnimationFrame(int animFrame, boolean allowFreeze)
    {
        //log.info("Anim Frame: " + animFrame);
        CharacterAnimationController ac = getController();
        if (ac == null)
        {
            setupAnimController(getBaseAnimation());
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

    public int getAnimationFrame()
    {
        CharacterAnimationController ac = getController();
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

    public int getDuration()
    {
        CharacterAnimationController ac = getController();
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

        return -1;
    }

    private CharacterAnimationController getController()
    {

        return animationController;
    }
}
