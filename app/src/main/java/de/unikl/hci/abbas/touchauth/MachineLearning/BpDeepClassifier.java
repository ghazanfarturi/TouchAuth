package de.unikl.hci.abbas.touchauth.MachineLearning;

/**
 * Created by ABBAS on 5/14/2017.
 */

import de.unikl.hci.abbas.touchauth.FeatureVector;
import de.unikl.hci.abbas.touchauth.utils.FileUtils;

import java.util.List;

public class BpDeepClassifier extends Classifier {
    private BpDeep bpDeep;

    public BpDeepClassifier(int[] layernum, double rate, double mobp) {
        bpDeep = new BpDeep(layernum, rate, mobp);
    }

    @Override
    public boolean train(List<FeatureVector> list) {
        list = FileUtils.readFeatureVectors(FileUtils.FILE_FEATUREVECTURE_NAME);
        for (int i = 0; i < 40; ++i) {
            for (int j = 0; j < list.size(); ++j) {
                bpDeep.train(list.get(j).getAll(), new double[]{list.get(j).getClassLabel()});
            }
        }
        return true;
    }

    @Override
    public int classify(FeatureVector featureVector) {
        double score = bpDeep.computeOut(featureVector.getAll())[0];
        System.out.println("score:" + score);
        return (int) Math.round(score);
    }
}
