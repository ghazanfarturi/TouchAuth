package de.unikl.hci.abbas.touchauth.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.unikl.hci.abbas.touchauth.FeatureVector;
import de.unikl.hci.abbas.touchauth.Parameters;
import de.unikl.hci.abbas.touchauth.R;
import de.unikl.hci.abbas.touchauth.AcquireData.TouchEventLive;
import de.unikl.hci.abbas.touchauth.init.TouchAuthInit;
import de.unikl.hci.abbas.touchauth.TempData.TempData;
import de.unikl.hci.abbas.touchauth.utils.FileUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;


public class DemoActivity extends TouchAuthActivity {

    public static final String TAG = "DemoActivity";

    private TouchAuthInit mTouchAuthInit = null;
    private TextView mTextView, mPositiveTextView;
    private Thread classifyThread;
    private int dScore;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mTextView.setText("First " + msg.arg2 + "Score: " + msg.arg1);
                Log.d(TAG, "Score is Computed: " + msg.arg1);
            }
            if (msg.what == 2) {
                mPositiveTextView.setText(msg.arg1 + "");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mTextView = (TextView) findViewById(R.id.classifyscore_textview);
        mPositiveTextView = (TextView) findViewById(R.id.positive_textview);

        LinearLayout ll = (LinearLayout) findViewById(R.id.paintlayout);
        View view = new PaintView(this);
        ll.addView(view);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set the write mode, write negative feature vectors into the file
        Parameters.Write_FeatureVector_State = 0;
        // Set the operating state
        Parameters.runing_state = true;
        System.out.println("start");
        mTouchAuthInit = new TouchAuthInit(new TouchEventLive());

        //mTouchAuthInit.start();

        classifyThread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (Parameters.runing_state) {
                    int prenum = 0;
                    if (FileUtils.read_positive_num_state) {
                        int num = Parameters.getDatanum(FileUtils.FILE_FEATURE_NUM_NAME);
                        if (prenum != num) {
                            Message message = Message.obtain();
                            message.what = 2;
                            message.arg1 = num;
                            handler.sendMessage(message);
                        }
                    }

                    int score = mTouchAuthInit.getScores();
                    if (score != 0) {
                        Message message = Message.obtain();
                        message.what = 1;
                        message.arg1 = score;
                        message.arg2 = ++i;
                        handler.sendMessage(message);
                        dScore = score;
                    }
                }
            }
        };
        classifyThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Parameters.runing_state = false;
        TempData.clear();
        classifyThread.interrupt();
        Log.d(TAG, "Score: " + dScore);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Score: " + dScore);
    }

}
