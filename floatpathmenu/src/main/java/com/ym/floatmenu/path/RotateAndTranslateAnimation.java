package com.ym.floatmenu.path;

import android.view.animation.Animation;
import android.view.animation.Transformation;
/**
 * 自定义动画类
 * 控制对象的位置，以对象的中心旋转
 * @author 何凌波
 *
 */
public class RotateAndTranslateAnimation extends Animation {
	private int mFromXType = ABSOLUTE;

	private int mToXType = ABSOLUTE;

	private int mFromYType = ABSOLUTE;

	private int mToYType = ABSOLUTE;

	private float mFromXValue = 0.0f;

	private float mToXValue = 0.0f;

	private float mFromYValue = 0.0f;

	private float mToYValue = 0.0f;

	private float mFromXDelta;

	private float mToXDelta;

	private float mFromYDelta;

	private float mToYDelta;

	private float mFromDegrees;

	private float mToDegrees;

	private int mPivotXType = ABSOLUTE;

	private int mPivotYType = ABSOLUTE;

	private float mPivotXValue = 0.0f;

	private float mPivotYValue = 0.0f;

	private float mPivotX;

	private float mPivotY;

	/**
	 * 位移动画的构造函数
	 * @param fromXDelta
	 *            动画开始时的X坐标
	 * @param toXDelta
	 *            动画结束时的X坐标
	 * @param fromYDelta
	 *            动画开始时的Y坐标
	 * @param toYDelta
	 *            动画结束时的Y坐标
	 * 
	 * @param fromDegrees
	 *            旋转开始时的角度
	 * @param toDegrees
	 *            旋转结束时的角度
	 */
	public RotateAndTranslateAnimation(float fromXDelta, float toXDelta,
			float fromYDelta, float toYDelta, float fromDegrees, float toDegrees) {
		mFromXValue = fromXDelta;
		mToXValue = toXDelta;
		mFromYValue = fromYDelta;
		mToYValue = toYDelta;

		mFromXType = ABSOLUTE;
		mToXType = ABSOLUTE;
		mFromYType = ABSOLUTE;
		mToYType = ABSOLUTE;

		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;

		mPivotXValue = 0.5f;
		mPivotXType = RELATIVE_TO_SELF;
		mPivotYValue = 0.5f;
		mPivotYType = RELATIVE_TO_SELF;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float degrees = mFromDegrees
				+ ((mToDegrees - mFromDegrees) * interpolatedTime);
		if (mPivotX == 0.0f && mPivotY == 0.0f) {
			t.getMatrix().setRotate(degrees);
		} else {
			t.getMatrix().setRotate(degrees, mPivotX, mPivotY);
		}

		float dx = mFromXDelta;
		float dy = mFromYDelta;
		if (mFromXDelta != mToXDelta) {
			dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
		}
		if (mFromYDelta != mToYDelta) {
			dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
		}

		t.getMatrix().postTranslate(dx, dy);
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mFromXDelta = resolveSize(mFromXType, mFromXValue, width, parentWidth);
		mToXDelta = resolveSize(mToXType, mToXValue, width, parentWidth);
		mFromYDelta = resolveSize(mFromYType, mFromYValue, height, parentHeight);
		mToYDelta = resolveSize(mToYType, mToYValue, height, parentHeight);

		mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
		mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
	}
}
