package me.yangyong.kity.android.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOUAV on 2015/9/11.
 */
public class ActivityController {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
