package com.switcherooboard.android;

import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;

abstract class LocalSwitcheroo extends Binder implements ISwitcheroo {
    private static final String TAG = "LocalSwitcheroo";

    /* Parcelable */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStrongBinder(this);
    }

    public static final Parcelable.Creator<LocalSwitcheroo> CREATOR = new Parcelable.Creator<LocalSwitcheroo>() {
        public LocalSwitcheroo createFromParcel(Parcel parcel) {
            return (LocalSwitcheroo) parcel.readStrongBinder();
        }

        public LocalSwitcheroo[] newArray(int size) {
            return new LocalSwitcheroo[size];
        }
    };

}
