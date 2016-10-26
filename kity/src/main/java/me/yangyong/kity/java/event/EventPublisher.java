package me.yangyong.kity.java.event;

import java.util.LinkedList;
import java.util.List;

/**
 * 所有可以触发事件的类的基类
 */
public abstract class EventPublisher {

    final private List<EventListener> eventListenerList = new LinkedList<>();

    /**
     * 添加事件监听器
     *
     * @param listener 事件监听器
     * @return true/false
     */
    public final boolean addListener(EventListener listener) {
        if (listener == null) {
            return false;
        }
        return eventListenerList.contains(listener) || eventListenerList.add(listener);
    }

    /**
     * 移除事件监听器
     *
     * @param listener 事件监听器
     * @return true/false
     */
    public final boolean removeListener(EventListener listener) {
        return eventListenerList.remove(listener);
    }

    /**
     * 移除所有事件监听器
     */
    public final void removeListeners() {
        for (EventListener listener : eventListenerList) {
            eventListenerList.remove(listener);
        }
    }

    /**
     * 通知所有的Listener
     *
     * @param event 事件
     * @return 如果其中有一个监听器返回true，则表示该事件已经被处理，不再继续接下来的处理
     */
    public final boolean fireEvent(Object event) {
        return fireEvent(event, null);
    }

    /**
     * 通知所有的Listener
     * <p/>
     *
     * @param event 事件
     * @param msg   内容
     * @return 如果其中有一个监听器返回true，则表示该事件已经被处理，不再继续接下来的处理
     */
    public final boolean fireEvent(Object event, Object msg) {
        //循环发布
        for (EventListener listener : eventListenerList) {
            //判断是否监听event事件
            if (listener.isListened(event)) {
                //通知，并返回处理结果
                if (listener.onNotified(event, this, msg)) {
                    //处理完成结束通知
                    return true;
                }
            }
        }

        //没有监听event的监听者
        return false;
    }
}
