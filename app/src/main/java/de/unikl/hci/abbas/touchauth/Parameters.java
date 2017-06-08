package de.unikl.hci.abbas.touchauth;

/**
 * Created by ABBAS on 5/14/2017.
 */

import de.unikl.hci.abbas.touchauth.utils.FileUtils;

public class Parameters {
    public static final int DATANUM = 30;
    public static final long RUNPERIOC = 1000;

    // Set the operating status parameters, start the time to run, or pause
    public static boolean runing_state = true;

    /* Set the feature vector state to be written,
    0 is to obtain the feature vector to be classified or add a positive eigenvector;
    1 is to add a negative feature vector */
    public static int Write_FeatureVector_State = 0;

    public static boolean enoughData(String filename) {
        int num = FileUtils.readFeatureNum(filename);
        if (num >= Parameters.DATANUM) {
            return true;
        } else return false;
    }

    public static int getDatanum(String filename) {
        return FileUtils.readFeatureNum(filename);
    }

}
