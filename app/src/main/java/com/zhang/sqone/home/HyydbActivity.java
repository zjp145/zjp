package com.zhang.sqone.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.bean.Meetingroom;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.TitleBarView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 会议室预定的表形式
 *
 * @author ZJP
 *         created at 16/3/28 上午11:33
 */
public class HyydbActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.huiyi_yuding)
    TitleBarView huiyiYuding;
    @Bind(R.id.huiyi_shenqingren)
    TextView huiyiShenqingren;
    @Bind(R.id.huiyishi)
    TextView huiyishi;
    @Bind(R.id.huiyi_huiyishimc)
    TextView huiyiHuiyishimc;
    @Bind(R.id.huiyi_shijian)
    TextView huiyiShijian;
    @Bind(R.id.huiyi_chuxiren)
    TextView huiyiChuxiren;
    @Bind(R.id.huiyi_canhuiren)
    TextView huiyiCanhuiren;
    @Bind(R.id.huiyi_fuwuyaoqu)
    TextView huiyiFuwuyaoqu;
    @Bind(R.id.huiyi_miaoshu1)
    TextView huiyiMiaoshu1;
    @Bind(R.id.hy_button)
    Button hyButton;
    @Bind(R.id.huiyi_mingchengl)
    LinearLayout huiyiMingchengl;
    @Bind(R.id.huiyi_chuxirenl)
    LinearLayout huiyiChuxirenl;
    @Bind(R.id.huiyi_canhuirenl)
    LinearLayout huiyiCanhuirenl;
    @Bind(R.id.huiyi_fuwuyaoqul)
    LinearLayout huiyiFuwuyaoqul;
    @Bind(R.id.huiyi_miaoshul)
    LinearLayout huiyiMiaoshul;
    /**会议室名称*/
    private String hysMc;
    /**会议使用时间*/
    private String hySj;
    private String strFromAct2;
    private Bundle bundle2;
    /**申请人*/
    private String rqr ;
    /**会议名称*/
    private String hymc;
    /**出席人*/
    private String cxr;
    /**出席人id*/
    private String cxrid;
    /**参会人*/
    private String chr;
    /**参会人id*/
    private String chrid;
    /**服务要求*/
    private String fwyp;
    /**会议描述*/
    private String hyms;
    /**会议室id*/
    private String hyid;
    /**会议是上午还是下午*/
    private int hysj;
    private String hySj1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_hyydb);
        ButterKnife.bind(this);
        if (User.nc.equals("")) {
            huiyiShenqingren.setText(User.sid);
            rqr=User.sid;
        } else {
            huiyiShenqingren.setText(User.nc);
            rqr = User.nc;
        }
        hyid = getIntent().getStringExtra("hyid");
        hysMc = getIntent().getStringExtra("mc");
        hySj = getIntent().getStringExtra("time");
        hySj1 = getIntent().getStringExtra("time1");
        hysj = getIntent().getIntExtra("noon",0);

        huiyishi.setText(hysMc);
        huiyiShijian.setText(hySj);
        onClickes();

    }

    private void onClickes() {
        huiyiMingchengl.setOnClickListener(this);
        huiyiCanhuirenl.setOnClickListener(this);
        huiyiChuxirenl.setOnClickListener(this);
        huiyiFuwuyaoqul.setOnClickListener(this);
        huiyiMiaoshul.setOnClickListener(this);
        hyButton.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.huiyi_mingchengl:
                Intent intent = new Intent();
                intent.setClass(HyydbActivity.this, HymcActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.huiyi_chuxirenl:
                Intent intent1 = new Intent();
                intent1.putExtra("bzf","1");
                intent1.setClass(HyydbActivity.this, TxlActivity.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.huiyi_fuwuyaoqul:
                Intent intent4 = new Intent();
                intent4.setClass(HyydbActivity.this, FwyqActivity.class);
                startActivityForResult(intent4, 0);
                break;
            case R.id.huiyi_miaoshul:
                Intent intent2 = new Intent();
                intent2.setClass(HyydbActivity.this, HymsActivity.class);
                startActivityForResult(intent2, 0);
                break;
            case R.id.huiyi_canhuirenl:
                Intent intent3 = new Intent();
                intent3.putExtra("bzf","2");
                intent3.setClass(HyydbActivity.this, TxlActivity.class);
                startActivityForResult(intent3, 0);
                break;
            case R.id.hy_button:
                Tj();
               break;

        }

    }

    private void Tj(){
        Meetingroom.ReqMeetingRoom.BookMeeting.Builder reserveMap =  Meetingroom.ReqMeetingRoom.BookMeeting.newBuilder();
        reserveMap.setSqr(rqr).setMn(hysMc).setHn(hymc).setDt(hySj1).setChryc(chrid).setChryn(chr).setCxryc(cxrid).setCxryn(cxr).setFwyq(fwyp).setHyms(hyms).setHysid(hyid).setNoon(hysj + "").setSqrcode(User.sid);
        final Meetingroom.ReqMeetingRoom reqDocument = Meetingroom.ReqMeetingRoom.newBuilder().setSid(User.sid).setBm(reserveMap).setAc("HYSYY").build();
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
                        Toast.makeText(HyydbActivity.this, index.getMsg(),
                                Toast.LENGTH_SHORT).show();
                        if(index.getScd().equals("0")){
                            finish();
                        }


                    }  else {
                        Toast.makeText(HyydbActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(HyydbActivity.this, Globals.HYAP_URI, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode) {
            if (!(data==null)){
                bundle2 = data.getBundleExtra("bundle2");
                 strFromAct2 = bundle2.getString("strResult");
            }
            if (5 == resultCode) {
                if(!strFromAct2.equals("")){
                    huiyiHuiyishimc.setVisibility(View.VISIBLE);
                    huiyiHuiyishimc.setText(strFromAct2);
                    hymc = strFromAct2;

                }else{
                    huiyiHuiyishimc.setVisibility(View.GONE);
                }
            }

            if (1 == resultCode) {

                if(!strFromAct2.equals("")){
                    huiyiMiaoshu1.setVisibility(View.VISIBLE);
                    huiyiMiaoshu1.setText(strFromAct2);
                    hyms = strFromAct2;
                }else{
                    huiyiMiaoshu1.setVisibility(View.GONE);
                }
            }
            if (2== resultCode){
                if(!strFromAct2.equals("")){
                    huiyiChuxiren.setVisibility(View.VISIBLE);
                    huiyiChuxiren.setText(strFromAct2);
                    cxr = strFromAct2;
                    cxrid = bundle2.getString("strResult2");

                }else{
                    huiyiChuxiren.setVisibility(View.GONE);
                }
            }
            if (3== resultCode){
                if(!strFromAct2.equals("")){
                    huiyiCanhuiren.setVisibility(View.VISIBLE);
                    huiyiCanhuiren.setText(strFromAct2);
                    chr = strFromAct2;
                    chrid = bundle2.getString("strResult2");

                }else{
                    huiyiCanhuiren.setVisibility(View.GONE);
                }
            }
            if (4== resultCode){

                if(!strFromAct2.equals("")){
                    huiyiFuwuyaoqu.setVisibility(View.VISIBLE);
                    huiyiFuwuyaoqu.setText(strFromAct2);
                    fwyp = strFromAct2;
                }else{
                    huiyiFuwuyaoqu.setVisibility(View.GONE);
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
