package com.ym.floatmenu.path.demo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 项目名称：androidUITraFloatLayout
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/9/14 17:12
 * 修改人：wengyiming
 * 修改时间：2016/9/14 17:12
 * 修改备注：
 */
public class FloatLayout extends FrameLayout implements View.OnTouchListener {
    //WindowMananger的params，控制这个值可以将自定义的view设置到窗口管理器中
    private WindowManager.LayoutParams mWmParams;
    private WindowManager mWindowManager;//当前view的窗口管理器
    private Context mContext;//一个全局上下文，demo使用的service的上下文

    private float mTouchStartX;//记录首次按下的位置x
    private float mTouchStartY;//记录首次按下的位置y

    private int mScreenWidth;//屏幕宽度
    private int mScreenHeight;//屏幕高度
    private boolean mDraging;//是否拖动中


    private FlHandler mFlHandler;

    public FloatLayout(Context context) {
        super(context);
        init(context);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        initVM();
    }


    public void setFlHandler(FlHandler flHandler) {
        mFlHandler = flHandler;
        View rootView = mFlHandler.onCreateView(LayoutInflater.from(mContext), this);//将xml布局导入当前View
//        rootView.setOnTouchListener(this);
        rootView.measure(MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED), MeasureSpec
                .makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        addView(rootView);
        //将当前view设置为前置窗口
        bringToFront();
        mWindowManager.addView(this, mWmParams);
        mFlHandler.onViewCreated(this);
    }

    public void attachToView(View view){
        view.setOnTouchListener(this);
    }



    private void initVM() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);//获取系统的窗口服务
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;//根据当前屏幕信息拿到屏幕的宽高
        mScreenHeight = dm.heightPixels;

        this.mWmParams = new WindowManager.LayoutParams();//获取窗口参数

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWmParams.type = WindowManager.LayoutParams.TYPE_TOAST;//等于API19或API19以下需要指定窗口参数type值为TYPE_TOAST才可以作为悬浮控件显示出来
        } else {
            mWmParams.type = WindowManager.LayoutParams.TYPE_PHONE;//API19以上侧只需指定为TYPE_PHONE即可
        }
        mWmParams.format = PixelFormat.RGBA_8888;//当前窗口的像素格式为RGBA_8888,即为最高质量


        //NOT_FOCUSABLE可以是悬浮控件可以响应事件，LAYOUT_IN_SCREEN可以指定悬浮球指定在屏幕内，部分虚拟按键的手机，虚拟按键隐藏时，虚拟按键的位置则属于屏幕内，此时悬浮球会出现在原虚拟按键的位置
        mWmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        //默认指定位置在屏幕的左上方，可以根据需要自己修改
        mWmParams.gravity = Gravity.LEFT | Gravity.TOP;

        //默认指定的横坐标为屏幕最左边
        mWmParams.x = 0;

        //默认指定的纵坐标为屏幕高度的10分之一，这里只是大概约束，因为上的flags参数限制，悬浮球不会出现在屏幕外
        mWmParams.y = mScreenHeight / 10;

        //宽度指定为内容自适应
        mWmParams.width = LayoutParams.WRAP_CONTENT;
        mWmParams.height = LayoutParams.WRAP_CONTENT;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

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

                float deltX = Math.abs(mTouchStartX - mMoveStartX);
                float deltY = Math.abs(mTouchStartY - mMoveStartY);
                if (deltX > 3 && deltY > 3) {
                    mDraging = true;
                    mWmParams.x = (int) (x - mTouchStartX);
                    mWmParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(this, mWmParams);
                    mFlHandler.onFloatMoving(mDraging, deltX > 3 ? deltX : 0, deltY > 3 ? deltY : 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mDraging = false;
            case MotionEvent.ACTION_CANCEL:
                if (mWmParams.x >= mScreenWidth / 2) {
                    mWmParams.x = mScreenWidth;
                    mFlHandler.onMoveCompletedLocation(true);
                } else if (mWmParams.x < mScreenWidth / 2) {
                    mWmParams.x = 0;
                    mFlHandler.onMoveCompletedLocation(false);
                }
                mWindowManager.updateViewLayout(this, mWmParams);
                mTouchStartX = mTouchStartY = 0;
                return false;
        }
        return false;
    }


}
