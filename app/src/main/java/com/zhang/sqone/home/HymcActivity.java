package com.zhang.sqone.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.R;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 会议名称
 *
 * @author ZJP
 *         created at 16/3/28 下午2:08
 */
public class HymcActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.huiyi_mingcheng)
    TitleBarView huiyiMingcheng;
    @Bind(R.id.hy_mc_text)
    EditText hyMcText;
    @Bind(R.id.hy_mc_button)
    Button hyMcButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_hymc);
        ButterKnife.bind(this);
        hyMcButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(hyMcText.getText().toString().trim().length()>20){
            Toast.makeText(HymcActivity.this,"名称长度超过20请重新输入",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent2 = new Intent();
            intent2.setClass(HymcActivity.this, HyydbActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("strResult", hyMcText.getText().toString().trim());
            intent2.putExtra("bundle2", bundle2);
            setResult(5, intent2);
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
}
