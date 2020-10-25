package com.lielamar.lielsutils;

import org.bukkit.util.Vector;

public class MathUtils {

    /**
     * Calculates the distances through the X & Z axis using
     * the distance formula: sqrt((x1-x2)^2 + (z1-z2)^2)
     *
     * @param x1        First X coordinate
     * @param x2        Second X coordinate
     * @param z1        First Z coordinate
     * @param z2        Second Z coordinate
     * @return          Distance
     */
    public static double XZDistance(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(z1-z2, 2));
    }

    /**
     * Calculates a yaw from 2 locations
     *
     * @param point1      First location
     * @param point2      Second location
     * @return            Yaw angle
     */
    public static float getAngle(Vector point1, Vector point2) {
        double dx = point2.getX() - point1.getX();
        double dz = point2.getZ() - point1.getZ();
        float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        if(angle < 0)
            angle += 360.0F;
        return angle;
    }

}
