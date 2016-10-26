package me.yangyong.kity.android.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 对话框
 */
public class Dialogy {

    /**
     * @param context                 环境
     * @param title                   标题
     * @param msg                     信息
     * @param positiveString          确认
     * @param onPositiveClickListener 确认监听
     */
    public static void show(Context context, String title, String msg, String positiveString, DialogInterface.OnClickListener onPositiveClickListener) {
        show(context, title, msg, positiveString, onPositiveClickListener, null, null);
    }

    /**
     * @param context                 环境
     * @param title                   标题
     * @param msg                     信息
     * @param positiveString          确认
     * @param onPositiveClickListener 确认监听
     * @param negativeString          取消
     * @param onNegativeClickListener 取消监听
     */
    public static void show(Context context, String title, String msg,
                            String positiveString, DialogInterface.OnClickListener onPositiveClickListener,
                            String negativeString, DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null)
            builder.setTitle(title);
        if (msg != null)
            builder.setMessage(msg);
        if (positiveString != null)
            builder.setPositiveButton(positiveString, onPositiveClickListener);
        if (negativeString != null)
            builder.setNegativeButton(negativeString, onNegativeClickListener);
        builder.setCancelable(true); //默认可取消
        builder.create().show();
    }

}
