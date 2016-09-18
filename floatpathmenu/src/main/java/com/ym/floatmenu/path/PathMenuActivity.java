package com.ym.floatmenu.path;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PathMenuActivity extends Activity {

	private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera,
			R.drawable.composer_music, R.drawable.composer_place,
			R.drawable.composer_sleep, R.drawable.composer_thought,
			R.drawable.composer_with };

	private static final int[] ITEM_DRAWABLES2 = { R.drawable.composer_camera,
		R.drawable.composer_music, R.drawable.composer_place,
		R.drawable.composer_sleep, R.drawable.composer_thought,
		R.drawable.composer_with,R.drawable.composer_with,R.drawable.composer_with,R.drawable.composer_with,R.drawable.composer_with };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_float);
		Button btnOpen = (Button) findViewById(R.id.btnOpenFloat);
		Button btnClose = (Button) findViewById(R.id.btnCloseFloat);
		btnOpen.setOnClickListener(mClickListener);
		btnClose.setOnClickListener(mClickListener);
		PathMenu pathMenu = (PathMenu) findViewById(R.id.path_menu);
		PathMenu pathMenu2 = (PathMenu) findViewById(R.id.path_menu2);
		PathMenu pathMenu3 = (PathMenu) findViewById(R.id.path_menu3);
		PathMenu pathMenu4 = (PathMenu) findViewById(R.id.path_menu4);
		PathMenu pathMenu5 = (PathMenu) findViewById(R.id.path_menu5);
		PathMenu pathMenu6 = (PathMenu) findViewById(R.id.path_menu6);
		PathMenu pathMenu7 = (PathMenu) findViewById(R.id.path_menu7);
		PathMenu pathMenu8 = (PathMenu) findViewById(R.id.path_menu8);
	
		initPathMenu(pathMenu, ITEM_DRAWABLES);
		initPathMenu(pathMenu2, ITEM_DRAWABLES);
		initPathMenu(pathMenu3, ITEM_DRAWABLES);
		initPathMenu(pathMenu4, ITEM_DRAWABLES);
		initPathMenu(pathMenu5, ITEM_DRAWABLES);
		initPathMenu(pathMenu6, ITEM_DRAWABLES);
		initPathMenu(pathMenu7, ITEM_DRAWABLES);
		initPathMenu(pathMenu8, ITEM_DRAWABLES2);
	}
	private void initPathMenu(PathMenu menu, int[] itemDrawables) {
		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(itemDrawables[i]);

			final int position = i;
			menu.addItem(item, new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(PathMenuActivity.this,
							"position:" + position, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btnOpenFloat) {
				Intent intent = new Intent(PathMenuActivity.this,
						PathMenuService.class);
				startService(intent);
			} else if (v.getId() == R.id.btnCloseFloat) {
				Intent intent = new Intent(PathMenuActivity.this,
						PathMenuService.class);
				stopService(intent);
			}
		}
	};
}
