package com.yangyong.windleaf.wifi_bt.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YangYong on 2016/8/12 0012.
 */
public class FastJsonAPITest {

    /**
     * 将String或Bean转为JSON对象
     */

    @Test
    public void test_From_Value_To_JSON() throws JSONException {
        JSONObject jsonObj0 = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
        JSONObject jsonObj2 = new JSONObject();
        JSONObject jsonObj3 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        //创建jsonObj0
        jsonObj0.put("name0", "zhangsan");
        jsonObj0.put("sex1", "female");
        System.out.println("jsonObj0:" + jsonObj0);

        //创建jsonObj1
        jsonObj1.put("name", "xuwei");
        jsonObj1.put("sex", "male");
        System.out.println("jsonObj1:" + jsonObj1);

        //创建jsonObj2，包含两个条目，条目内容分别为jsonObj0，jsonObj1
        jsonObj2.put("item0", jsonObj0);
        jsonObj2.put("item1", jsonObj1);
        System.out.println("jsonObj2:" + jsonObj2);

        //创建jsonObj3，只有一个条目，内容为jsonObj2
        jsonObj3.put("j3", jsonObj2);
        System.out.println("jsonObj3:" + jsonObj3);

        //往JSONArray中添加JSONObject对象。发现JSONArray跟JSONObject的区别就是JSONArray比JSONObject多中括号[]
        jsonArray.add(jsonObj1);
        System.out.println("jsonArray:" + jsonArray);

        JSONObject jsonElement = new JSONObject();
        jsonElement.put("weather", jsonArray);
        System.out.println("jsonElement:" + jsonElement);
    }

    @Test
    public void test_From_Bean_To_JSONObject() {
        //将bean对象转化为json对象
        Address address = getAddress();
        System.out.println("From_Bean_To_JSONObject");
        JSONObject jsonObject = (JSONObject) JSON.toJSON(address);
        System.out.println("To jsonObject : " + jsonObject.toString());
    }


    @Test
    public void test_From_Bean_To_JSONString() {
        //将bean对象转化为json对象
        Address address = getAddress();
        System.out.println("From_Bean_To_JSONString");
        String jsonString = JSON.toJSONString(address);
        System.out.println("To jsonString  : " + jsonString.toString());
    }


    @Test
    public void test_From_Bean_To_JSONArray() {
        //将java对象list转化为json对象：
        Address address = getAddress();
        Address address2 = getAddress2();

        List list = new ArrayList();
        list.add(address);
        list.add(address2);

        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
        System.out.println("From_Bean_To_JSONArray");
        System.out.println(jsonArray.toString());
    }

    /**
     * 将JSON对象转为String或Bean
     */

    @Test
    public void test_From_JSONObject_To_String() {

        JSONObject jsonObject = getJSONObject();

        System.out.println("To_Bean:");
        System.out.println(jsonObject.get("no"));
        System.out.println(jsonObject.get("provience"));
        System.out.println(jsonObject.get("road"));
        System.out.println(jsonObject.get("streate"));
    }

    @Test
    public void test_From_JSONObject_To_Bean() {
        //将json对象转化为bean对象
        JSONObject jsonObject = getJSONObject();
        System.out.println("From JSONObject:");
        System.out.println(jsonObject.toString());

        Address address = jsonObject.toJavaObject(Address.class);
        System.out.println("To_Bean:");
        System.out.println(address.toString());
    }

    @Test
    public void test_From_JSONArray_To_Bean() {
        //将java对象list转化为json对象：
        JSONArray jsonArray = getJSONArray();
        System.out.println("From JSONArray:");
        System.out.println(jsonArray.toString());

        List<Address> list2 = JSONArray.parseArray(jsonArray.toString(), Address.class);
        System.out.println("To Bean:");
        for (Address address : list2) {
            System.out.println(address.toString());
        }
    }

    @Test
    public void test_From_JSONArray_To_String() {
        //将java对象list转化为json对象：
        JSONArray jsonArray = getJSONArray();
        System.out.println("From JSONArray:");
        System.out.println(jsonArray.toString());

        System.out.println("To Bean:");
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("To Bean2:");
        Object[] addresses = jsonArray.toArray();
        for (Object address : addresses) {
            System.out.println(address);
        }
    }

    private JSONArray getJSONArray() {
        //将bean对象转化为JSONArray对象
        Address address1 = getAddress();
        Address address2 = getAddress2();
        List list1 = new ArrayList();
        list1.add(address1);
        list1.add(address2);
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list1);
        return jsonArray;
    }


    private JSONObject getJSONObject() {
        //将bean对象转化为JSONObject
        Address address = getAddress();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(address);
        return jsonObject;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setNo("104");
        address.setProvience("陕西");
        address.setRoad("高新路");
        address.setStreate("");
        return address;
    }

    private Address getAddress2() {
        Address address = new Address();
        address.setNo("105");
        address.setProvience("陕西");
        address.setRoad("未央路");
        address.setStreate("张办");
        return address;
    }
}
