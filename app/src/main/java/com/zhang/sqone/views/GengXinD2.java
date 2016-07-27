package com.zhang.sqone.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.zhang.sqone.R;
import com.zhang.sqone.adapter.CommonAdapter;
import com.zhang.sqone.adapter.ViewHolder;
import com.zhang.sqone.bean.Document;
import com.zhang.sqone.bean.Test1;
import com.zhang.sqone.home.DaiBanActivity;

import java.util.List;

/**
 * ] 自定义的Dialog工厂类(用户提示)
 *
 * @author Administrator
 */
public abstract class GengXinD2 {

    private CommonAdapter<Test1.ReqTest11.Test11Map> resultAdapter;

    /**
     * 1.确定取消按钮 按钮资字样 已经标题字样可自行填写 。 2.默认字样 确定/取消 。 3.填写string 不可为null 填写""即可。
     *
     * @param context
     *            上下文
     * @param tetle
     *            标题头
     * @param btDetermine
     *            确定按钮String
     * @param btDismiss
     *            取消按钮String
     * @return
     */
    // 删除提示框
    public GengXinD2 deleteDialog(Context context, List<Test1.ReqTest11.Test11Map> list) {

        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.gengxin_d2);
        Button bt_dg_ecaluation_confirm = (Button) dialog
                .findViewById(R.id.bt_dg_ecaluation_confirm3);
        bt_dg_ecaluation_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                determineButton();
                dialog.dismiss();

            }
        });
        ListView myList = (ListView) dialog
                .findViewById(R.id.t_list);
        resultAdapter = new CommonAdapter<Test1.ReqTest11.Test11Map>(context, list, R.layout.t_1) {
            @Override
            public void convert(ViewHolder holder, Test1.ReqTest11.Test11Map fileMap) {
                Log.i("zhang", "_______"+fileMap.getTs());
                    holder.setText(R.id.t_1,fileMap.getTs());

            }
        };
        myList.setAdapter(resultAdapter);
        return null;

    }

    public abstract void determineButton();

}
