package com.jason.mvvm.loadsir;


import com.kingja.loadsir.callback.Callback;
import com.jason.mvvm.R;


/**
 * Description: 错误回调
 */
public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
