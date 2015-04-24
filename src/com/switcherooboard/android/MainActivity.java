package com.switcherooboard.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements ISwitcherooCallback {

    private static final String TAG = "MainActivity";

    private ISwitcheroo mSwitcheroo;

    /* Activity */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getFragmentManager().beginTransaction()
            .add(android.R.id.content, new ProgressFragment())
            .commit();

        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        this.mSwitcheroo = this.getIntent().getParcelableExtra(ScanActivity.EXTRA_SWITCHEROO);
        this.mSwitcheroo.connect(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (this.isFinishing()) {
            this.mSwitcheroo.disconnect();
        }
    }

    /* ISwitcherooCallback */

    public void onSwitcherooConnected() {
        this.getFragmentManager().beginTransaction()
            .replace(android.R.id.content, new ControlFragment())
            .commit();
    }

    public void onSwitcherooDisconnected() {
        this.finish();
    }

    /* */

    protected boolean flipRelay(final int index, final boolean state, final Integer duration) {
        return this.mSwitcheroo.flipRelay(index, state, duration);
    }

}
