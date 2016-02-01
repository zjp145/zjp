package com.zhang.sqone.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhang.sqone.R;

/**
*程序的主界面
*@author ZJP
*created at 2016/2/1 13:15
*/


public class FragmentAt extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_at, container, false);
	}

}
