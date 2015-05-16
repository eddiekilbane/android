package com.switcherooboard.android;

import android.os.Parcelable;

interface ISwitcheroo extends Parcelable {
    String getAddress();
    void connect(final ISwitcherooCallback callback);
    boolean flipRelay(final int index, final boolean state, Integer duration);
    void disconnect();
}
