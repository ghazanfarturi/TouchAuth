package de.unikl.hci.abbas.touchauth.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import de.unikl.hci.abbas.touchauth.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LinearLayout ll = (LinearLayout) findViewById(R.id.layout1);
        View view = new PaintView(this);
        ll.addView(view);
    }
}
