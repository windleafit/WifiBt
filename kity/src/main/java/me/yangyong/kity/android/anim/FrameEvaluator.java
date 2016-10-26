package me.yangyong.kity.android.anim;

import android.animation.TypeEvaluator;
import android.graphics.RectF;

public class FrameEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        FrameItem startFrame = (FrameItem) startValue;
        FrameItem endFrame = (FrameItem) endValue;

        RectF nowRect = new RectF(startFrame.rect.left + fraction * (endFrame.rect.left - startFrame.rect.left),
                startFrame.rect.top + fraction * (endFrame.rect.top - startFrame.rect.top),
                startFrame.rect.right + fraction * (endFrame.rect.right - startFrame.rect.right),
                startFrame.rect.bottom + fraction * (endFrame.rect.bottom - startFrame.rect.bottom));

        float nowAlpha = startFrame.alpha + fraction * (endFrame.alpha - startFrame.alpha);
        double nowRotate = startFrame.rotate + fraction * (endFrame.rotate - startFrame.rotate);

        return new FrameItem(nowRect,nowAlpha,nowRotate);
    }
}
