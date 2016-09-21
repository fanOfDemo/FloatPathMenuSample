package com.ym.floatmenu.path.demo;

import com.ym.floatmenu.path.ArcPathMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PathMenu pathMenu = new PathMenu(this.getApplicationContext());
//        pathMenu.setVisibility(View.VISIBLE);

        ArcPathMenu pathMenu = new ArcPathMenu(this.getApplicationContext());
    }
}
