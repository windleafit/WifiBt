package me.yangyong.kity.android.anim;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * 动画开始或结束的状态
 */
public class FrameItem {
    /**
     * left，top，right，bottom
     */
    public RectF rect;
    public PointF center;
    public float alpha;
    public double rotate;

    public FrameItem(RectF rect) {
        this.rect = rect;
    }

    public FrameItem(RectF rect, float alpha, double rotate) {
        this.rect = rect;
        this.alpha = alpha;
        this.rotate = rotate;
    }


    public double getAlpha() {
        return alpha;
    }

    public FrameItem setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public PointF getCenter() {
        return center;
    }

    public FrameItem setCenter(PointF center) {
        this.center = center;
        return this;
    }

    public RectF getRect() {
        return rect;
    }

    public FrameItem setRect(RectF rect) {
        this.rect = rect;
        return this;
    }

    public double getRotate() {
        return rotate;
    }

    public FrameItem setRotate(double rotate) {
        this.rotate = rotate;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("rect:[" + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom + "]");
        builder.append("wh:[" + rect.width() + "," + rect.height() + "]");
        return builder.toString();
    }
}
