package com.ym.floatmenu.path.demo;

import com.ym.floatmenu.path.PathMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PathMenu pathMenu = new PathMenu(this.getApplicationContext());
        pathMenu.setVisibility(View.VISIBLE);

    }
}
