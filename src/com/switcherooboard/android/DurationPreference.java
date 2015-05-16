package com.switcherooboard.android;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class DurationPreference extends EditTextPreference {

    private static final String TAG = "DurationPreference";

    /* EditTextPreference */

    public DurationPreference(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }

    public DurationPreference(Context context, AttributeSet attrs) {
      super(context, attrs);
  	}

    public DurationPreference(Context context) {
      super(context);
    }

    @Override
    public void setText(String text) {
      long duration = Long.valueOf(text);

      duration = Math.round(duration/100.0);
      text = Long.toString(duration);

      super.setText(text);
    }

    @Override
    public String getText() {
      String text = super.getText();
      long duration = Long.valueOf(text);

      duration = duration * 100;
      text = Long.toString(duration);

      return text;
    }

}
