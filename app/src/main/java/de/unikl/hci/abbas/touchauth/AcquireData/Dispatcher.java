package de.unikl.hci.abbas.touchauth.AcquireData;

/**
 * Created by ABBAS on 5/14/2017.
 */

import android.util.Log;

import de.unikl.hci.abbas.touchauth.FeatureVector;
import de.unikl.hci.abbas.touchauth.Parameters;
import de.unikl.hci.abbas.touchauth.TouchAuth;
import de.unikl.hci.abbas.touchauth.TempData.TempData;
import de.unikl.hci.abbas.touchauth.utils.FileUtils;

public class Dispatcher {
    private Generator mGenerator;
    private FeatureVector fv = null;
    private static int num = 0;

    public Dispatcher(Generator generator) {
        this.mGenerator = generator;
    }

    public void process(Object ev) {

        if (mGenerator.process(ev)) {

            if (Parameters.Write_FeatureVector_State == 0) {

                fv = mGenerator.getFeatureVector();
                num = Parameters.getDatanum(FileUtils.FILE_FEATURE_NUM_NAME);
                System.out.println("num:" + num + "  parameter:" + Parameters.DATANUM);

                if (num < Parameters.DATANUM) {
                    Log.i("Dispatcher Running mode" + Parameters.Write_FeatureVector_State, "Add a positive feature vector");
                    new Thread() {
                        @Override
                        public void run() {
                            FileUtils.writeFeatureVector(FileUtils.FILE_FEATUREVECTURE_NAME, fv);
                            num += 1;
                            FileUtils.writeFeatureNum(FileUtils.FILE_FEATURE_NUM_NAME, num);
                        }
                    }.start();

                } else {

                    Log.i("Dispatcher Running mode" + Parameters.Write_FeatureVector_State, "Submit a temporary classification feature");
                    TempData.featureVectors.add(fv);
                    TouchAuth.classifyFeatureVector = true;
                }

            } else {

                fv = mGenerator.getFeatureVector();
                num = Parameters.getDatanum(FileUtils.FILE_NEGATIVE_FEATURE_NUM_NAME);
                System.out.println("num:" + num + "  parameter:" + Parameters.DATANUM);

                if (num < Parameters.DATANUM) {
                    Log.i("Dispatcher Running mode" + Parameters.Write_FeatureVector_State, "Add a negative feature vector");
                    new Thread() {
                        @Override
                        public void run() {
                            FileUtils.writeFeatureVector(FileUtils.FILE_NEGATIVE_FEATURE_NAME, fv);
                            num += 1;
                            FileUtils.writeFeatureNum(FileUtils.FILE_NEGATIVE_FEATURE_NUM_NAME, num);
                        }
                    }.start();
                }
            }
        }
    }
}
