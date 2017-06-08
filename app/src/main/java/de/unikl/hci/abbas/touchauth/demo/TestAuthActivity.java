package de.unikl.hci.abbas.touchauth.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
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

import de.unikl.hci.abbas.touchauth.R;


public class TestAuthActivity extends TouchAuthActivity {

    public static final String TAG = "AuthActivity";

    private TouchAuthInit mTouchAuthInit = null;

    private TextView mTextView, mPositiveTextView;

    private Thread classifyThread;

    private int dScore;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mTextView.setText("Total Attempts: " + msg.arg2 + " Score: " + msg.arg1);
                Log.d(TAG, "Score is Computed: " + msg.arg1);

            }
            if (msg.what == 2) {
                mPositiveTextView.setText(msg.arg1 + "");
            }

            authenticate(dScore);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_auth);

        mTextView = (TextView) findViewById(R.id.classifyScoreTextView);
        mPositiveTextView = (TextView) findViewById(R.id.positiveTextView);

//        LinearLayout ll = (LinearLayout) findViewById(R.id.authPaintLayout);
//        View view = new PaintView(this);
//        ll.addView(view);

        // TODO: Allow user to perform gestures
        // TODO: popup ProgressWheel
        // TODO: classify the user
        // TODO: show result of passed or failed
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


//                    if (score > 0 || score < 0) {
//                        classifyThread.interrupt();
//                    }
                }
            }
        };
        classifyThread.start();

        //authenticate(dScore);

    }

    public void authenticate(int overallScore) {
        Intent intent;
        if (overallScore > 0) {
            mTextView.setText("AUTHENTICATED");
            intent = new Intent(TestAuthActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }

        if (overallScore < 0) {
            mTextView.setText("FAILED");
            intent = new Intent(TestAuthActivity.this, FailedActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
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
