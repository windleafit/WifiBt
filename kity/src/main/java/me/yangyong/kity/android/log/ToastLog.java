package me.yangyong.kity.android.log;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JOUAV on 2015/12/24.
 */
public class ToastLog {

    public static void show(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
