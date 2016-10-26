package me.yangyong.kity.android.anim;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 一个view对应的动画
 */
public class FrameItemValueAnim {
    private View view;
    private FrameItem fromFrameItem;
    private FrameItem toFrameItem;
    private int duration;//ms
    private ValueAnimator valueAnimator;

    public FrameItemValueAnim(View view, FrameItem fromFrameItem, FrameItem toFrameItem, int duration) {
        this.view = view;
        this.fromFrameItem = fromFrameItem;
        this.toFrameItem = toFrameItem;
        this.duration = duration;

        init();
    }

    private void init() {
        valueAnimator = ValueAnimator.ofObject(new FrameEvaluator(), fromFrameItem, toFrameItem);
        valueAnimator.addUpdateListener(new FrameAnimationUpdate());
        valueAnimator.setDuration(duration);
    }

    public void start() {
        valueAnimator.start();
    }

    /**
     * 是否移动
     *
     * @return
     */
    private boolean isMove() {
        if ((fromFrameItem.rect.left != toFrameItem.rect.left) ||
                (fromFrameItem.rect.top != toFrameItem.rect.top))
            return true;
        return false;
    }

    /**
     * 是否缩放
     *
     * @return
     */
    private boolean isScale() {
        if ((fromFrameItem.rect.width() != toFrameItem.rect.width()) ||
                (fromFrameItem.rect.height() != toFrameItem.rect.height()))
            return true;
        return false;
    }

    /**
     * 是否渐变
     *
     * @return
     */
    private boolean isAlpha() {
        if (fromFrameItem.alpha != toFrameItem.alpha)
            return true;
        return false;
    }

    /**
     * 是否旋转
     *
     * @return
     */
    private boolean isRotate() {
        if (fromFrameItem.rotate != toFrameItem.rotate)
            return true;
        return false;
    }

    public FrameItemValueAnim onAnimatorListener(AnimatorListenerAdapter listener) {
        valueAnimator.addListener(listener);
        return this;
    }

    private class FrameAnimationUpdate implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

            FrameItem nowFrameItem = (FrameItem) animation.getAnimatedValue();

            if (isMove()) {
                view.setX(nowFrameItem.rect.left);
                view.setY(nowFrameItem.rect.top);
            }

            if (isScale()) {
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.width = (int) nowFrameItem.rect.width();
                lp.height = (int) nowFrameItem.rect.height();
                view.setLayoutParams(lp);
            }

            if (isAlpha()) {
                view.setAlpha(nowFrameItem.alpha);
            }

            view.postInvalidate();

        }
    }
}
