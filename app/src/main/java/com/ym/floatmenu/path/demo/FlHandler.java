package com.ym.floatmenu.path.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：androidUITraFloatLayout
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/9/14 17:13
 * 修改人：wengyiming
 * 修改时间：2016/9/14 17:13
 * 修改备注：
 */
public interface FlHandler {

    View onCreateView(LayoutInflater inflater, ViewGroup viewGroup);

    void onViewCreated(FloatLayout parent);

    void onFloatMoving(boolean isMoving, float deltX, float deltY);

    void onMoveCompletedLocation(boolean isRight);


}
