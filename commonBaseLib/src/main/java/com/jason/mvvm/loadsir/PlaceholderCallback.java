package com.jason.mvvm.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.jason.mvvm.R;


/**
 * Description: 占位回调
 */
public class PlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
