package me.yangyong.kity.android.anim;

import java.util.LinkedList;
import java.util.List;

/**
 * 多个控件动画
 */
public class FrameAnimation {

    private List<FrameItemValueAnim> mFrameItemAnimList;

    public FrameAnimation() {
        mFrameItemAnimList = new LinkedList<>();
    }

    public void addFrameItemAnim(FrameItemValueAnim frameItemAnim) {
        mFrameItemAnimList.add(frameItemAnim);
    }

    public void start() {
        for (FrameItemValueAnim itemAnim : mFrameItemAnimList) {
            itemAnim.start();
        }
    }

}
