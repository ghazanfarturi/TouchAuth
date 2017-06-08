package de.unikl.hci.abbas.touchauth.init;

/**
 * Created by ABBAS on 5/14/2017.
 */

import de.unikl.hci.abbas.touchauth.AcquireData.Generator;
import de.unikl.hci.abbas.touchauth.TouchAuth;
import de.unikl.hci.abbas.touchauth.AcquireData.Dispatcher;
import de.unikl.hci.abbas.touchauth.AcquireData.TouchEvent;
import de.unikl.hci.abbas.touchauth.MachineLearning.BpDeepClassifier;
import de.unikl.hci.abbas.touchauth.MachineLearning.KNNClassifier;
import de.unikl.hci.abbas.touchauth.MachineLearning.SVMClassifier;


public class TouchAuthInit extends TouchAuth {
    public TouchAuthInit(TouchEvent touchEvent) {
        super();
        setDispatcher(new Dispatcher(touchEvent));
//        useClassifier(new BpDeepClassifier(new int[]{25,30,2}, 0.15, 0.8));
        useClassifier(new SVMClassifier(TouchEvent.NUM_FEATURES));
//        useClassifier(new KNNClassifier(7, TouchEvent.NUM_FEATURES));
    }


}
