package com.lielamar.lielsutils.bukkit.ai;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public class Pathfinder {

    public static List<Location> findRoute(Location from, Location to, byte moveOptions, int maxIter, double distance) {
        List<Location> locations = new ArrayList<>();
        Queue<PathPoint> openSet = new PriorityQueue<>();
        Map<Location, PathPoint> allNodes = new HashMap<>();

        PathPoint start = new PathPoint(from,null,0d,PathPoint.computeScore(from,to));

        allNodes.put(from,start);
        openSet.add(start);

        int i = maxIter;

        while(!openSet.isEmpty() && --i > 0) {
            PathPoint next = openSet.poll();

            if(next.current.distance(to) < distance) {
                PathPoint current = next;

                do {
                    locations.add(0,current.current);
                    current = allNodes.get(current.previous);
                } while (current != null);

                return locations;
            }

            getConnections(moveOptions,next.current,distance).forEach( location -> {
                PathPoint nextPoint = allNodes.getOrDefault(location, new PathPoint(location));
                allNodes.put(location, nextPoint);

                double newScore = next.routeScore + PathPoint.computeScore(next.current, location);

                if(newScore < nextPoint.routeScore) {
                    nextPoint.routeScore = newScore;
                    nextPoint.previous = next.current;
                    nextPoint.estimatedScore = newScore + PathPoint.computeScore(location, to);
                    openSet.add(nextPoint);
                }
            });
        }
        return locations;
    }

    public static Set<Location> getConnections(byte moveOptions, Location node, double distance) {
        Set<Location> r = new HashSet<>();

        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    Location test = node.clone().add(x * distance,y * distance,z * distance);

                    if(!test.getBlock().getType().isSolid()) {
                        if((MoveOptions.FLING.can(moveOptions)) ||
                                (MoveOptions.WALKING.can(moveOptions) && y < 1 && test.clone().add(0,-1,0).getBlock().getType().isSolid()) ||
                                (MoveOptions.DROPPING.can(moveOptions) && y < 0 && test.clone().add(0,-1,0).getBlock().getType().isSolid()) ||
                                (MoveOptions.CLAIMING.can(moveOptions) && haveSolidAround(test) && x == 0 && z == 0) ||
                                (MoveOptions.GLIDING.can(moveOptions) && y < 1) ||
                                (MoveOptions.FLOATING.can(moveOptions) && y == 0) ||
                                (MoveOptions.JUMPING.can(moveOptions) && test.clone().add(0,-1,0).getBlock().getType().isSolid()) ||
                                (MoveOptions.SWIMMING.can(moveOptions) && test.getBlock().getType() == Material.WATER)) {
                            r.add(test);
                        }
                    }
                }
            }
        }
        return r;
    }

    private static boolean haveSolidAround(Location location){
        return location.clone().add(0,0,1).getBlock().getType().isSolid() ||
                location.clone().add(0,0,-1).getBlock().getType().isSolid() ||
                location.clone().add(1,0,0).getBlock().getType().isSolid() ||
                location.clone().add(-1,0,0).getBlock().getType().isSolid();
    }


    private static class PathPoint implements Comparable<PathPoint> {

        public Location current;
        public Location previous;
        public double routeScore;
        public double estimatedScore;

        public PathPoint(Location current) {
            this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        public PathPoint(Location current, Location previous, double routeScore,double estimatedScore) {
            this.current = current;
            this.previous = previous;
            this.routeScore = routeScore;
            this.estimatedScore = estimatedScore;
        }

        public static double computeScore(Location current, Location previous) {
            return current.distance(previous);
        }

        @Override
        public int compareTo(PathPoint o) {
            return Double.compare(routeScore, o.routeScore);
        }
    }
}