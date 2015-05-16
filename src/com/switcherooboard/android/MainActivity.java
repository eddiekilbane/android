package com.switcherooboard.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private SwitcherooFragment mSwitcherooFragment;

    private AtomicBoolean mDisconnected = new AtomicBoolean();

    /* Activity */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        this.mSwitcherooFragment = (SwitcherooFragment) this.getFragmentManager().findFragmentByTag("switcheroo");

        if (this.mSwitcherooFragment == null) {
            this.mSwitcherooFragment = new SwitcherooFragment();

            this.getFragmentManager().beginTransaction()
                .add(this.mSwitcherooFragment, "switcheroo")
                .commit();

            this.getFragmentManager().beginTransaction()
                .add(android.R.id.content, new ProgressFragment())
                .commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (this.isFinishing() && this.mDisconnected.compareAndSet(false, true)) {
            this.mSwitcherooFragment.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (this.isFinishing() && this.mDisconnected.compareAndSet(false, true)) {
            this.mSwitcherooFragment.disconnect();
        }
    }

    /* */

    protected void onSwitcherooConnected() {
        this.getFragmentManager().beginTransaction()
            .replace(android.R.id.content, new ControlFragment())
            .commit();
    }

    protected void onSwitcherooDisconnected() {
        if (this.mDisconnected.compareAndSet(false, true)) {
            this.finish();
        }
    }

    /* */

    protected String getAddress() {
      return this.mSwitcherooFragment.getAddress();
    }

    protected boolean flipRelay(final int index, final boolean state, final Integer duration) {
        return this.mSwitcherooFragment.flipRelay(index, state, duration);
    }

}
