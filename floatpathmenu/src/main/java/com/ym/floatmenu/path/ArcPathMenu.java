package com.ym.floatmenu.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 项目名称：FloatPathMenuSample
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/9/20 15:35
 * 修改人：wengyiming
 * 修改时间：2016/9/20 15:35
 * 修改备注：
 */

public class ArcPathMenu extends View implements View.OnTouchListener {

    private static final int MAX_DURATION = 3000;  //最大间隔时长
    private static final int MIN_DURATION = 500; //最小间隔时长
    private static final int MIN_RADIUS = 100;

    public static final int LEFT_TOP = 1;
    public static final int CENTER_TOP = 2;
    public static final int RIGHT_TOP = 3;
    public static final int LEFT_CENTER = 4;
    public static final int CENTER = 5;
    public static final int RIGHT_CENTER = 6;
    public static final int LEFT_BOTTOM = 7;
    public static final int CENTER_BOTTOM = 8;
    public static final int RIGHT_BOTTOM = 9;


    private static final int[] mColors = new int[]{0xB07ECBDA, 0xB0E6A92C, 0xB0D6014D, 0xB05ABA94};
    private static final int[] ITEM_DRAWABLES = {R.drawable.composer_camera,
            R.drawable.composer_music, R.drawable.composer_place,
            R.drawable.composer_sleep, R.drawable.composer_thought,
            R.drawable.composer_with};


    private WindowManager mWindowManager;// 当前view的窗口管理器
    private WindowManager.LayoutParams mWmParams;  // WindowMananger的params，控制这个值可以将自定义的view设置到窗口管理器中
    private float mTouchStartX;// 记录首次按下的位置x
    private float mTouchStartY;// 记录首次按下的位置y
    private int mScreenWidth;// 屏幕宽度
    private int mScreenHeight;// 屏幕高度
    private boolean mDraging;// 是否拖动中

    private int position;// 按钮的位置
    private int mRadius = MIN_RADIUS;// 中心菜单圆点到子菜单中心的距离
    private boolean mExpanded = false;
    private Bitmap logo;


    private Paint mPaint;
    private int mCanvasAngle;   //Canvas旋转角度
    private int mDuration = MIN_DURATION;//动画间隔时长
    private Context mContext;

    public ArcPathMenu(Context context) {
        super(context);
        init(context);
    }

    public ArcPathMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArcPathMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initWindowsManager(context);
        Drawable drawable = context.getResources().getDrawable(R.drawable.composer_icn_plus);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        logo = bd.getBitmap();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColors[0]);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mRadius = logo.getWidth() * 2 + mRadius;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeW = 0;
        int sizeH = 0;

        sizeW = mRadius;
        sizeH = mRadius;
//        switch (position) {
//            case LEFT_TOP:
//                sizeW = mRadius;
//                sizeH = mRadius;
//                break;
//            case LEFT_CENTER:
//                sizeW = mRadius;
//                sizeH = mRadius;
//                break;
//            case LEFT_BOTTOM:
//                sizeW = mRadius;
//                sizeH = mRadius;
//                break;
//            default:
//                sizeW = mRadius;
//                sizeH = mRadius;
//                break;
//        }
        setMeasuredDimension(sizeW, sizeH);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        left = centerX - logo.getWidth() / 2;
        right = centerX + logo.getWidth() / 2;
        top = centerY - logo.getHeight() / 2;
        bottom = centerY + logo.getHeight() / 2;
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        canvas.save();

        int left = centerX - getWidth() / 2;
        int top = centerY - logo.getHeight() / 2;
        canvas.drawBitmap(logo, left, top, mPaint);
        canvas.restore();


        canvas.save();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);


        RectF rect = new RectF(centerX - getWidth() / 2, centerX - getWidth() / 2,
                centerY + getWidth() / 2, centerY + getWidth() / 2);

        canvas.drawArc(rect, 270, 180, false, mPaint);
        canvas.restore();

//        for (int subMenuImg : ITEM_DRAWABLES) {
//            Drawable drawable = mContext.getResources().getDrawable(subMenuImg);
//            BitmapDrawable bd = (BitmapDrawable) drawable;
//            Bitmap subMenuBitmap = bd.getBitmap();
//
//            Rect frame = computeChildFrame(left, top, mRadius, degrees,
//                    logo.getWidth());
//
//            degrees += perDegrees;
//            canvas.drawBitmap(subMenuBitmap, frame.left, frame.top, mPaint);
//        }
    }

    private void initWindowsManager(Context context) {
        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);// 获取系统的窗口服务
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;// 根据当前屏幕信息拿到屏幕的宽高
        mScreenHeight = dm.heightPixels;
        this.mWmParams = new WindowManager.LayoutParams();// 获取窗口参数
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWmParams.type = WindowManager.LayoutParams.TYPE_TOAST;// 等于API19或API19以下需要指定窗口参数type值为TYPE_TOAST才可以作为悬浮控件显示出来
        } else {
            mWmParams.type = WindowManager.LayoutParams.TYPE_PHONE;// API19以上侧只需指定为TYPE_PHONE即可
        }


        mWmParams.format = PixelFormat.RGBA_8888;// 当前窗口的像素格式为RGBA_8888,即为最高质量

        // NOT_FOCUSABLE可以是悬浮控件可以响应事件，LAYOUT_IN_SCREEN可以指定悬浮球指定在屏幕内，部分虚拟按键的手机，虚拟按键隐藏时，虚拟按键的位置则属于屏幕内，此时悬浮球会出现在原虚拟按键的位置
        mWmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        // 默认指定位置在屏幕的左上方，可以根据需要自己修改
        mWmParams.gravity = Gravity.LEFT | Gravity.TOP;
        position = 1;
        // 默认指定的横坐标为屏幕一半
        mWmParams.x = 0;
        // 默认指定的纵坐标为屏幕高度的一半，这里只是大概约束，因为上的flags参数限制，悬浮球不会出现在屏幕外
        mWmParams.y = 0;

        // 宽度指定为内容自适应
        mWmParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        mWmParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        setBackgroundColor(context.getColor(android.R.color.holo_red_light));
        mWindowManager.addView(this, mWmParams);

        setOnTouchListener(this);
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = (int) event.getRawX();
        float y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                mWmParams.alpha = 1f;
                mWindowManager.updateViewLayout(this, mWmParams);
                mDraging = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float mMoveStartX = event.getX();
                float mMoveStartY = event.getY();
                if (Math.abs(mTouchStartX - mMoveStartX) > 3
                        && Math.abs(mTouchStartY - mMoveStartY) > 3) {
                    mDraging = true;
                    mWmParams.x = (int) (x - mTouchStartX);
                    mWmParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(this, mWmParams);
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                refreshMenuXY();
                break;
        }
        return false;
    }

    private void refreshMenuXY() {
        int wmX = mWmParams.x;
        int wmY = mWmParams.y;
        int halfLayoutW = 0;
        int halfLayoutH = 0;

        if (wmX < (mScreenWidth / 3)) //左边  竖区域
        {
            mWmParams.x = 0;
            if (wmY < mScreenHeight / 3) {
                position = LEFT_TOP;//左上
                mWmParams.y = 0;
            } else if (wmY >= mScreenHeight / 3
                    && wmY <= mScreenHeight * 2 / 3) {
                position = LEFT_CENTER;//左中
                mWmParams.y = mScreenHeight / 2 - halfLayoutH;
            } else if (wmY > mScreenHeight * 2 / 3 && wmY <= mScreenHeight) {
                position = LEFT_BOTTOM;//左下
                mWmParams.y = mScreenHeight;
            }
        } else if (wmX >= mScreenWidth / 3 && wmX <= mScreenWidth * 2 / 3)//中间 竖区域
        {
            mWmParams.x = mScreenWidth / 2 - halfLayoutW;
            if (wmY < mScreenHeight / 3) {
                position = CENTER_TOP;//中上
                mWmParams.y = 0;
            } else if (wmY >= mScreenHeight / 3
                    && wmY <= mScreenHeight * 2 / 3) {
                position = CENTER;//中
                mWmParams.y = mScreenHeight - halfLayoutH;
            } else if (wmY > mScreenHeight * 2 / 3) {
                position = CENTER_BOTTOM;//中下
                mWmParams.y = mScreenHeight;
            }
        } else if (wmX > mScreenWidth * 2 / 3 && wmX <= mScreenWidth)//右边竖区域
        {
            mWmParams.x = mScreenWidth;
            if (wmY < mScreenHeight / 3) {
                position = RIGHT_TOP;//上右
                mWmParams.y = 0;
            } else if (wmY >= mScreenHeight / 3
                    && wmY <= mScreenHeight * 2 / 3) {
                position = RIGHT_CENTER;//中右
                mWmParams.y = mScreenHeight / 2 - halfLayoutH;
            } else if (wmY > mScreenHeight * 2 / 3 && wmY <= mScreenHeight) {
                position = RIGHT_BOTTOM;//下右
                mWmParams.y = mScreenHeight;
            }
        }
        mWindowManager.updateViewLayout(this, mWmParams);
        mTouchStartX = mTouchStartY = 0;
    }

}
