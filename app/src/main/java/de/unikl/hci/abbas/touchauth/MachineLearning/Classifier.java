package de.unikl.hci.abbas.touchauth.MachineLearning;

/**
 * Created by ABBAS on 5/14/2017.
 */

import de.unikl.hci.abbas.touchauth.FeatureVector;

import java.util.List;


public abstract class Classifier {
    public abstract boolean train(List<FeatureVector> featureVectors);

    public abstract int classify(FeatureVector featureVector);
}
