package com.zhang.sqone;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.zhang.sqone.utils.RequestManager;


public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RequestManager.cancelAll(this);
	}


}
