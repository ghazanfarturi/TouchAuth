package de.unikl.hci.abbas.touchauth.AcquireData;

/**
 * Created by ABBAS on 5/14/2017.
 */

import de.unikl.hci.abbas.touchauth.FeatureVector;

public abstract class Generator {
    protected FeatureVector fv;

    public abstract boolean process(Object ev);

    public FeatureVector getFeatureVector() {
        return fv;
    }
}
