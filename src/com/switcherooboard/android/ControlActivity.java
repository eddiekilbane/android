package com.switcherooboard.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class ControlActivity extends Activity implements ISwitcherooCallback {

    private static final String TAG = "ControlActivity";

    private ISwitcheroo mSwitcheroo;

    public void onSwitchClick(View view) {
        SwitchButton button = (SwitchButton) view;

        final Integer index;

        if (button.getId() == R.id.switch_1) {
            index = 0;
        } else if (button.getId() == R.id.switch_2) {
            index = 1;
        } else if (button.getId() == R.id.switch_3) {
            index = 2;
        } else if (button.getId() == R.id.switch_4) {
            index = 3;
        } else {
            throw new NullPointerException();
        }

        final boolean state = button.isChecked();

        this.mSwitcheroo.flipRelay(index, state, null);
    }

    /* Activity */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.control);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        this.mSwitcheroo = this.getIntent().getParcelableExtra(ScanActivity.EXTRA_SWITCHEROO);
        this.mSwitcheroo.connect(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.control_menu, menu);
        return true;
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
    }

    public void onSwitcherooDisconnected() {
        this.finish();
    }

}
