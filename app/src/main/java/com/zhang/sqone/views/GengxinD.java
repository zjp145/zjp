package com.zhang.sqone.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.zhang.sqone.R;

/**
 * ] 自定义的Dialog工厂类(用户提示)
 *
 * @author Administrator
 */
public abstract class GengxinD {

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
    public GengxinD deleteDialog(Context context, String tetle,
                                 String btDetermine, String btDismiss) {

        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.genxin_view);
        Button bt_dg_ecaluation_confirm = (Button) dialog
                .findViewById(R.id.bt_dg_ecaluation_confirm3);
        bt_dg_ecaluation_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                determineButton();
                dialog.dismiss();

            }
        });
        return null;

    }

    public abstract void determineButton();

}
