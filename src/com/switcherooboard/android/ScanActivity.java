package com.switcherooboard.android;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class ScanActivity extends Activity implements BluetoothAdapter.LeScanCallback {

    private static final String TAG = "Switcheroo.ScanActivity";

    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;

    GridView mGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);

        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();

        mGridView = (GridView) findViewById(R.id.gridview);

        mGridView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1));

        mGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        android.util.Log.d(TAG, "startLeScan()");
        mBluetoothAdapter.startLeScan(this);
    }

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
        android.util.Log.d(TAG, "onLeScan" + " -> " + "\"" + device.getName() + "\"");

        if (device.getName().equals("Switcheroo")) {
            final ArrayAdapter adapter = (ArrayAdapter) mGridView.getAdapter();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter.getPosition(device) == -1) adapter.add(device);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        android.util.Log.d(TAG, "stopLeScan()");
        mBluetoothAdapter.stopLeScan(this);
    }

}
