package com.msu.nasa.rmc.automation.initialization;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Class that initializes the location of the robot
 */

public class Initializer {
    private boolean noMoreImages;
    private ConcurrentLinkedQueue<Bitmap> imageQueue;
    private Integer beaconColor;
    private Integer colorThreshold;

    public Initializer(Integer beaconColor, Integer colorThreshold){
        this.noMoreImages = false;
        this.imageQueue = new ConcurrentLinkedQueue<>();
        this.beaconColor = beaconColor;
        this.colorThreshold = colorThreshold;
    }

    /**
     * Adds an image to the image queue to scan for the beacon
     *
     * @param bitmap {@link Bitmap} bitmap to search beacon for
     */
    public void addImage(Bitmap bitmap){
        imageQueue.add(bitmap);
    }

    /**
     * Tells Initializer that there are no more images to scan
     */
    public void doneAddingImages(){
        noMoreImages = true;
    }

    /**
     * Kicks off a thread for every image in the image queue to search for the beacon in every
     * image
     */
    private void run() {
        ThreadFactory threadFactory;
        while (!noMoreImages) {
            if (!imageQueue.isEmpty()) {
                Bitmap image = imageQueue.poll();
                Beacon beacon = findBeacon(image, 0, 0, image.getWidth(), image.getHeight(), false);
            }
        }
    }

    /**
     * Double binary search to find the points on the beacon
     *
     * @param bitmap
     * @param x
     * @param y
     * @param w
     * @param h
     * @param hori
     * @return
     */
    private Beacon findBeacon(Bitmap bitmap, Integer x, Integer y, Integer w, Integer h, boolean hori){
        Integer mpx = (x + w)/2;
        Integer mpy = (y + h)/2;
        if (x.equals(w))
            return;
        if (y.equals(h))
            return;


        return null;
    }

    /**
     * Scans along the given axis for the correct color and tells other threads it found the beacon
     * if it finds the beacon
     *
     * @param bitmap {@link Bitmap} bitmap to scan
     * @param start {@link Integer} start of the scanline
     * @param finish {@link Integer} end of the scanline
     * @param mp {@link Integer} point on axis to scan
     * @param hori true is x axis false if y axis
     * @return true if color is on line and false otherwise
     */
    private boolean scanline(Bitmap bitmap, Integer start, Integer finish, Integer mp, boolean hori){
        for (int i = start; i < finish; i++){
            boolean found;
            if (hori)
                found = checkColor(bitmap.getPixel(i, mp), beaconColor, colorThreshold);
            else
                found = checkColor(bitmap.getPixel(mp, i), beaconColor, colorThreshold);
            if (found) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks that all color components are within the threshold of the beacon color
     *
     * @param color {@link Integer} color to check
     * @param beaconColor {@link Integer} color of the beacon
     * @param colorThreshold {@link Integer} threshold to be within
     * @return true if all color components are within threshold false otherwise
     */
    private boolean checkColor(Integer color, Integer beaconColor, Integer colorThreshold) {
        return (
                checkColorComponent(Color.red(color), Color.red(color), colorThreshold) &&
                checkColorComponent(Color.green(color), Color.green(color), colorThreshold) &&
                checkColorComponent(Color.blue(color), Color.blue(color), colorThreshold)
        );
    }

    /**
     * Checks that the given color component is within the threshold of the beacon color
     *
     * @param colorComponent {@link Integer} color component to check
     * @param beaconColor {@link Integer} color component of the beacon
     * @param colorThreshold {@link Integer} threshold that color should be within
     * @return true if color is within threshold false otherwise
     */
    private boolean checkColorComponent(Integer colorComponent, Integer beaconColor, Integer colorThreshold) {
        return Math.abs(colorComponent-beaconColor) <= colorThreshold;
    }
}
