package com.msu.nasa.rmc.automation.initialization;

import android.graphics.Point;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class that stores the location of the corners of the beacon
 */

public class Beacon{
    private List<Point> location;

    private Comparator<Point> pointComparator = new Comparator<Point>() {
        @Override
        public int compare(Point lhs, Point rhs) {
            return lhs.x - rhs.x;
        }
    };

    public Beacon() {
        location = new CopyOnWriteArrayList<>();
    }

    /**
     * Adds suspected corner point to the beacon, removes corner points that are not true corner
     * points
     *
     * @param point {@link Point} point to add to beacon if it is a corner
     */
    public void addPoint(Point point){
        /**if (point.x < location.get(0).x)
            location.set(0, point);
            return;
        if (point.x )
        if (point.y < location.get(1).y && point.y < location.get(1).y)
            location.add(point);
        if (point.x > location.get(2).x && point.x > location.get(3).x)
            location.add(point);
        if (point.y > location.get(2).y && point.y > location.get(3).y)
            location.add(point);
        **/
    }

    /**
     * Returns the corners of the beacon in clockwise order starting with the upper left
     *
     * @return {@link List<Point>} points in clockwise order
     **/
    public List<Point> getLocation(){
        return location;
    }

    /**
     * Returns the upper left corner of the beacon
     *
     * @return {@link Point} upper left corner of beacon
     */
    public Point upperLeft(){
        return location.get(0);
    }

    /**
     * Returns the lower left corner of the beacon
     *
     * @return {@link Point} lower left corner of beacon
     */
    public Point lowerLeft(){
        return location.get(0);
    }

    /**
     * Returns the upper right corner of the beacon
     *
     * @return {@link Point} upper right corner of beacon
     */
    public Point upperRight(){
        return location.get(0);
    }

    /**
     * Returns the lower right corner of the beacon
     *
     * @return {@link Point} lower right corner of the beacon
     */
    public Point lowerRight(){
        return location.get(0);
    }
}
