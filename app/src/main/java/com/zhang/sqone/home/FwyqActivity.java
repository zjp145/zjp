package com.zhang.sqone.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.R;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 服务要求界面（会议）
 *
 * @author ZJP
 *         created at 16/3/28 下午3:20
 */
public class FwyqActivity extends BaseActivity  {

    @Bind(R.id.fuyq_title)
    TitleBarView fuyqTitle;
    @Bind(R.id.checkbox_1)
    CheckBox checkbox1;
    @Bind(R.id.checkbox_2)
    CheckBox checkbox2;
    @Bind(R.id.checkbox_3)
    CheckBox checkbox3;
    @Bind(R.id.checkbox_4)
    CheckBox checkbox4;
    @Bind(R.id.checkbox_5)
    CheckBox checkbox5;
    @Bind(R.id.checkbox_6)
    CheckBox checkbox6;
    @Bind(R.id.checkbox_7)
    CheckBox checkbox7;
    @Bind(R.id.checkbox_8)
    CheckBox checkbox8;
    @Bind(R.id.fuyq_msh)
    EditText fuyqMsh;
    private ArrayList<String> strings = new ArrayList<>();
    private Intent intent;
    private StringBuilder stringBuilder = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_fwyq);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        intent = getIntent();
        fuyqTitle.setClickEnterButtonListener(new OnclickEnter());
        onchecks();
    }
    private class OnclickEnter implements TitleBarView.OnClickEnterButtonListener{
        @Override
        public void onClickEnterButton(View v) {
            Log.i("zhang", "tj " + strings.size());
            strings.size();

                for (int i = 0;i<strings.size();i++){
                    stringBuilder.append(strings.get(i)+",");
                }
             if(!fuyqMsh.getText().toString().trim().equals("")){
                 stringBuilder.append(fuyqMsh.getText().toString().trim()+",");
                }

                Bundle bundle2 = new Bundle();
                bundle2.putString("strResult", stringBuilder.toString());
            intent.putExtra("bundle2", bundle2);
                setResult(4, intent);
                finish();


        }
    }

    private void onchecks() {
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("照相摄影");
                }else{
                    if (strings.contains("照相摄影"))
                        strings.remove("照相摄影");
                }
            }
        });
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("摆放桌签");
                }else{
                    if (strings.contains("摆放桌签"))
                        strings.remove("摆放桌签");
                }
            }
        });
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("需要音响");
                }else{
                    if (strings.contains("需要音响"))
                        strings.remove("需要音响");
                }
            }
        });
        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("开放空调");
                }else{
                    if (strings.contains("开放空调"))
                        strings.remove("开放空调");
                }
            }
        });
        checkbox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("会议组准备茶水");
                }else{
                    if (strings.contains("会议组准备茶水"))
                        strings.remove("会议组准备茶水");
                }
            }
        });
        checkbox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("会议组准备电脑");
                }else{
                    if (strings.contains("会议组准备电脑"))
                        strings.remove("会议组准备电脑");
                }
            }
        });
        checkbox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("准备话筒");
                }else{
                    if (strings.contains("准备话筒"))
                        strings.remove("准备话筒");
                }
            }
        });
        checkbox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strings.add("准备电子屏");
                }else{
                    if (strings.contains("准备电子屏"))
                        strings.remove("准备电子屏");
                }
            }
        });

    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景
    }


}
