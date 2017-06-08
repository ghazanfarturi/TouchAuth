package de.unikl.hci.abbas.touchauth.utils;

/**
 * Created by ABBAS on 5/14/2017.
 */

import java.util.Arrays;

/**
 * Useful Array statistic functions (max, variance, percentile,...)
 * @author Aaron Atwater
 * @author Hassan Khan
 */

public class ArrayUtil {
    /**
     * Returns the maximum value in array
     * @param array	A {@code double} array
     * @return Max value in {@code array}
     * */
    public static double max(double [] array) {
        double max = Double.MIN_VALUE;
        for(int i = 0; i < array.length; i++)
            if (array[i] > max)
                max = array[i];
        return max;
    }

    /**
     * Returns variance among values in array
     * @param array	A {@code double} array
     * @return returns variance among elements of {@code array}
     */
    public static double variance(double [] array) {
        double sum = 0, mean = 0, var = 0;
        for(double d : array)
            sum += d;
        mean = sum/array.length;
        double temp = 0;
        for(double d :array)
            temp += (mean-d)*(mean-d);
        var = temp/array.length;
        return Math.sqrt(var);
    }

    /**
     * Returns the 'percentile' value in array
     * @param array	A {@code double} array
     * @param percentile The percentile value to obtain between 0-1
     * @return returns value at {@code percentile} in {@code array}
     * */
    public static double percentile(double [] array, double percentile) {
        Arrays.sort(array);
        if (array.length == 0 || percentile < 0 || percentile > 1)
            throw new IllegalArgumentException();
        double k = (array.length -1 ) * percentile;
        double f = Math.floor(k);
        double c = Math.ceil(k);
        if (f == c)
            return array[(int)k];
        return array[(int)f]* (c-k) + array[(int)c] * (k-f);
    }


}
