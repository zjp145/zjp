package com.zhang.sqone.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.R;
import com.zhang.sqone.utils.AppUtil;
import com.zhang.sqone.utilss.SystemStatusManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SyisActivity extends BaseActivity {

    @Bind(R.id.saomiao_h5)
    WebView saomiaoH5;
    private String codedContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_syis);
        ButterKnife.bind(this);
        codedContent = getIntent().getStringExtra("codedContent");
        Log.i("zhang", "返回值：" + codedContent);
        saomiaoH5.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        saomiaoH5.setWebChromeClient(new WebChromeClient());
        //支持javascript
        saomiaoH5.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        saomiaoH5.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        saomiaoH5.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        saomiaoH5.getSettings().setUseWideViewPort(true);
        saomiaoH5.getSettings().setDomStorageEnabled(true);
        //自适应屏幕
        saomiaoH5.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        saomiaoH5.getSettings().setLoadWithOverviewMode(true);
        saomiaoH5.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        saomiaoH5.setDownloadListener(new MyDownloadStart());
        if (AppUtil.isWeb(codedContent)) {
            saomiaoH5.loadUrl(codedContent);

        } else {
            saomiaoH5.loadDataWithBaseURL("", codedContent, "text/html", "UTF-8", "");
        }

//
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && saomiaoH5.canGoBack()) {
            saomiaoH5.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyDownloadStart implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            // TODO Auto-generated method stub
            //调用自己的下载方式
//          new HttpThread(url).start();
            //调用系统浏览器下载
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        }

    }
}
