package com.zhang.sqone.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.adapter.CommonAdapter;
import com.zhang.sqone.adapter.ViewHolder;
import com.zhang.sqone.bean.Meetingroom;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.utils.AppUtil;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.DeleteF2;
import com.zhang.sqone.views.ListViewForScrollView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择会议室预定的界面
 *
 * @author ZJP
 *         created at 16/3/28 上午11:33
 */
public class HyydActivity extends BaseActivity {

    @Bind(R.id.tiem_shangwu)
    TextView tiemShangwu;
    @Bind(R.id.shangwu_list)
    ListViewForScrollView shangwuList;
    @Bind(R.id.tiem_xiawu)
    TextView tiemXiawu;
    @Bind(R.id.xiawu_list)
    ListViewForScrollView xiawuList;
    @Bind(R.id.time_weeks)
    TextView timeWeeks;
    @Bind(R.id.time_weekx)
    TextView timeWeekx;
    private List<Meetingroom.ReqMeetingRoom.LastMap> zhang;
    private List<Meetingroom.ReqMeetingRoom.FreeMeetMap> shangwu;
    private List<Meetingroom.ReqMeetingRoom.FreeMeetMap> xiawu;
    private CommonAdapter<Meetingroom.ReqMeetingRoom.FreeMeetMap> ja;
    private String time;
    private CommonAdapter<Meetingroom.ReqMeetingRoom.FreeMeetMap> ja2;
    private boolean f= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_hyyd);
        ButterKnife.bind(this);
        time = getIntent().getStringExtra("day");
        Log.i("zhang", "时间" + time);
        String week = AppUtil.getWeek(time);
        timeWeeks.setText("周"+week);
        timeWeekx.setText("周"+week);
        tiemShangwu.setText(time);
        tiemXiawu.setText(time);
        regRequest();
    }

    /**
     * 网络请求数据
     */
    public void regRequest() {

        final Meetingroom.ReqMeetingRoom reqDocument = Meetingroom.ReqMeetingRoom.newBuilder().setSid(User.sid).setDt(time).setAc("KXHYS").build();
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
                    if (index.getScd().equals("1")) {

                        zhang = index.getLastlistList();
                        Log.i("zhang", "zhang" + zhang.size());
                        shangwu = zhang.get(0).getFmlistList();
                        Log.i("zhang", "shangwuis");

                        if (shangwu.equals("") || shangwu == null || shangwu.size()==0) {
                            Log.i("zhang", "shangwuis3");
                            f = true;
                        } else {
                            Log.i("zhang", "shangwuis2");
                            ja = new CommonAdapter<Meetingroom.ReqMeetingRoom.FreeMeetMap>(HyydActivity.this, shangwu, R.layout.huiyiyuding_item) {
                                @Override
                                public void convert(ViewHolder holder, final Meetingroom.ReqMeetingRoom.FreeMeetMap freeMeetMap) {
                                    holder.setText(R.id.huiyiyd_title, freeMeetMap.getMn());
                                    if (freeMeetMap.getLx().equals("1")) {
                                        holder.setImageResource(R.id.iamge_hyyd, R.mipmap.huiyi_xiao);
                                    }
                                    if (freeMeetMap.getLx().equals("2")) {
                                        holder.setImageResource(R.id.iamge_hyyd, R.mipmap.huiyi_zhong);
                                    }
                                    if (freeMeetMap.getLx().equals("3")) {
                                        holder.setImageResource(R.id.iamge_hyyd, R.mipmap.huiyi_da);
                                    }
                                    if (freeMeetMap.getCa().equals("1")) {
                                        holder.setVisible(R.id.shexiangtou, true);
                                        holder.setVisible1(R.id.wushebei, false);
                                    }
                                    if (freeMeetMap.getPr().equals("1")) {
                                        holder.setVisible(R.id.touyingyi, true);
                                        holder.setVisible1(R.id.wushebei, false);
                                    }
                                    if (freeMeetMap.getSd().equals("1")) {
                                        holder.setVisible(R.id.yixiang, true);
                                        holder.setVisible1(R.id.wushebei, false);
                                    }

                                    holder.setOnClickListener(R.id.yuding_yuding, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(HyydActivity.this, HyydbActivity.class);
                                            intent.putExtra("noon",0);
                                            intent.putExtra("hyid",freeMeetMap.getId());
                                            intent.putExtra("mc", freeMeetMap.getMn());
                                            intent.putExtra("time", time + "    上午");
                                            intent.putExtra("time1", time);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                }

                            };
                            shangwuList.setAdapter(ja);
                        }
                        xiawu = zhang.get(1).getFmlistList();

                        Log.i("zhang", "xiawu" + xiawu.size());
                        if (xiawu.equals("") || xiawu == null || xiawu.size()==0) {
                            if(f){
                                DeleteF2 d = new DeleteF2() {
                                    @Override
                                    public void determineButton() {
                                        finish();
                                    }
                                }.deleteDialog(HyydActivity.this, "当前日期无空闲会议室", "", "");
                            }

                        } else {
                            ja2 = new CommonAdapter<Meetingroom.ReqMeetingRoom.FreeMeetMap>(HyydActivity.this, xiawu, R.layout.huiyiyuding_item) {
                                @Override
                                public void convert(ViewHolder holder, final Meetingroom.ReqMeetingRoom.FreeMeetMap freeMeetMap) {
                                    holder.setText(R.id.huiyiyd_title, freeMeetMap.getMn());
                                    if (freeMeetMap.getLx().equals("1")) {
                                        holder.setImageResource(R.id.iamge_hyyd, R.mipmap.huiyi_xiao);
                                    }
                                    if (freeMeetMap.getLx().equals("2")) {
                                        holder.setImageResource(R.id.iamge_hyyd, R.mipmap.huiyi_zhong);
                                    }
                                    if (freeMeetMap.getLx().equals("3")) {
                                        holder.setImageResource(R.id.iamge_hyyd, R.mipmap.huiyi_da);
                                    }
                                    if (freeMeetMap.getCa().equals("1")) {
                                        holder.setVisible(R.id.shexiangtou, true);
                                        holder.setVisible1(R.id.wushebei, false);
                                    }
                                    if (freeMeetMap.getPr().equals("1")) {
                                        holder.setVisible(R.id.touyingyi, true);
                                        holder.setVisible1(R.id.wushebei, false);
                                    }
                                    if (freeMeetMap.getSd().equals("1")) {
                                        holder.setVisible(R.id.yixiang, true);
                                        holder.setVisible1(R.id.wushebei, false);
                                    }

                                    holder.setOnClickListener(R.id.yuding_yuding, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(HyydActivity.this, HyydbActivity.class);
                                            intent.putExtra("noon",1);
                                            intent.putExtra("hyid",freeMeetMap.getId());
                                            intent.putExtra("mc", freeMeetMap.getMn());
                                            intent.putExtra("time", time + "    下午");
                                            intent.putExtra("time1", time);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            };
                            xiawuList.setAdapter(ja2);


                        }

                    } else {
                        Toast.makeText(HyydActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(HyydActivity.this, Globals.HYAP_URI, null);
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
