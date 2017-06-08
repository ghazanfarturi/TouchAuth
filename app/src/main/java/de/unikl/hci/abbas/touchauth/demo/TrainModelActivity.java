package de.unikl.hci.abbas.touchauth.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;

import de.unikl.hci.abbas.touchauth.AcquireData.TouchEventLive;
import de.unikl.hci.abbas.touchauth.Parameters;
import de.unikl.hci.abbas.touchauth.R;
import de.unikl.hci.abbas.touchauth.TempData.TempData;
import de.unikl.hci.abbas.touchauth.init.TouchAuthInit;
import de.unikl.hci.abbas.touchauth.utils.FileUtils;

public class TrainModelActivity extends TouchAuthActivity {

    public static final String TAG = "TrainerActivity";

    ImageView mFirstScreen;
    Button mButtonNext;
    TextView mTextView;
    boolean mFileState;
    int resourceInc = 0;

    private TouchAuthInit mTouchAuthInit = null;

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
        setContentView(R.layout.activity_train_model);

        mFirstScreen = (ImageView) findViewById(R.id.mlFirstScreen);
        mFirstScreen.setImageResource(R.drawable.circle_blakish);

        LinearLayout ll = (LinearLayout) findViewById(R.id.mlPaintLayout);
        View view = new PaintView(this);
        ll.addView(view);

        mButtonNext = (Button) findViewById(R.id.mlbtnNext);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mFirstScreen.setImageResource(R.drawable.circle_grey);
            }
        });



        verifyStoragePermissions(this);

        // TODO: Train the model by collecting user data and extract features

    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirstScreen = (ImageView) findViewById(R.id.mlFirstScreen);

        Parameters.Write_FeatureVector_State = 1;
        Parameters.runing_state = true;
        System.out.println("start");
        Log.d(TAG, "Trainer Activity Started");
        mTouchAuthInit = new TouchAuthInit(new TouchEventLive());

        new Thread() {
            int num = 0;

            @Override
            public void run() {
                Log.d(TAG, "Trainer Activity Thread Run");
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

        mButtonNext = (Button) findViewById(R.id.mlbtnNext);
        mButtonNext.setOnClickListener(new View.OnClickListener() {



            int i = 2;
            @Override
            public void onClick(View v) {

                final int[] resourceId = new int[20];
                resourceId[0] = getResources().getIdentifier("circle_blakish", "drawable", getPackageName());
                resourceId[1] = getResources().getIdentifier("circle_grey", "drawable", getPackageName());
                resourceId[2] = getResources().getIdentifier("circle_orange", "drawable", getPackageName());
                resourceId[3] = getResources().getIdentifier("circle_silver", "drawable", getPackageName());
                resourceId[4] = getResources().getIdentifier("circle_yellow", "drawable", getPackageName());
                resourceId[5] = getResources().getIdentifier("square_blue", "drawable", getPackageName());
                resourceId[6] = getResources().getIdentifier("square_greenish", "drawable", getPackageName());
                resourceId[7] = getResources().getIdentifier("square_reddish", "drawable", getPackageName());
                resourceId[8] = getResources().getIdentifier("square_yellish", "drawable", getPackageName());
                resourceId[9] = getResources().getIdentifier("triangle_blue", "drawable", getPackageName());
                resourceId[10] = getResources().getIdentifier("triangle_blue_opp", "drawable", getPackageName());
                resourceId[11] = getResources().getIdentifier("triangle_green", "drawable", getPackageName());
                resourceId[12] = getResources().getIdentifier("triangle_green_opp", "drawable", getPackageName());
                resourceId[13] = getResources().getIdentifier("triangle_orange", "drawable", getPackageName());
                resourceId[14] = getResources().getIdentifier("triangle_orange_opp", "drawable", getPackageName());
                resourceId[15] = getResources().getIdentifier("triangle_purple", "drawable", getPackageName());
                resourceId[16] = getResources().getIdentifier("triangle_purple_opp", "drawable", getPackageName());
                resourceId[17] = getResources().getIdentifier("triangle_reddish", "drawable", getPackageName());
                resourceId[18] = getResources().getIdentifier("triangle_purple_opp", "drawable", getPackageName());
                resourceId[19] = getResources().getIdentifier("square_pinkish", "drawable", getPackageName());

                if ((v.getId() == R.id.mlbtnNext) && i < 20) {
                    mFirstScreen.setImageResource(resourceId[i]);
                    i++;
                }

                // TODO: When i = 20 change the next shape caption to done and go to another activity
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        Parameters.runing_state = false;
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//
//
//        int action = event.getActionMasked();
//
//        switch (action) {
//            case (MotionEvent.ACTION_UP): {
//                if (resourceInc < 20) {
//                    mFirstScreen.setImageResource(resourceId[resourceInc]);
//                    resourceInc++;
//                }
//
//                return true;
//            }
////            default:
////                return super.onTouchEvent(event);
//        }
//        return false;
//    }


}
