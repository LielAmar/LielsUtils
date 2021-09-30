package com.lielamar.lielsutils.bukkit.direction;

import org.bukkit.util.Vector;

public class DirectionUtils {

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
