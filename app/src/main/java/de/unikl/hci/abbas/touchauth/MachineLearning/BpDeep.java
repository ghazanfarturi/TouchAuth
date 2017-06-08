package de.unikl.hci.abbas.touchauth.MachineLearning;

/**
 * Created by ABBAS on 5/14/2017.
 */

import java.util.Random;

public class BpDeep {
    public double[][] layer;                // Neural network nodes at each level
    public double[][] layerErr;             // Neural network nodes error
    public double[][][] layer_weight;       // Node weight of each layer
    public double[][][] layer_weight_delta; // The weight of each node node
    public double mobp;                     // Momentum coefficient
    public double rate;                     // Learning coefficient

    public BpDeep(int[] layernum, double rate, double mobp) {
        this.mobp = mobp;
        this.rate = rate;
        layer = new double[layernum.length][];
        layerErr = new double[layernum.length][];
        layer_weight = new double[layernum.length][][];
        layer_weight_delta = new double[layernum.length][][];
        Random random = new Random();
        for (int l = 0; l < layernum.length; l++) {
            layer[l] = new double[layernum[l]];
            layerErr[l] = new double[layernum[l]];
            if (l + 1 < layernum.length) {
                layer_weight[l] = new double[layernum[l] + 1][layernum[l + 1]];
                layer_weight_delta[l] = new double[layernum[l] + 1][layernum[l + 1]];
                for (int j = 0; j < layernum[l] + 1; j++)
                    for (int i = 0; i < layernum[l + 1]; i++)
                        layer_weight[l][j][i] = random.nextDouble(); // Random initialization weight
            }
        }
    }

    // Output the output forward step by step
    public double[] computeOut(double[] in) {
        for (int l = 1; l < layer.length; l++) {
            for (int j = 0; j < layer[l].length; j++) {
                double z = layer_weight[l - 1][layer[l - 1].length][j];
                for (int i = 0; i < layer[l - 1].length; i++) {
                    layer[l - 1][i] = l == 1 ? in[i] : layer[l - 1][i];
                    z += layer_weight[l - 1][i][j] * layer[l - 1][i];
                }
                layer[l][j] = 1 / (1 + Math.exp(-z));
            }
        }
        return layer[layer.length - 1];
    }

    // Layer by layer to calculate the error and modify the weight
    public void updateWeight(double[] tar) {
        int l = layer.length - 1;
        for (int j = 0; j < layerErr[l].length; j++)
            layerErr[l][j] = layer[l][j] * (1 - layer[l][j]) * (tar[j] - layer[l][j]);

        while (l-- > 0) {
            for (int j = 0; j < layerErr[l].length; j++) {
                double z = 0.0;
                for (int i = 0; i < layerErr[l + 1].length; i++) {
                    z = z + l > 0 ? layerErr[l + 1][i] * layer_weight[l][j][i] : 0;
                    layer_weight_delta[l][j][i] = mobp * layer_weight_delta[l][j][i] + rate * layerErr[l + 1][i] * layer[l][j]; // Implicit layer momentum adjustment
                    layer_weight[l][j][i] += layer_weight_delta[l][j][i]; // Implicit layer weight adjustment
                    if (j == layerErr[l].length - 1) {
                        layer_weight_delta[l][j + 1][i] = mobp * layer_weight_delta[l][j + 1][i] + rate * layerErr[l + 1][i]; // Intercept momentum adjustment
                        layer_weight[l][j + 1][i] += layer_weight_delta[l][j + 1][i]; // Intercept weight adjustment
                    }
                }
                layerErr[l][j] = z * layer[l][j] * (1 - layer[l][j]); // Record the error
            }
        }
    }

    public void train(double[] in, double[] tar) {
        double[] out = computeOut(in);
        updateWeight(tar);
    }
}
