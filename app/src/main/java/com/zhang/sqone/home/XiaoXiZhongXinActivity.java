package com.zhang.sqone.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.adapter.CommonAdapter;
import com.zhang.sqone.adapter.ViewHolder;
import com.zhang.sqone.bean.Index;
import com.zhang.sqone.bean.Noticecontacts;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.pullswipe.PullToRefreshSwipeMenuListView;
import com.zhang.sqone.pullswipe.pulltorefresh.RefreshTime;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;

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
 * 通知公告
 *
 * @author ZJP
 *         created at 2016/4/27 15:16
 */
public class XiaoXiZhongXinActivity extends BaseActivity implements PullToRefreshSwipeMenuListView.IXListViewListener, AbsListView.OnScrollListener {

    @Bind(R.id.tzgg_list)
    PullToRefreshSwipeMenuListView tzggList;
    @Bind(R.id.xiaoxi_title)
    TitleBarView xiaoxiTitle;
    /**
     * 加载数据
     */
    private Handler mHandler;

    private boolean flsh = false;
    /**
     * 科室列表的详情数据
     */
    private List<Noticecontacts.ReqNoticeAndContacts.MessageMap> fileList = new ArrayList<>();
//
    /**
     * 科室列表的适配器
     */
    private CommonAdapter<Noticecontacts.ReqNoticeAndContacts.MessageMap> resultAdapter;
    /**
     * 控制列表中的页数
     */
    private int p = 1;
    private boolean isL = true;
    /**
     * 控制数据是加载 还是刷新
     */
    private boolean isFlsh = true;
    /**
     * 判断是不是滑动到底部
     */
    private boolean isFlsh2 = false;
    private List<Noticecontacts.ReqNoticeAndContacts.MessageMap> fileList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_tong_zhigonggao);
        ButterKnife.bind(this);
        xiaoxiTitle.setText("消息中心");
        tzggList.setPullRefreshEnable(true);
        tzggList.setPullLoadEnable(true);
        tzggList.setXListViewListener(this);
        tzggList.setOnScrollListener(this);
//        regRequest();
        mHandler = new Handler();
        //点击listview 进入详情
        tzggList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("zhang", "点击了");
//                Noticecontacts.ReqNoticeAndContacts.MessageMap.Builder messageMap = Noticecontacts.ReqNoticeAndContacts.MessageMap.newBuilder();
//                messageMap.setId(fileList2.get(position - 1).getId());
                final Noticecontacts.ReqNoticeAndContacts reqDocument = Noticecontacts.ReqNoticeAndContacts.newBuilder().setSid(User.sid).setAc("XXDQ").setId(fileList2.get(position - 1).getId()).build();
                new UniversalHttp() {
                    @Override
                    public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                        reqDocument.writeTo(outputStream);
                    }

                    @Override
                    public <T> void inPutInterface(InputStream inputStream) throws IOException {
                        Noticecontacts.ReqNoticeAndContacts index = Noticecontacts.ReqNoticeAndContacts.parseFrom(inputStream);

                        Log.i("请求响应", "stu" + index.getStu()+"______"+
                                "scd" + index.getScd()+"______"+
                                "mag" + index.getMsg()
                        );
                        if (index.getStu() == null || !(index.getStu().equals("1"))) {
                            Toast.makeText(Globals.context,
                                    Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                        }else{
                            if (index.getScd().equals("1")) {
                                if (index.getScd().equals("1")) {
                                    if (fileList2.get(position - 1).getMessagetype().equals("0")) {
                                        Intent intent5 = new Intent(XiaoXiZhongXinActivity.this, DaiBanActivity.class);
                                        intent5.putExtra(Globals.GWGL, 2);
                                        startActivity(intent5);
                                    }
                                    if (fileList2.get(position - 1).getMessagetype().equals("1")) {
                                        Intent intent21 = new Intent(XiaoXiZhongXinActivity.this, ShouziliaoActivity.class);
                                        startActivity(intent21);
                                    }
                                    if (fileList2.get(position - 1).getMessagetype().equals("2")) {
                                        Intent intent3 = new Intent(XiaoXiZhongXinActivity.this, TongZhigonggao.class);
                                        startActivity(intent3);
                                    }
                                    if (fileList2.get(position - 1).getMessagetype().equals("3")) {
                                        Intent intent3 = new Intent(XiaoXiZhongXinActivity.this, TongZhigonggao.class);
                                        startActivity(intent3);

                                    }


                                }
                            } else {
                                Toast.makeText(XiaoXiZhongXinActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }.protocolBuffer(XiaoXiZhongXinActivity.this, Globals.TZTX_URI, null);
            }
        });

    }

    /**
     * //     * 根据不同的标识符加载网络的数据
     * //
     */
    public void regRequest() {
        final Noticecontacts.ReqNoticeAndContacts reqDocument = Noticecontacts.ReqNoticeAndContacts.newBuilder().setSid(User.sid).setAc("XXLB").setP(p + "").build();
        new UniversalHttp() {
            @Override
            public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                reqDocument.writeTo(outputStream);
            }

            @Override
            public <T> void inPutInterface(InputStream inputStream) throws IOException {
                Noticecontacts.ReqNoticeAndContacts index = Noticecontacts.ReqNoticeAndContacts.parseFrom(inputStream);

                Log.i("请求响应", "stu" + index.getStu()+"______"+
                        "scd" + index.getScd()+"______"+
                        "mag" + index.getMsg()
                );
                if (index.getStu() == null || !(index.getStu().equals("1"))) {
                    Toast.makeText(Globals.context,
                            Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                }else{
                    if (index.getScd().equals("1")) {
                        if (index.getScd().equals("1")) {
                            fileList = index.getMlistList();
                            Log.i("zhang", "集合数据" + fileList.size());
                            if (flsh) {
                                fileList2.clear();
                                fileList2.addAll(fileList);
                                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                                RefreshTime.setRefreshTime(XiaoXiZhongXinActivity.this, df.format(new Date()));
                                resultAdapter.setData(fileList2);
                                resultAdapter.notifyDataSetChanged();
                                tzggList.setRefreshTime(RefreshTime.getRefreshTime(XiaoXiZhongXinActivity.this));
                                tzggList.stopRefresh();
                                tzggList.stopLoadMore();
                                flsh = false;

                            } else {
                                if (isFlsh) {
                                    fileList2.clear();
                                    fileList2.addAll(fileList);
                                    Log.i("zhang", "列表个数" + fileList.size());
                                    resultAdapter = new CommonAdapter<Noticecontacts.ReqNoticeAndContacts.MessageMap>(XiaoXiZhongXinActivity.this, fileList, R.layout.lie_biao_item) {
                                        @Override
                                        public void convert(ViewHolder holder, Noticecontacts.ReqNoticeAndContacts.MessageMap fileMap) {
                                            Log.i("zhang", "convert " + fileMap.getContent());
                                            holder.setText(R.id.lie_biao_title, fileMap.getContent());
                                            holder.setVisible1(R.id.lie_biao_iamge, false).setVisible1(R.id.lie_biao_time, false);


                                        }
                                    };
                                    tzggList.setAdapter(resultAdapter);
                                } else {
                                    if (fileList.size() != 0) {
                                        Log.i("zhang", "列表个数" + fileList.size());
                                        fileList2.addAll(fileList);
                                        Log.i("zhang", "列表个数2" + fileList2.size());
                                        resultAdapter.setData(fileList2);
                                        resultAdapter.notifyDataSetChanged();
                                        onLoad();
                                    } else {
                                        Toast.makeText(XiaoXiZhongXinActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                                        onLoad();
                                    }
                                }

                            }

                        }
                    } else {
                        Toast.makeText(XiaoXiZhongXinActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(XiaoXiZhongXinActivity.this, Globals.TZTX_URI, null);
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

    /**
     * 模拟加载更多
     */
    private void onLoad() {
        Log.i("zhang", "onLoad: ");
        tzggList.setRefreshTime(RefreshTime.getRefreshTime(XiaoXiZhongXinActivity.this));
        tzggList.stopRefresh();
        tzggList.stopLoadMore();

    }

    @Override
    protected void onResume() {
        super.onResume();
          /*加载数据*/
        p = 1;
        Log.i("zhang", "onRefresh: ");
//        if (isL){
        flsh = false;
//            isL=false;
//        }else{
//            flsh=true;
//        }

        isFlsh = true;
        regRequest();
        initLogin();
    }

    public void initLogin() {
        Log.i("zhang", "我的用户名："+User.sid);
        Index.ReqIndex.Login.Builder login = Index.ReqIndex.Login.newBuilder();
        login.setUsername(User.sid);
        //添加密码
        login.setPassword(User.pwd);
        login.setCid(User.cid);
        login.setType(User.type);
        login.setPhncode(User.mis_id);
        //Index.ReqIndex.ReqRec.Builder message = Index.ReqIndex.ReqRec.newBuilder();
        //message.setPhone("15931295549");
        //message.setPwd("15910438651");
        //message.setYzm("123456");
        final Index.ReqIndex index = Index.ReqIndex.newBuilder().setLogin(login).setAc("LOGIN").build();
        new UniversalHttp() {
            @Override
            public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                index.writeTo(outputStream);
            }

            @Override
            public <T> void inPutInterface(InputStream inputStream) throws IOException {
                Index.ReqIndex index = Index.ReqIndex.parseFrom(inputStream);
                Log.i("请求响应", "stu" + index.getStu()+"______"+
                        "scd" + index.getScd()+"______"+
                        "mag" + index.getMsg()
                );
                if (index.getStu() == null || !(index.getStu().equals("1"))) {
                    Toast.makeText(Globals.context,
                            Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                }else{
                    if(index.getScd().equals("1")){
                        //头像地址
                        User.IconPath = index.getLogin().getPh();
                        User.wcjd=index.getLogin().getWccd();
                        User.tzts=index.getLogin().getTzts();
                        User.dbts=index.getLogin().getDbts();
                        User.sfzl=index.getLogin().getZlts();
                        User.xxzx = index.getLogin().getXxzxts();
                        Log.i("zhang", "完成度"+User.wcjd);
                        Log.i("zhang", "未读通知"+User.tzts);
                        Log.i("zhang", "未读工作"+User.dbts);
                        Log.i("zhang", "资料条数"+User.sfzl);
                        Log.i("zhang","消息中心"+User.xxzx);
                    }else {
                        Toast.makeText(XiaoXiZhongXinActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(XiaoXiZhongXinActivity.this, Globals.WS_URI, null);
//        new HttpUtil() {
//            @Override
//            public <T> void analysisInputStreamData(Index.ReqIndex index) throws IOException {
//                //登陆成功
//                if(index.getScd().equals("1")){
//                    //头像地址
//                    User.IconPath = index.getLogin().getPh();
//                    User.wcjd=index.getLogin().getWccd();
//                    User.tzts=index.getLogin().getTzts();
//                    User.dbts=index.getLogin().getDbts();
//                    User.sfzl=index.getLogin().getZlts();
//                    User.xxzx = index.getLogin().getXxzxts();
//                    Log.i("zhang", "完成度"+User.wcjd);
//                    Log.i("zhang", "未读通知"+User.tzts);
//                    Log.i("zhang", "未读工作"+User.dbts);
//                    Log.i("zhang", "资料条数"+User.sfzl);
//                    Log.i("zhang","消息中心"+User.xxzx);
//
//                }
//
//            }
//        }.protocolBuffer(DaiBanActivity.this, Globals.WS_URI, index, null);

    }

    /**
     * 下拉刷新数据
     */
    public void onRefresh() {
        p = 1;
        Log.i("zhang", "onRefresh: ");
        flsh = true;
        isFlsh = true;
        regRequest();
    }

    /**
     * 点击加载更多
     */
    public void onLoadMore() {
        Log.i("zhang", "onLoadMore: ");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 2000);
        if(p==1){
            if (fileList2.size() > 4) {
                p++;

                isFlsh = false;
                regRequest();

            }
        }else{
            Toast.makeText(XiaoXiZhongXinActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                if (flsh) {

                } else {

                    if (fileList2.size() > 4) {
                        p++;

                        isFlsh = false;
                        regRequest();
                    }
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
