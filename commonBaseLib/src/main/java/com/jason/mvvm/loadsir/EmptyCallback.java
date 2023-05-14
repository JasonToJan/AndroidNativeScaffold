package com.jason.mvvm.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.jason.mvvm.R;


/**
 * Description: 空的回调
 */
public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
