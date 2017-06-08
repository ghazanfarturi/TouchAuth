package de.unikl.hci.abbas.touchauth.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import de.unikl.hci.abbas.touchauth.Parameters;
import de.unikl.hci.abbas.touchauth.R;
import de.unikl.hci.abbas.touchauth.AcquireData.TouchEventLive;
import de.unikl.hci.abbas.touchauth.init.TouchAuthInit;
import de.unikl.hci.abbas.touchauth.utils.FileUtils;
import de.unikl.hci.abbas.touchauth.widget.ProgressWheel.ProgressWheel;


public class NegativeFeatureActivity extends TouchAuthActivity {

    public static final String TAG = "NegativeFeatureActivity";

    private TouchAuthInit mTouchAuthInit = null;
    TextView mTextView;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                mTextView.setText("" + msg.arg1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negative_feature);
        mTextView = (TextView) findViewById(R.id.negativeTextView);

        verifyStoragePermissions(this);

        Log.d(TAG, "Negative Activity Created");

    }

    @Override
    protected void onStart() {
        super.onStart();

        Parameters.Write_FeatureVector_State = 1;
        Parameters.runing_state = true;
        System.out.println("start");
        Log.d(TAG, "Negative Activity Started");
        mTouchAuthInit = new TouchAuthInit(new TouchEventLive());
        //mTouchAuthInit.start();

        new Thread() {
            @Override

            public void run() {
                Log.d(TAG, "Negative Activity Thread Run");
                int prenum = 0;

                while (Parameters.runing_state) {

                    if (FileUtils.read_negative_num_state) {

                        int num = Parameters.getDatanum(FileUtils.FILE_NEGATIVE_FEATURE_NUM_NAME);

                        if (prenum != num) {

                            Message message = Message.obtain();
                            message.what = 1;
                            message.arg1 = num;
                            handler.sendMessage(message);
                            prenum = num;
                        }
                    }

                    try {
                        Thread.sleep(Parameters.RUNPERIOC);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Parameters.runing_state = false;
    }
}

