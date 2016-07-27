package com.zhang.sqone.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.R;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 会议描述界面
 *
 * @author ZJP
 *         created at 16/3/28 下午1:40
 */
public class HymsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @Bind(R.id.huiyi_miaoshu)
    TitleBarView huiyiMiaoshu;
    @Bind(R.id.huiyi_miaoshu_text)
    EditText huiyiMiaoshuText;
    @Bind(R.id.huiyi_miaoshu_button)
    Button huiyiMiaoshuButton;
    @Bind(R.id.zhang_1)
    TextView zhang1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_hyms);
        ButterKnife.bind(this);
        huiyiMiaoshuButton.setOnClickListener(this);
        huiyiMiaoshuText.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(huiyiMiaoshuText.getText().toString().trim().length()>100){
            Toast.makeText(HymsActivity.this, "描述长度超过100请重新输入", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent2 = new Intent();
            intent2.setClass(HymsActivity.this, HyydbActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("strResult", huiyiMiaoshuText.getText().toString().trim());
            intent2.putExtra("bundle2", bundle2);
            setResult(1, intent2);
            finish();
        }

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        zhang1.setText(s.length()+"");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
