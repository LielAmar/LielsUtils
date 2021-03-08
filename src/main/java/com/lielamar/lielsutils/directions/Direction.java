package com.lielamar.lielsutils.directions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public enum Direction {

    N("north"),
    NE("north_east"),
    E("east"),
    SE("south_east"),
    S("south"),
    SW("south_west"),
    W("west"),
    NW("north_west"),
    X("location");


    private final String rawName;

    Direction(String rawName) {
        this.rawName = rawName;
    }

    public String getRawName() {
        return rawName;
    }


    /**
     * Returns the direction in which a player is relevant to a location
     *
     * @param player   Player to check
     * @param to       Destination
     * @return         Direction
     */
    public static Direction getDirectionFromLocations(Player player, Location to) {
        if(player.getWorld() != to.getWorld())
            return Direction.X;

        Vector vector = to.toVector().subtract(player.getLocation().toVector());
        Vector direction = player.getEyeLocation().getDirection();
        double angle = vector.angle(direction);
        double deg = angle * 180 / Math.PI;

        if(deg <= 22)
            return Direction.S;
        else if(deg <= 67)
            return Direction.SW;
        else if(deg <= 112)
            return Direction.E;
        else if(deg <= 157)
            return Direction.SE;
        else if(deg <= 202)
            return Direction.S;
        else if(deg <= 247)
            return Direction.SW;
        else if(deg <= 292)
            return Direction.W;
        else if(deg <= 337)
            return Direction.NW;
        return Direction.N;
    }
}
