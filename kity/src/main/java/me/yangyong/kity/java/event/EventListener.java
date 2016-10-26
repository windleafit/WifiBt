package me.yangyong.kity.java.event;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 所有事件的监听器接口,可以选择监听的事件集合,最多为32个
 */
public abstract class EventListener {

    private List<Object> mEventList;

    /**
     * 监听所用事件的监听器
     */
    public EventListener() {
        mEventList = new LinkedList<>();
    }

    /**
     * 通过指定需要监听的事件，构造监听器
     *
     * @param events 需要监听的事件
     */

    public EventListener(Object... events) {
        mEventList = new LinkedList<>();
        Collections.addAll(mEventList, events);
    }

    /**
     * 指定的事件是否被该对象监听
     *
     * @param event 事件类型
     * @return 是否被对象监听
     */
    public boolean isListened(Object event) {
        return mEventList.isEmpty() || mEventList.contains(event);
    }

    /**
     * 被通知
     *
     * @param type    事件类型
     * @param source  发出通知的对象
     * @param message 具体消息
     * @return 返回该事件是否得到处理，true - 表示已经得到处理，该事件便不应该再次被其他监听器触发
     * false - 表示还没有得到处理，该事件可以再次被其他监听器触发
     */
    public abstract boolean onNotified(Object type, Object source, Object message);

}
