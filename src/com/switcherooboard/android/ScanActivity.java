package com.switcherooboard.android;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class ScanActivity extends Activity implements OnItemClickListener {

  private static final String TAG = "ScanActivity";

  private BluetoothAdapter mBluetoothAdapter;

  private GridView mGridView;

  private ScanList mScanList;

  private ArrayAdapter mArrayAdapter;

  /* Activity */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.scan);

    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    this.mScanList = (ScanList) this.getLastNonConfigurationInstance();

    if (this.mScanList == null) {
      this.mScanList = new ScanList();
      this.mBluetoothAdapter.startLeScan(this.mScanList);
    }

    this.mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.mScanList);

    this.mScanList.setArrayAdapter(this.mArrayAdapter);

    this.mGridView = (GridView) this.findViewById(R.id.gridview);

    this.mGridView.setAdapter(this.mArrayAdapter);

    this.mGridView.setOnItemClickListener(this);
  }

  @Override
  public Object onRetainNonConfigurationInstance() {
    this.mScanList.setArrayAdapter(null);
    return this.mScanList;
  }

  @Override
  public void onPause() {
    super.onPause();

    if (this.isFinishing()) {
      this.mBluetoothAdapter.stopLeScan(this.mScanList);
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    if (this.isFinishing()) {
      this.mBluetoothAdapter.stopLeScan(this.mScanList);
    }
  }

  /* */

  public static final String EXTRA_SWITCHEROO = "com.switcherooboard.android.extra.SWITCHEROO";

  /* OnItemClickListener */

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent intent = new Intent(ScanActivity.this, MainActivity.class);

    final BluetoothDevice device = (BluetoothDevice) parent.getAdapter().getItem(position);
    intent.putExtra(ScanActivity.EXTRA_SWITCHEROO, new GattSwitcheroo(device.getAddress()));

    this.startActivity(intent);
  }

  /* */

  private static class ScanList extends ArrayList<BluetoothDevice> implements BluetoothAdapter.LeScanCallback {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private ArrayAdapter mArrayAdapter;

    public void setArrayAdapter(ArrayAdapter adapter) {
      this.mArrayAdapter = adapter;
    }

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
      android.util.Log.d(TAG, "onLeScan" + " -> " + "\"" + device.getName() + "\"");

      if (device.getName().equals("Switcheroo")) {
        this.mHandler.post(new Runnable() {
          @Override
          public void run() {
            if (!ScanList.this.contains(device)) {
              ScanList.this.add(device);

              if (ScanList.this.mArrayAdapter != null) {
                ScanList.this.mArrayAdapter.notifyDataSetChanged();
              }
            }
          }
        });
      }
    }
  }

}
