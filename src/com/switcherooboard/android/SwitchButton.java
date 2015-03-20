package com.switcherooboard.android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class SwitchButton extends CompoundButton {

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0x0101007e); // radioButtonStyle
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
