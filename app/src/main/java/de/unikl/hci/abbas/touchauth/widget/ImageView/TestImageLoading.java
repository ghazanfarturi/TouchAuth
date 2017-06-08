package de.unikl.hci.abbas.touchauth.widget.ImageView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Field;

import de.unikl.hci.abbas.touchauth.R;

public class TestImageLoading extends Activity {

    ImageView firstScreen = null;
    Button btnNext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_loading);

        //final ImageView qImageView = (ImageView) findViewById(R.id.firstScreen);
        //qImageView.setImageResource(R.drawable.circle_orange);

        firstScreen = (ImageView) findViewById(R.id.firstScreen);
        firstScreen.setImageResource(R.drawable.circle_orange);

        btnNext = (Button) findViewById (R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstScreen.setImageResource(R.drawable.circle_yellow);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        firstScreen = (ImageView) findViewById(R.id.firstScreen);

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

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                if ((v.getId() == R.id.btn_next) && i < 20) {
                    firstScreen.setImageResource(resourceId[i]);
                    i++;
                }
                // TODO: When i = 20 change the nextshape caption to done and go to another activity

            }
        });


    }
}
