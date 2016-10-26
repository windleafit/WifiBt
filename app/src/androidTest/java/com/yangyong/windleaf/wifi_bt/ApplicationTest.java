package com.yangyong.windleaf.wifi_bt;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.widget.Toast;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public void testVolley(){
        Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();
    }

}