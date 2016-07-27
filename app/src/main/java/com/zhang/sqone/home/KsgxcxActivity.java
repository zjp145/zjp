package com.zhang.sqone.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.adapter.CommonAdapter;
import com.zhang.sqone.adapter.ViewHolder;
import com.zhang.sqone.bean.Departmentworkdoc;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.pullswipe.PullToRefreshSwipeMenuListView;
import com.zhang.sqone.pullswipe.pulltorefresh.RefreshTime;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;
import com.zhang.sqone.xiangqing.BanShiXQActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 科室安排查询
 *@author ZJP
 *created at 16/4/1 下午3:49
 */
public class KsgxcxActivity extends BaseActivity implements PullToRefreshSwipeMenuListView.IXListViewListener, AbsListView.OnScrollListener, TextView.OnEditorActionListener {

    @Bind(R.id.ksgzcx_title)
    TitleBarView gwglTitle;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.keshigz_list)
    PullToRefreshSwipeMenuListView keshigzList;
    /**访问的数据集合*/
    private List<Departmentworkdoc.ReqDepartmentWorkDoc.fileMap> fileList = new ArrayList<>();

    /**控制列表中的页数*/
    private int p = 1;
    /**控制数据是加载 还是刷新*/
    private  boolean isFlsh = true;
    private boolean flsh = false;
    /**判断是不是滑动到底部*/
    private boolean isFlsh2 = false;
    private Handler mHandler;
    private CommonAdapter<Departmentworkdoc.ReqDepartmentWorkDoc.fileMap> resultAdapter;
    private String srting="";
    private List<Departmentworkdoc.ReqDepartmentWorkDoc.fileMap> fileList2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_ksgxcx);
        ButterKnife.bind(this);
        //请求数据
        regRequest(srting);

        keshigzList.setPullRefreshEnable(true);
        keshigzList.setPullLoadEnable(true);
        keshigzList.setXListViewListener(this);
        keshigzList.setOnScrollListener(this);
        mHandler = new Handler();
        searchEt.setOnEditorActionListener(this);
        //点击listview 进入详情
        keshigzList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KsgxcxActivity.this, BanShiXQActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Globals.LIDS,resultAdapter.getItem(position - 1) );
                bundle.putInt(Globals.BDID, 5);
                bundle.putString(Globals.BSF,"ks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 根据不同的标识符加载网络的数据
     */
    public void regRequest(String s ) {
        final Departmentworkdoc.ReqDepartmentWorkDoc reqDocument  = Departmentworkdoc.ReqDepartmentWorkDoc.newBuilder().setSid(User.sid).setAc("KSGZCX").setP(p+"").setTj(s).build();
        new UniversalHttp() {
            @Override
            public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                reqDocument.writeTo(outputStream);
            }

            @Override
            public <T> void inPutInterface(InputStream inputStream) throws IOException {
                Departmentworkdoc.ReqDepartmentWorkDoc index = Departmentworkdoc.ReqDepartmentWorkDoc.parseFrom(inputStream);
                Log.i("请求响应", "stu" + index.getStu()+"______"+
                        "scd" + index.getScd()+"______"+
                        "mag" + index.getMsg()
                );
                if (index.getStu() == null || !(index.getStu().equals("1"))) {
                    Toast.makeText(Globals.context,
                            Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                }else{
                    if (index.getScd().equals("1")) {
                        fileList = index.getFlistList();
                        if (flsh) {fileList2.clear();
                            fileList2.addAll(fileList);
                            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                            RefreshTime.setRefreshTime(KsgxcxActivity.this, df.format(new Date()));
                            resultAdapter.setData(fileList);
                            resultAdapter.notifyDataSetChanged();
                            keshigzList.setRefreshTime(RefreshTime.getRefreshTime(KsgxcxActivity.this));
                            keshigzList.stopRefresh();

                            keshigzList.stopLoadMore();

                        } else {
                            if (isFlsh){
                                fileList2.addAll(fileList);
                                Log.i("zhang", "列表个数" + fileList.size());
                                resultAdapter = new CommonAdapter<Departmentworkdoc.ReqDepartmentWorkDoc.fileMap>(KsgxcxActivity.this, fileList, R.layout.dbgw_litm) {
                                    @Override
                                    public void convert(ViewHolder holder, Departmentworkdoc.ReqDepartmentWorkDoc.fileMap fileMap) {

                                        holder.setText(R.id.banshi_title, fileMap.getDn())
                                                .setText(R.id.banshi_time, fileMap.getDt())
                                                .setText(R.id.banshi_name_f, fileMap.getSpna())
                                                .setText(R.id.banshi_name_d, fileMap.getAsna())
                                                .setText(R.id.banshi_tishi, fileMap.getFn());
                                /*改版*/
//                                if(fileMap.getDs().equals("已办")){
//                                    holder.setImageResource(R.id.banshi_image,R.mipmap.yiban);
//
//                                }else if(fileMap.getDs().equals("终止")){
//                                    holder.setImageResource(R.id.banshi_image,R.mipmap.zhognzhi);
//
//                                }else if(fileMap.getDs().equals("重办")){
//                                    holder.setImageResource(R.id.banshi_image,R.mipmap.chongban);
//
//                                }else if(fileMap.getDs().equals("驳回")){
//                                    holder.setImageResource(R.id.banshi_image,R.mipmap.bohui);
//
//                                }else if(fileMap.getDs().equals("取消")){
//                                    holder.setImageResource(R.id.banshi_image,R.mipmap.quxiao);
//
//                                }
                                        holder.setVisible(R.id.shoucang_iamge, false);

                                    }
                                };
                                keshigzList.setAdapter(resultAdapter);
                            }else{
                                if (fileList.size()!=0){
                                    Log.i("zhang", "列表个数" + fileList.size());
                                    fileList2.addAll(fileList);
                                    Log.i("zhang", "列表个数2" + fileList2.size());
                                    resultAdapter.setData(fileList2);
                                    resultAdapter.notifyDataSetChanged();
                                    onLoad();
                                } else {
                                    Toast.makeText(KsgxcxActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                                    onLoad();
                                }
                            }

                        }

                    } else {
                        Toast.makeText(KsgxcxActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(KsgxcxActivity.this, Globals.KSCX_URI, null);

    }
    /**模拟加载更多*/
    private void onLoad() {
        Log.i("zhang", "onLoad: ");

        keshigzList.setRefreshTime(RefreshTime.getRefreshTime(KsgxcxActivity.this));
        keshigzList.stopRefresh();
        keshigzList.stopLoadMore();

    }
    /**下拉刷新数据*/
    public void onRefresh() {
        p=1;
        Log.i("zhang", "onRefresh: ");
        flsh = true;
        isFlsh=true;

        regRequest(srting);
    }
    /**点击加载更多*/
    public void onLoadMore() {
        if(isFlsh2){

        }else{
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoad();
                }
            }, 2000);
            isFlsh2=false;
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
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                //加载更多功能的代码

                if(fileList2.size()>4){
                    p++;
                    flsh = false;
                    isFlsh= false;
                    regRequest(srting);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
        {
            Log.i("zhang", "text===="+v.getText());
            srting = v.getText().toString().trim();
            regRequest(srting);
            return true;
        }
        return false;
    }

}
