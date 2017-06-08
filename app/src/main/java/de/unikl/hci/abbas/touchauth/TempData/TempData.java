package de.unikl.hci.abbas.touchauth.TempData;

/**
 * Created by ABBAS on 5/14/2017.
 */

import de.unikl.hci.abbas.touchauth.FeatureVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class TempData {
    public static Queue<FeatureVector> featureVectors = new LinkedList<>();

    public static FeatureVector getFeature() {
        return featureVectors.remove();
    }

    public static void clear() {
        featureVectors.clear();
    }
}
