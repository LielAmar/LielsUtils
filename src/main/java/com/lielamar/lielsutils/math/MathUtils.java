package com.lielamar.lielsutils.math;

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
    public static double Distance2D(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(z1-z2, 2));
    }

    /**
     * Calculates the distance in the X, Y & Z axis using
     * the distance formula: sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)
     *
     * @param x1        First X coordinate
     * @param x2        Second X coordinate
     * @param z1        First Z coordinate
     * @param z2        Second Z coordinate
     * @param y1        First Y coordinate
     * @param y2        Second Y coordinate
     * @return          Distance
     */
    public static double Distance3D(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2));
    }

    /**
     * Formats a double variable - keeps the original number with <precision> digits after the dot
     *
     * @param number       Number to format
     * @param precision    Amount of digits after dot
     * @return             Formatted number
     */
    public static double formatDouble(double number, int precision) {
        int tmp = (int)number*(int)Math.pow(10, precision);
        return tmp/Math.pow(10, precision);
    }
}
