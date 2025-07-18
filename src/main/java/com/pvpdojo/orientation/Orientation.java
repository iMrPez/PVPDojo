package com.pvpdojo.orientation;

import net.runelite.api.coords.LocalPoint;

public class Orientation
{
    public static int orientationX(double orientation)
    {
        int direction = simplifyOrientation((int) orientation);
        if (direction == 1280 || direction == 1536 || direction == 1792)
        {
            return 1;
        }

        if (direction == 256 || direction == 512 || direction == 768)
        {
            return -1;
        }

        return 0;
    }

    public static int orientationY(double orientation)
    {
        int direction = simplifyOrientation((int) orientation);
        if (direction == 768 || direction == 1024 || direction == 1280)
        {
            return 1;
        }

        if (direction == 256 || direction == 0 || direction == 2048 || direction == 1792)
        {
            return -1;
        }

        return 0;
    }

    public static double radiansToJAngle(double radians, double changeX, double changeY)
    {
        double angle = radians * 1024 / Math.PI;
        if (changeX > 0 && changeY > 0)
        {
            angle = 1536 - angle;
            return angle;
        }

        if (changeX < 0 && changeY > 0)
        {
            angle = 512 - angle;
            return angle;
        }

        if (changeX < 0 && changeY < 0)
        {
            angle = 512 - angle;
            return angle;
        }

        if (changeX > 0 && changeY < 0)
        {
            angle = 1536 - angle;
            return angle;
        }

        if (changeX == 0 && changeY > 0)
        {
            return 1024;
        }

        if (changeX == 0 && changeY < 0)
        {
            return 0;
        }

        if (changeX > 0 && changeY == 0)
        {
            return 1536;
        }

        if (changeX < 0 && changeY == 0)
        {
            return 512;
        }

        return angle;
    }

    public static int simplifyOrientation(int orientation)
    {
        orientation = boundOrientation(orientation);

        if (orientation == 0 || orientation == 512 || orientation == 1024 || orientation == 1536 || orientation == 2048)
        {
            return orientation;
        }

        if (orientation > 0 && orientation < 512)
        {
            return 256;
        }

        if (orientation > 512 && orientation < 1024)
        {
            return 768;
        }

        if (orientation > 1024 && orientation < 1536)
        {
            return 1280;
        }

        if (orientation > 1536 && orientation < 2048)
        {
            return 1792;
        }

        return 0;
    }

    public static int subtract(int first, int second)
    {
        int product = boundOrientation(first - second);
        if (product > 1024)
        {
            return (2048 - product) * -1;
        }

        if (product < -1024)
        {
            return 2048 + product;
        }

        return product;
    }

    public static int boundOrientation(int orientation)
    {
        while (orientation >= 2048)
        {
            orientation -= 2048;
        }

        while (orientation < 0)
        {
            orientation += 2048;
        }

        return orientation;
    }

    public static double getAngleBetween(LocalPoint firstPoint, LocalPoint secondPoint)
    {
        int secondX = secondPoint.getX();
        int secondY = secondPoint.getY();
        int firstX = firstPoint.getX();
        int firstY = firstPoint.getY();
        double differenceX = secondX - firstX;
        double differenceY = secondY - firstY;
        return Orientation.radiansToJAngle(Math.atan(differenceY / differenceX), differenceX, differenceY);
    }
}