package com.zhang.sqone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhang.sqone.R;
import com.zhang.sqone.bean.Meetingdiscuss;

import java.util.List;

public class MyAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private List<Meetingdiscuss.ReqMeetingDiscuss.MeetingCxMap> fileList3 ;
    private MyAdapter(Context context ,List<Meetingdiscuss.ReqMeetingDiscuss.MeetingCxMap> fileList)
    {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
        this.fileList3 = fileList;
    }
    public void setData(List<Meetingdiscuss.ReqMeetingDiscuss.MeetingCxMap> fileList ) {
        this.fileList3 = fileList;
    }
    @Override
    public int getCount() {
        //How many items are in the data set represented by this Adapter.
        //在此适配器中所代表的数据集中的条目数
        return fileList3.size();
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        //获取数据集中与指定索引对应的数据项
        return position;
    }

    @Override
    public long getItemId(int position) {
        //Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id
        return position;
    }

    //Get a View that displays the data at the specified position in the data set.
    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.banzi_litems, null);
        TextView text1 = (TextView) convertView.findViewById(R.id.banzhi1_title);
        TextView text2 = (TextView) convertView.findViewById(R.id.banzhi1_shenhe);
        TextView text3 = (TextView) convertView.findViewById(R.id.banzhi1_text);
        TextView text4 = (TextView) convertView.findViewById(R.id.caozuo_text);
        text1.setText(fileList3.get(position).getQs());
        text2.setText(fileList3.get(position).getSt());
        text3.setText(fileList3.get(position).getSd());
        if (fileList3.get(position).getIfcauo().equals("0")){
            text4.setVisibility(View.INVISIBLE);
        }



        return convertView;
    }

}