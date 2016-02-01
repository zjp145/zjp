package com.zhang.sqone.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhang.sqone.MainActivity;
import com.zhang.sqone.R;
import com.zhang.sqone.TianZhuMainActivity;
import com.zhang.sqone.zxing.ZxingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登陆页面
 */
public class Dengluactivity extends Activity implements View.OnClickListener {

    @Bind(R.id.delu_11)
    Button mButton;/*跳转到主界面*/
    @Bind(R.id.rwm_button)
    Button rwmButton;
    @Bind(R.id.tzsq_button)
    Button tzsqButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        ButterKnife.bind(this);
        //获得控件添加事件
        initViews();
    }

    private void initViews() {
        mButton.setOnClickListener(this);
        rwmButton.setOnClickListener(this);
        tzsqButton.setOnClickListener(this);
    }


    //点击事件的使用
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delu_11:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.rwm_button:
                Intent intent2 = new Intent(this, ZxingActivity.class);
                startActivity(intent2);
                break;
            case R.id.tzsq_button:
                Intent intent3 = new Intent(this, TianZhuMainActivity.class);
                startActivity(intent3);
                break;

        }

    }


}
