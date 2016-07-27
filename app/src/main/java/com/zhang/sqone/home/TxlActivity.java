package com.zhang.sqone.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.bean.Meetingroom;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.bean.Users;
import com.zhang.sqone.utils.HttpUtilDocumentwh;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.CircularImage;
import com.zhang.sqone.views.TitleBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通信录
 *
 * @author ZJP
 *         created at 16/3/28 下午5:00
 */
public class TxlActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @Bind(R.id.search_et_input)
    EditText searchEtInput;
    @Bind(R.id.txl_title)
    TitleBarView txlTitle;
    private List<Meetingroom.ReqMeetingRoom.OfficerMap> Clist;
    private MyExpandableListViewAdapter adapter;
    private ExpandableListView txlexpandableList;
    ArrayList<Users> List = new ArrayList<Users>();
    private ArrayList<String> strings = new ArrayList<>();
    private ArrayList<String> strings2 = new ArrayList<>();
    private ArrayList<String> ryid = new ArrayList<>();
    private Intent intent;
    private StringBuilder stringBuilder = new StringBuilder();
    private StringBuilder ryidstringBuilder = new StringBuilder();
    private String bzf;
    Comparator<Users> comparator = new Comparator<Users>() {
        public int compare(Users s1, Users s2) {
            //先排年龄
            if (s1.power != s2.power) {
                return s1.power - s2.power;
            } else {
                //年龄相同则按姓名排序
                if (!s1.name.equals(s2.name)) {
                    return s1.name.compareTo(s2.name);
                }
            }
            return 0;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
//        添加了一个editext自动弹出键盘解决方法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_txl);
        ButterKnife.bind(this);
        intent = getIntent();
        bzf = intent.getStringExtra("bzf");
        //请求数据
        regRequest("");
        txlexpandableList = (ExpandableListView) findViewById(R.id.txlexpandable_list);
        //设置list的展开
        txlexpandableList.setGroupIndicator(null);
        // 监听组点击
        txlexpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (Clist.get(groupPosition).getDname().isEmpty()) {
                    return true;
                }
                return false;
            }
        });
        searchEtInput.setOnEditorActionListener(this);

        // 监听每个分组里子控件的点击事件
        txlexpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                intent.putExtra(Globals.HJ_DZ_DW, Clist.get(groupPosition).getCompanyname());
//                intent.putExtra(Globals.HJ_DZ_ID, Clist.get(groupPosition).getAdresslistList().get(childPosition).getAdressid());
//                intent.putExtra(Globals.HJ_DZ_NA, Clist.get(groupPosition).getAdresslistList().get(childPosition).getAdressname());
//                setResult(Activity.RESULT_OK, intent);//返回页面1
//                finish();
                return false;
            }
        });
        txlTitle.setClickEnterButtonListener(new OnclickEnter());



    }


    static void display(ArrayList<Users> lst){
        for(Users s:lst)
            Log.i("zhang", s.getName()+"/"+s.getPower());
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
        {
            Log.i("zhang", "text===="+v.getText());
            regRequest(v.getText().toString().trim());
            return true;
        }
        return false;

}


    private class OnclickEnter implements TitleBarView.OnClickEnterButtonListener{
        @Override
        public void onClickEnterButton(View v) {
            Log.i("zhang", "tj " + strings.size());
            strings.size();
            if (bzf.equals("1")){
                for (int i = 0;i<strings.size();i++){

                    Users stu1 = new Users(strings.get(i), Integer.parseInt(strings2.get(i)));

                    List.add(stu1);
                }
                //这里就会自动根据规则进行排序
                Collections.sort(List, comparator);
                display(List);
                for (int i = 0;i<List.size();i++){

                    stringBuilder.append(List.get(i).getName()+",");
                    ryidstringBuilder.append(ryid.get(i)+",");
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("strResult", stringBuilder.toString());
                bundle2.putString("strResult2",ryidstringBuilder.toString());
                intent.putExtra("bundle2", bundle2);
                setResult(2, intent);
                finish();
            }else if(bzf.equals("2")) {
                for (int i = 0;i<strings.size();i++){
                    stringBuilder.append(strings.get(i)+",");
                    ryidstringBuilder.append(ryid.get(i)+",");
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("strResult", stringBuilder.toString());
                bundle2.putString("strResult2",ryidstringBuilder.toString());
                intent.putExtra("bundle2", bundle2);
                setResult(3, intent);
                finish();
            }else{
                if (strings.size()>1){
                    Toast.makeText(TxlActivity.this,"只能选择一个主管副职",Toast.LENGTH_SHORT).show();
                } else if (strings.size()==1){
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("strResult",strings.get(0));
                    intent.putExtra("bundle2", bundle2);
                    setResult(4, intent);
                    finish();
                }
            }

        }
    }

    /**
     * 加载网络数据
     */
    public void regRequest(String s) {

        Meetingroom.ReqMeetingRoom reqDocument = Meetingroom.ReqMeetingRoom.newBuilder().setSid(User.sid).setAc("TXLLB").setTj(s).build();

        new HttpUtilDocumentwh() {
            @Override
            public <T> void analysisInputStreamData(Meetingroom.ReqMeetingRoom index) throws IOException {
                Clist = index.getOlistList();
                Log.i("zhang", "tia"+Clist.size());
                adapter = new MyExpandableListViewAdapter(TxlActivity.this);

                txlexpandableList.setAdapter(adapter);
                /*默认展开*/
                // for (int i = 0; i < adapter.getGroupCount(); i++) {
                //txlexpandableList.expandGroup(i);
                //}
            }
        }.protocolBuffer(TxlActivity.this, Globals.HYAP_URI, reqDocument, null);
    }

    // 用过ListView的人一定很熟悉，只不过这里是BaseExpandableListAdapter
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public MyExpandableListViewAdapter(Context context) {
            this.context = context;
        }

        /**
         * 获取组的个数
         *
         * @return
         * @see ExpandableListAdapter#getGroupCount()
         */
        @Override
        public int getGroupCount() {
            return Clist.size();
        }

        /**
         * 获取指定组中的子元素个数
         *
         * @param groupPosition
         * @return
         * @see ExpandableListAdapter#getChildrenCount(int)
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            return Clist.get(groupPosition).getPlistList().size();
        }

        /**
         * 获取指定组中的数据
         *
         * @param groupPosition
         * @return
         * @see ExpandableListAdapter#getGroup(int)
         */
        @Override
        public Object getGroup(int groupPosition) {
            return Clist.get(groupPosition);
        }

        /**
         * 获取指定组中的指定子元素数据。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see ExpandableListAdapter#getChild(int, int)
         */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return Clist.get(groupPosition).getPlistList().get(childPosition);
        }

        /**
         * 获取指定组的ID，这个组ID必须是唯一的
         *
         * @param groupPosition
         * @return
         * @see ExpandableListAdapter#getGroupId(int)
         */
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /**
         * 获取指定组中的指定子元素ID
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see ExpandableListAdapter#getChildId(int, int)
         */
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /**
         * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
         *
         * @return
         * @see ExpandableListAdapter#hasStableIds()
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * 获取显示指定组的视图对象
         *
         * @param groupPosition 组位置
         * @param isExpanded    该组是展开状态还是伸缩状态
         * @param convertView   重用已有的视图对象
         * @param parent        返回的视图对象始终依附于的视图组
         * @return
         * @see ExpandableListAdapter#getGroupView(int, boolean, View,
         * ViewGroup)
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.txl_f_item, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.txl_1);

                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            //点击的使用改变的图片
//            if (!isExpanded)
//            {
//                groupHolder.img.setBackgroundResource(R.mipmap.address_w);
//            }
//            else
//            {
//                groupHolder.img.setBackgroundResource(R.mipmap.address_j);
//            }

            groupHolder.txt.setText(Clist.get(groupPosition).getDname());
            return convertView;
        }

        /**
         * 获取一个视图对象，显示指定组中的指定子元素数据。
         *
         * @param groupPosition 组位置
         * @param childPosition 子元素位置
         * @param isLastChild   子元素是否处于组中的最后一个
         * @param convertView   重用已有的视图(View)对象
         * @param parent        返回的视图(View)对象始终依附于的视图组
         * @return
         * @see ExpandableListAdapter#getChildView(int, int, boolean, View,
         * ViewGroup)
         */
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
                convertView = LayoutInflater.from(context).inflate(R.layout.txl_z_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView) convertView.findViewById(R.id.txl_name);
                itemHolder.txt1 = (TextView) convertView.findViewById(R.id.txl_zw);
                itemHolder.img = (CircularImage) convertView.findViewById(R.id.txl_iamge1);
                itemHolder.ccx = (CheckBox) convertView.findViewById(R.id.txl_c);
            if (strings.contains(Clist.get(groupPosition).getPlist(childPosition).getPname())){
//                Log.i("zhang", "选中");
                itemHolder.ccx.setChecked(true);
            }else{
//                Log.i("zhang", "未选中");
                itemHolder.ccx.setChecked(false);
            }
            itemHolder.ccx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){


                        strings.add(Clist.get(groupPosition).getPlist(childPosition).getPname());
                        ryid.add(Clist.get(groupPosition).getPlist(childPosition).getPcode());
                        strings2.add(Clist.get(groupPosition).getPlist(childPosition).getPk());
                        Log.i("zhang", "姓名"+strings.get(0)+"权利"+strings2.get(0 ));
                    }else{
                        if (strings.contains(Clist.get(groupPosition).getPlist(childPosition).getPname()))
                        strings.remove(Clist.get(groupPosition).getPlist(childPosition).getPname());

                        if ( ryid.contains(Clist.get(groupPosition).getPlist(childPosition).getPcode()))
                            ryid.remove(Clist.get(groupPosition).getPlist(childPosition).getPcode());
                        if ( strings2.contains(Clist.get(groupPosition).getPlist(childPosition).getPk()))
                            strings2.remove(Clist.get(groupPosition).getPlist(childPosition).getPk());
                    }
                }
            });
            itemHolder.txt.setText(Clist.get(groupPosition).getPlist(childPosition).getPname());
            itemHolder.txt1.setText(Clist.get(groupPosition).getPlist(childPosition).getZhiwu());

            ImageLoader.getInstance().displayImage(Clist.get(groupPosition).getPlist(childPosition).getPurl(), itemHolder.img);
            return convertView;
        }

        /**
         * 是否选中指定位置上的子元素。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see ExpandableListAdapter#isChildSelectable(int, int)
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GroupHolder {
        public TextView txt;

        public TextView img;
    }

    class ItemHolder {
        public CircularImage img;
        public CheckBox ccx;
        public TextView txt;
        public TextView txt1;
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
