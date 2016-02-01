package com.zhang.sqone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhang.sqone.adapter.CommonAdapter;
import com.zhang.sqone.adapter.ViewHolder;
import com.zhang.sqone.bean.HomeImagerResult;
import com.zhang.sqone.bean.JobResult;
import com.zhang.sqone.ceshi.HttpasyncActivity;
import com.zhang.sqone.utils.GsonUtils;
import com.zhang.sqone.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
* 使用程序的主界面
* */
public class MainActivity extends BaseActivity {

    @Bind(R.id.main_ptrlist)
    PullToRefreshListView mList;/*加载数据的list*/
    private LinearLayout mBanner;
    private RelativeLayout rl;/*获得banner图中的组件*/
    ViewGroup.LayoutParams lp;/*banner所放置的位置*/
    private List<String> imgList = new ArrayList<String>()/*banner图地址*/;
    private ArrayList<ImageView> imagViews;/*Viewpager添加的iamgeview*/
    private static final int POINT_LENGTH = 3;/*设置banner图的个数*/
    private LinearLayout pointGroup;/*指示点存放的布局*/
    private ViewPager vp;/*Banner中的ViewPager*/
    private int mCurrentPagePosition = FIRST_ITEM_INDEX; //程序的进入是banner图的位置
    private static final int FIRST_ITEM_INDEX = 1;
    private int mCurrentIndex;
    private static final int AD_TIME = 2000;/*banner播放时间*/
    private String[] s = {"1111", "11111", "22222"};
    public static String loca = Globals.LOCACODE;/*接口参数*/
    public static String sti = "";  //开始时间
    public static String eti = "";  //结束时间
    private Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            Log.i(Globals.LOG_TAG, "p=" +  vp.getCurrentItem());
            int p = vp.getCurrentItem() + 1;
            vp.setCurrentItem(p);
            handler.postDelayed(this, AD_TIME);
        }
    };
    private LinearLayout mce;
    private int page = 1;

    private List<JobResult.Job> jobs = new ArrayList<>();
    private CommonAdapter<JobResult.Job> ja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        获得banner图的布局
        mBanner = (LinearLayout) getLayoutInflater().inflate(R.layout.home_banner, null);
        //获得banner图中的View
        rl = (RelativeLayout) mBanner.findViewById(R.id.home_head_relative_layout);
        //获得是在点的布局
        pointGroup = (LinearLayout) mBanner.findViewById(R.id.point_group);
        //获得banner图上的viewpager
        vp = (ViewPager) mBanner.findViewById(R.id.home_vp);
//        获得banner图的长宽
        addBanner();
//        设置list
        setList();
        loadData();
        //获得banner图中的图片
        getHomeImage();
        handler.postDelayed(runnable, AD_TIME);//每两秒执行一次runnable.
        //下载数据添加到主界面的list


    }

    private void loadData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Globals.WS_POST_KEY,
                "{\"Ac\":\"ZWLB\",\"Para\":{\"D\":\"" + loca + "\",\"Sti\":\"" + sti + "\",\"Eti\":\"" + eti + "\",\"P\":\"" + page + "\",\"I\":\"" + "" + "\"}}");


        new VolleyUtil() {

            public void analysisData(String response) {
                JobResult s = GsonUtils.json2bean(response, JobResult.class);
                if (s == null || !(s.Stu == 1)) {
                    Toast.makeText(MainActivity.this, Globals.SER_ERROR,
                            Toast.LENGTH_SHORT).show();

                } else {
                    if (page == 1 && s.Rst.Lst.size() == 0) {
                        Toast.makeText(MainActivity.this, "没有信息",
                                Toast.LENGTH_SHORT).show();
//                        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        if (s.Rst.Lst.size() < Globals.COUNT) {
//                            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        }
                        if (page == 1) {
                            jobs = s.Rst.Lst;
//                            id = jobs.get(0).XID;

                        } else {
                            List<JobResult.Job> js = s.Rst.Lst;
                            for (int i = 0; i < js.size(); i++) {
                                jobs.add(js.get(i));
                            }
                        }

                        ja.setData(jobs);
                        ja.notifyDataSetChanged();
                        page++;
                    }
                }
            }

        }.volleyStringRequestPost(MainActivity.this, params, mList);

    }

    private void setList() {
        //从PullToRefreshListView获得listView
        ListView lv = mList.getRefreshableView();
        //list添加Header
        lv.addHeaderView(mBanner, null, true);
//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.item, R.id.item_text, s));
        //添加分割线
        lv.setHeaderDividersEnabled(true);
        //设置刷新状态
        mList.setMode(PullToRefreshBase.Mode.BOTH);
        //设置list的上拉加载下拉刷新
        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.setMode(PullToRefreshBase.Mode.BOTH);
                //  id = "";
                page = 1;
                sti = "";
                eti = "";
                loca = Globals.LOCACODE;
                if(jobs != null) {
                    jobs.clear();
                }

                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        ja= new CommonAdapter<JobResult.Job>(this,jobs,R.layout.listview_jzl_item) {
            @Override
            public void convert(ViewHolder holder, JobResult.Job job) {
                holder.setText(R.id.ce_shi_text, job.T);
//                Picasso.with(MainActivity.this).load(job.PH).into(holder.<ImageView>getView(R.id.ce_shi_image));
                ImageLoader.getInstance().displayImage(job.PH, holder.<ImageView>getView(R.id.ce_shi_image));
            }
        };
        mList.setAdapter(ja);
    }

    private void addBanner() {
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int scrrnWidth = dm.widthPixels;
        //将布局添加站位
        lp = rl.getLayoutParams();
        lp.width = scrrnWidth;
        lp.height = lp.width / 2;
        rl.setLayoutParams(lp);

    }

    public void getHomeImage() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Globals.WS_POST_KEY, "{\"Ac\":\"GGW\"}}");

        new VolleyUtil() {

            public void analysisData(String response) {
                HomeImagerResult s = GsonUtils.json2bean(response,
                        HomeImagerResult.class);
                if (s == null || !(s.Stu == 1)) {
                    Toast.makeText(MainActivity.this, Globals.SER_ERROR,
                            Toast.LENGTH_SHORT).show();
                } else {
                    imgList = s.Rst.Lst;
                    Log.i("s2525", imgList.get(1));
                    if (imgList != null) {
                        //在banner图中添加数据
                        initViewPager();
                    }
                }

            }

        }.volleyStringRequestPost(MainActivity.this, params, null);

    }

    private void initViewPager() {
        imagViews = new ArrayList<ImageView>();
        // 增加第1个界面,实际上他显示的是最后一个界面
        addImageView(POINT_LENGTH - 1);
        for (int i = 0; i < POINT_LENGTH; i++) {
            addImageView(i);
            // 添加指示点
            ImageView point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);

            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        // 增加最后的一个界面，实际上他显示的是第一个界面
        addImageView(0);
        //给viewpager添加适配器
        vp.setAdapter(new MyPagerAdapter());
        //当Viewpager发生变化触发事件
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private void setCurrentDot(int positon) {
                // 界面实际显示的序号是第1, 2, 3。而点的序号应该是0, 1, 2.所以减1.
                positon = positon - 1;
                if (positon < 0 || positon > imagViews.size() - 1 || mCurrentIndex == positon) {
                    return;
                }
                pointGroup.getChildAt(positon).setEnabled(true);
                pointGroup.getChildAt(mCurrentIndex).setEnabled(false);
                mCurrentIndex = positon;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position > POINT_LENGTH) {
                    mCurrentPagePosition = FIRST_ITEM_INDEX;
                } else if (position < FIRST_ITEM_INDEX) {
                    mCurrentPagePosition = POINT_LENGTH;
                } else {
                    mCurrentPagePosition = position;
                }
//        Log.i(Globals.LOG_TAG, "当前的位置是" + mCurrentPagePosition);
                setCurrentDot(mCurrentPagePosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_IDLE == state) {
                    vp.setCurrentItem(mCurrentPagePosition, false);
                }

            }

        });
        //首先显示id为1的banner图数据
        vp.setCurrentItem(mCurrentPagePosition);
    }

    private void addImageView(int i) {
        // 初始化图片资源
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams vpll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(vpll);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(imgList.get(i),
                image);
        imagViews.add(image);
    }

    //Viewpager 使用的adapter
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imagViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imagViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imagViews.get(position));
            View view = imagViews.get(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(MainActivity.this, HttpasyncActivity.class);
                    startActivity(intent);
                    Log.i("ss123", "onClick " + position);
                }
            });
            return imagViews.get(position);
        }
    }

}
