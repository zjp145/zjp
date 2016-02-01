package com.zhang.sqone.dbgw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.sqone.R;
/**
*程序的四大板块的代办公文
*@author ZJP
*created at 2016/2/1 13:23
*/
public class FragmentAuth extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	return inflater.inflate(R.layout.fragment_auth, container, false);
}
}
