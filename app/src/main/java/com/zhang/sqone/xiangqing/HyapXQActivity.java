package com.zhang.sqone.xiangqing;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zhang.sqone.utils.XiaZaiUtil;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.ListViewForScrollView;
import com.zhang.sqone.views.MasterLayout;
import com.zhang.sqone.views.TitleBarView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 会议安排详情页
 *
 * @author ZJP
 *         created at 2016/4/28 13:50
 */
public class  HyapXQActivity extends BaseActivity {

    @Bind(R.id.xq_title)
    TitleBarView xqTitle;
    //    @Bind(R.id.banshi_image)
//    ImageView banshiImage;

    @Bind(R.id.banshi_tishi)
    TextView banshiTishi;
    @Bind(R.id.banshi_time)
    TextView banshiTime;
    @Bind(R.id.banshi_moren)
    ImageView banshiMoren;
    @Bind(R.id.banshi_name_f)
    TextView banshiNameF;
    @Bind(R.id.banshi_iamge12)
    ImageView banshiIamge12;
    @Bind(R.id.banshi_name_d)
    TextView banshiNameD;
    @Bind(R.id.banshi_layout)
    LinearLayout banshiLayout;
    @Bind(R.id.shoucang_iamge)
    ImageView shoucangIamge;
    @Bind(R.id.q_xiangqing_con1)
    TextView qXiangqingCon1;
    @Bind(R.id.q_xiangqing_list)
    ListViewForScrollView qXiangqingList;
    @Bind(R.id.banshi_fj)
    TextView banshiFj;
    @Bind(R.id.hyap_cx)
    LinearLayout hyapCx;
    @Bind(R.id.bs_cy)
    TextView bsCy;
    @Bind(R.id.bs_tianshus)
    TextView bsTianshus;
    @Bind(R.id.iamge_bsf)
    LinearLayout iamgeBsf;
    @Bind(R.id.banshi_title)
    TextView banshiTitle;
    private Bundle ba;
    private Meetingroom.ReqMeetingRoom.MeetingMap fileMap;
    private String xqid;
    private List<Meetingroom.ReqMeetingRoom.FileList> zhang;
    private CommonAdapter<Meetingroom.ReqMeetingRoom.FileList> ja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_hyap_xq);
        ButterKnife.bind(this);
        hyapCx.setVisibility(View.VISIBLE);
        fileMap = (Meetingroom.ReqMeetingRoom.MeetingMap) getIntent().getSerializableExtra(Globals.LIDS);
        xqid = fileMap.getId();
        banshiTitle.setText(fileMap.getMn());
        banshiTime.setText(fileMap.getDt());
        banshiFj.setText(fileMap.getMac() + "个附件");
        banshiTishi.setText(fileMap.getDn());
            /**改版*/
            if(fileMap.getMs().equals("0")){
                iamgeBsf.setBackgroundResource(R.drawable.hy_br);
                bsTianshus.setText("审批中");
                bsCy.setVisibility(View.GONE);

            }else if (fileMap.getMs().equals("1")){
                iamgeBsf.setBackgroundResource(R.drawable.gz_br);
                bsTianshus.setText("已结束");
                bsCy.setVisibility(View.GONE);

            }else{
                iamgeBsf.setBackgroundResource(R.drawable.yd1_br);
                bsTianshus.setText("已预订");
                bsCy.setVisibility(View.GONE);
            }
        shoucangIamge.setVisibility(View.INVISIBLE);
        banshiIamge12.setVisibility(View.INVISIBLE);
        banshiNameD.setVisibility(View.INVISIBLE);
        banshiMoren.setImageResource(R.mipmap.shen);
        regRequest2();
    }

    private void regRequest2() {
        Meetingroom.ReqMeetingRoom.param param = Meetingroom.ReqMeetingRoom.param.newBuilder().setId(xqid).build();
        final Meetingroom.ReqMeetingRoom index = Meetingroom.ReqMeetingRoom.newBuilder().setSid(User.sid).setAc("HYAPXQ").setPa(param).build();
        new UniversalHttp() {
            @Override
            public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                index.writeTo(outputStream);
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
                        banshiNameF.setText(index.getPa().getSqr());
                        qXiangqingCon1.setText(index.getPa().getCon());
                        zhang = index.getPa().getFilelistList();
                        Log.i("zhang", "附件个数 " + zhang.size());
                        //创建适配器对布局控件添加属性
                        ja = new CommonAdapter<Meetingroom.ReqMeetingRoom.FileList>(HyapXQActivity.this, zhang, R.layout.xia_zai_item) {
                            @Override
                            public void convert(ViewHolder holder, Meetingroom.ReqMeetingRoom.FileList ceshi) {
                                Log.i("zhang", "下载地址 " + ceshi.getUrl() + ceshi.getDx());
                                //添加文字
                                holder.setText(R.id.xiazai_item_dx, ceshi.getName());
                                if (!ceshi.getDx().equals("")) {
                                    int i = Integer.parseInt(ceshi.getDx());
                                    int i2 = i / 1024 + 1;
                                    if (i2 > 1000) {
                                        int i3 = i2 / 1024;
                                        if (i3 == 0) {
                                            i3++;
                                        }
                                        holder.setText(R.id.wenjian_dx, i3 + "MB");

                                    } else {
                                        holder.setText(R.id.wenjian_dx, i2 + "KB");
                                    }
                                }

                                final String filename = ceshi.getName();
                                if (AppUtil.fileIsExists(Environment.getExternalStorageDirectory() + "/zhtzwj/" + filename)) {
                                    ImageButton imagebutton = holder.<ImageButton>getView(R.id.xiazai_iamge_button);
                                    MasterLayout masterLayout = holder.<MasterLayout>getView(R.id.xiazai_zx);
                                    TextView textView = holder.<TextView>getView(R.id.xiazai_sudu);
                                    masterLayout.setVisibility(View.GONE);
                                    imagebutton.setVisibility(View.VISIBLE);
                                    imagebutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = getFileIntent(new File(Environment.getExternalStorageDirectory() + "/zhtzwj/" + filename));
                                            HyapXQActivity.this.startActivity(intent);
                                        }
                                    });
                                    textView.setVisibility(View.GONE);
//                            Log.i("zhang", "有文件");

                                } else {

                                    Log.i("zhang", "没有文件");
                                }
//                        Log.i("zhang", "sd卡的路径：" + Environment.getExternalStorageDirectory().getPath()+"/"+filename);
                                //下载动画效果的帮助类  完成动态下载的效果
                                XiaZaiUtil xiaZaiUtil = new XiaZaiUtil(holder.<ImageButton>getView(R.id.xiazai_iamge_button), HyapXQActivity.this, ceshi.getUrl(), holder.<MasterLayout>getView(R.id.xiazai_zx), holder.<TextView>getView(R.id.xiazai_sudu),ceshi.getName());
//                        Log.i("zhang", ceshi.getFt());
                                String ft = ceshi.getFt();
                                if (ft.equals("doc") || ft.equals("docx")) {
                                    holder.setImageResource(R.id.xiazai_item_tp, R.mipmap.word);
                                } else if (ft.equals("txt")) {
                                    holder.setImageResource(R.id.xiazai_item_tp, R.mipmap.txt);
                                } else if (ft.equals("png")) {
                                    holder.setImageResource(R.id.xiazai_item_tp, R.mipmap.png);
                                } else if (ft.equals("xls") || ft.equals("xlsx")) {
                                    holder.setImageResource(R.id.xiazai_item_tp, R.mipmap.excel);
                                } else if (ft.equals("pdf")) {
                                    holder.setImageResource(R.id.xiazai_item_tp, R.mipmap.pdf);
                                } else if (ft.equals("ppt") || ft.equals("pptx")) {
                                    holder.setImageResource(R.id.xiazai_item_tp, R.mipmap.ppt);
                                }
                            }

                        };
                        qXiangqingList.setAdapter(ja);

                    } else {
                        Toast.makeText(HyapXQActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(HyapXQActivity.this, Globals.HYAP_URI, null);

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
     * 获取文件格式 返回的使用打开文件的intent
     */
    public Intent getFileIntent(File file) {
        //Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Log.i("tag", "type=" + type);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    /**
     * 判断文件的格式使用不同的action打开文件
     */
    private String getMIMEType(File f) {
        //如果没有匹配的打开文件的情况现在所有的程序打开文件
        String type = "*/*";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;

    }

    private final String[][] MIME_MapTable = {
            //{后缀名， MIME类型}
            {"3gp", "video/3gpp"},
            {"apk", "application/vnd.android.package-archive"},
            {"asf", "video/x-ms-asf"},
            {"avi", "video/x-msvideo"},
            {"bin", "application/octet-stream"},
            {"bmp", "image/bmp"},
            {"c", "text/plain"},
            {"class", "application/octet-stream"},
            {"conf", "text/plain"},
            {"cpp", "text/plain"},
            {"doc", "application/msword"},
            {"docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"xls", "application/vnd.ms-excel"},
            {"xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"exe", "application/octet-stream"},
            {"gif", "image/gif"},
            {"gtar", "application/x-gtar"},
            {"gz", "application/x-gzip"},
            {"h", "text/plain"},
            {"htm", "text/html"},
            {"html", "text/html"},
            {"jar", "application/java-archive"},
            {"java", "text/plain"},
            {"jpeg", "image/jpeg"},
            {"jpg", "image/jpeg"},
            {"js", "application/x-javascript"},
            {"log", "text/plain"},
            {"m3u", "audio/x-mpegurl"},
            {"m4a", "audio/mp4a-latm"},
            {"m4b", "audio/mp4a-latm"},
            {"m4p", "audio/mp4a-latm"},
            {"m4u", "video/vnd.mpegurl"},
            {"m4v", "video/x-m4v"},
            {"mov", "video/quicktime"},
            {"mp2", "audio/x-mpeg"},
            {"mp3", "audio/x-mpeg"},
            {"mp4", "video/mp4"},
            {"mpc", "application/vnd.mpohun.certificate"},
            {"mpe", "video/mpeg"},
            {"mpeg", "video/mpeg"},
            {"mpg", "video/mpeg"},
            {"mpg4", "video/mp4"},
            {"mpga", "audio/mpeg"},
            {"msg", "application/vnd.ms-outlook"},
            {"ogg", "audio/ogg"},
            {"pdf", "application/pdf"},
            {"png", "image/png"},
            {"pps", "application/vnd.ms-powerpoint"},
            {"ppt", "application/vnd.ms-powerpoint"},
            {"pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {"prop", "text/plain"},
            {"rc", "text/plain"},
            {"rmvb", "audio/x-pn-realaudio"},
            {"rtf", "application/rtf"},
            {"sh", "text/plain"},
            {"tar", "application/x-tar"},
            {"tgz", "application/x-compressed"},
            {"txt", "text/plain"},
            {"wav", "audio/x-wav"},
            {"wma", "audio/x-ms-wma"},
            {"wmv", "audio/x-ms-wmv"},
            {"wps", "application/vnd.ms-works"},
            {"xml", "text/plain"},
            {"z", "application/x-compress"},
            {"zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };


}
