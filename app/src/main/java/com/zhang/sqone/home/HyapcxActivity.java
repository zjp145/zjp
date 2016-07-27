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
import com.zhang.sqone.bean.Meetingroom;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.pullswipe.PullToRefreshSwipeMenuListView;
import com.zhang.sqone.pullswipe.pulltorefresh.RefreshTime;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;
import com.zhang.sqone.xiangqing.HyapXQActivity;

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
 * 会议安排查询
 *
 * @author ZJP
 *         created at 16/3/31 上午11:35
 */
public class HyapcxActivity extends BaseActivity implements PullToRefreshSwipeMenuListView.IXListViewListener, AbsListView.OnScrollListener {

    @Bind(R.id.huiyianp_title)
    TitleBarView huiyianpTitle;
    @Bind(R.id.huiyiap_list)
    /**会议列表*/
            PullToRefreshSwipeMenuListView huiyiapList;
    /**
     * 加载数据
     */
    private Handler mHandler;

    private boolean flsh = false;
    /**
     * 科室列表的详情数据
     */
    private List<Meetingroom.ReqMeetingRoom.MeetingMap> fileList = new ArrayList<>();

    /**
     * 科室列表的适配器
     */
    private CommonAdapter<Meetingroom.ReqMeetingRoom.MeetingMap> resultAdapter;
    /**
     * 控制列表中的页数
     */
    private int p = 1;
    /**
     * 控制数据是加载 还是刷新
     */
    private boolean isFlsh = true;
    /**
     * 判断是不是滑动到底部
     */
    private boolean isFlsh2 = false;
    private List<Meetingroom.ReqMeetingRoom.MeetingMap> fileList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_hyapcx);
        ButterKnife.bind(this);
        huiyiapList.setPullRefreshEnable(true);
        huiyiapList.setPullLoadEnable(true);
        huiyiapList.setXListViewListener(this);
        huiyiapList.setOnScrollListener(this);
        regRequest();
        mHandler = new Handler();
        //点击listview 进入详情
        huiyiapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HyapcxActivity.this, HyapXQActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Globals.LIDS, resultAdapter.getItem(position - 1));
//                bundle.putInt(Globals.BDID, i);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    /**
     * 根据不同的标识符加载网络的数据
     */
    public void regRequest() {

        final Meetingroom.ReqMeetingRoom reqDocument = Meetingroom.ReqMeetingRoom.newBuilder().setSid(User.sid).setAc("HYAPCX").setP(p + "").build();
        new UniversalHttp() {
            @Override
            public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                reqDocument.writeTo(outputStream);
            }

            @Override
            public <T> void inPutInterface(InputStream inputStream) throws IOException {
                Meetingroom.ReqMeetingRoom index = Meetingroom.ReqMeetingRoom.parseFrom(inputStream);
                Log.i("请求响应", "stu" + index.getStu()+"______"+
                        "scd" + index.getScd()+"______"+
                        "mag" + index.getMsg()
                );
                if (index.getStu() == null || !(index.getStu().equals("1"))) {
                    Toast.makeText(Globals.context,
                            Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                }else{
                    if (index.getScd().equals("1")){
                        fileList = index.getMlistList();
                        if (flsh) {
                            fileList2.clear();
                            fileList2.addAll(fileList);
                            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                            RefreshTime.setRefreshTime(HyapcxActivity.this, df.format(new Date()));
                            resultAdapter.setData(fileList);
                            resultAdapter.notifyDataSetChanged();
                            huiyiapList.setRefreshTime(RefreshTime.getRefreshTime(HyapcxActivity.this));
                            huiyiapList.stopRefresh();
                            huiyiapList.stopLoadMore();
                            flsh = false;

                        } else {

                            if (isFlsh) {
                                fileList2.addAll(fileList);
                                Log.i("zhang", "列表个数---第一" + fileList.size());
                                resultAdapter = new CommonAdapter<Meetingroom.ReqMeetingRoom.MeetingMap>(HyapcxActivity.this, fileList, R.layout.dbgw_litm) {
                                    @Override
                                    public void convert(ViewHolder holder, Meetingroom.ReqMeetingRoom.MeetingMap fileMap) {
                                        holder.setVisible(R.id.hyap_cx, true);
                                        holder.setText(R.id.banshi_title, fileMap.getMn())
                                                .setText(R.id.banshi_time, fileMap.getDt())
                                                .setText(R.id.banshi_fj, fileMap.getMac())
                                                .setText(R.id.banshi_tishi, fileMap.getDn());
//                            if (i==1){

//                                holder.setImageResource(R.id.banshi_moren,R.mipmap.shen);
                                        holder.setVisible1(R.id.banshi_layout, false);
//                                holder.setVisible(R.id.banshi_name_d, false);

//                            }

                                        /**改版*/
                                        if (fileMap.getMs().equals("0")) {
                                            holder.setBackgroundRes(R.id.iamge_bsf, R.drawable.hy_br);
                                            holder.setVisible1(R.id.bs_cy, false);
                                            holder.setText(R.id.bs_tianshus, "审批中");


                                        } else if (fileMap.getMs().equals("1")) {
                                            holder.setBackgroundRes(R.id.iamge_bsf, R.drawable.gz_br);
                                            holder.setVisible1(R.id.bs_cy, false);
                                            holder.setText(R.id.bs_tianshus, "已结束");
                                        }else{
                                            holder.setBackgroundRes(R.id.iamge_bsf, R.drawable.yd1_br);
                                            holder.setVisible1(R.id.bs_cy, false);
                                            holder.setText(R.id.bs_tianshus, "已预订");
                                        }

//                            if (fileMap.getIs().equals("0")) {
                                        //隐藏
                                        holder.setVisible(R.id.shoucang_iamge, false);
//                                seti(1);
//                            } else {
//                                //显示
//                                holder.setVisible(R.id.shoucang_iamge, true);
//                                seti(3);
//                            }
                                    }
                                };
                                huiyiapList.setAdapter(resultAdapter);
                            } else {
                                if (fileList.size() != 0) {
                                    Log.i("zhang", "列表个数---第二————" + fileList.size());
                                    fileList2.addAll(fileList);
                                    Log.i("zhang", "列表个数---第三————" + fileList2.size());
                                    resultAdapter.setData(fileList2);
                                    resultAdapter.notifyDataSetChanged();
                                    onLoad();
                                } else {
                                    Toast.makeText(HyapcxActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                                    onLoad();
                                }
                            }

                        }


                    } else {
                        Toast.makeText(HyapcxActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(HyapcxActivity.this, Globals.HYAP_URI, null);

    }

    /**
     * 模拟加载更多
     */
    private void onLoad() {
        Log.i("zhang", "onLoad: ");
        huiyiapList.setRefreshTime(RefreshTime.getRefreshTime(HyapcxActivity.this));
        huiyiapList.stopRefresh();
        huiyiapList.stopLoadMore();

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

            }else{
                Toast.makeText(HyapcxActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
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

                Log.i("zhang", "onLoadMore: ");
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
