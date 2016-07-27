package com.zhang.sqone.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zhang.sqone.R;

/**
*
* 阅读人员的界面
* @author ZJP
* created at 2016/5/11 10:09
*
*/
public abstract class YueDuRenView {

    /**
     * 1.确定取消按钮 按钮资字样 已经标题字样可自行填写 。 2.默认字样 确定/取消 。 3.填写string 不可为null 填写""即可。
     *
     * @param context
     *            上下文
     * @param btDetermine
     *            确定按钮String
     * @param btDismiss
     *            取消按钮String
     * @return
     */
    // 展示阅读人员
    public YueDuRenView deleteDialog(Context context,
                                String btDetermine, String btDismiss) {

        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.yueduren_view);
        TextView bt_dg_ecaluation_confirm = (TextView) dialog
                .findViewById(R.id.ydry_text);
        TextView bt_dg_ecaluation_cancel = (TextView) dialog
                .findViewById(R.id.wdry_text);
            bt_dg_ecaluation_confirm.setText(btDetermine);
            bt_dg_ecaluation_cancel.setText(btDismiss);
        return null;

    }
    public abstract void determineButton();

}
