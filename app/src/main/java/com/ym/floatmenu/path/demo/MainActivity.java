package com.ym.floatmenu.path.demo;

import com.ym.floatmenu.path.PathMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static final int[] ITEM_DRAWABLES = {R.drawable.composer_camera, R.drawable.composer_music,
            R.drawable.composer_place, R.drawable.composer_sleep, R.drawable.composer_thought, R.drawable.composer_with};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PathMenu pathMenu = new PathMenu(this.getApplicationContext());
        pathMenu.setVisibility(View.VISIBLE);
    }
}
