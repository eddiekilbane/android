package com.switcherooboard.android;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Semaphore;
import java.util.UUID;

public class ControlActivity extends Activity {

    private static final String TAG = "Switcheroo.ControlActivity";

    static final UUID SERVICE_UUID = UUID.fromString("00000015-9d7a-4919-b570-3bb24a4bf68e");
    static final UUID CHARACTERISTIC_21_UUID = UUID.fromString("00000021-9d7a-4919-b570-3bb24a4bf68e");
    static final UUID CHARACTERISTIC_22_UUID = UUID.fromString("00000022-9d7a-4919-b570-3bb24a4bf68e");
    static final UUID CHARACTERISTIC_23_UUID = UUID.fromString("00000023-9d7a-4919-b570-3bb24a4bf68e");
    static final UUID CHARACTERISTIC_24_UUID = UUID.fromString("00000024-9d7a-4919-b570-3bb24a4bf68e");

    BluetoothDevice mBluetoothDevice;
    BluetoothGatt mBluetoothGatt;
    BluetoothGattService mBluetoothGattService;

    final Semaphore mSemaphore = new Semaphore(0);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);

        mBluetoothDevice = getIntent().getParcelableExtra("com.switcherooboard.android.BLUETOOTHDEVICE");
    }

    @Override
    public void onStart() {
        super.onStart();

        mBluetoothGatt = mBluetoothDevice.connectGatt(this, false, mBluetoothGattCallback);

        try {
            mSemaphore.acquire();
        } catch(java.lang.InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            android.util.Log.d(TAG, "onConnectionStateChange");

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                android.util.Log.d(TAG, "onConnectionStateChange -> STATE_CONNECTED");
                mBluetoothGatt.discoverServices();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            android.util.Log.d(TAG, "onServicesDiscovered");

            if (status == BluetoothGatt.GATT_SUCCESS) {
                android.util.Log.d(TAG, "onServicesDiscovered -> GATT_SUCCESS");
                mBluetoothGattService = mBluetoothGatt.getService(SERVICE_UUID);
                mSemaphore.release();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onSwitchClick(View view) {
        SwitchButton button = (SwitchButton) view;

        final BluetoothGattCharacteristic characteristic;

        if (button.getId() == R.id.switch_1) {
            characteristic = mBluetoothGattService.getCharacteristic(CHARACTERISTIC_21_UUID);
        } else if (button.getId() == R.id.switch_2) {
            characteristic = mBluetoothGattService.getCharacteristic(CHARACTERISTIC_22_UUID);
        } else if (button.getId() == R.id.switch_3) {
            characteristic = mBluetoothGattService.getCharacteristic(CHARACTERISTIC_23_UUID);
        } else if (button.getId() == R.id.switch_4) {
            characteristic = mBluetoothGattService.getCharacteristic(CHARACTERISTIC_24_UUID);
        } else {
            throw new RuntimeException();
        }

        if (button.isChecked()) {
            characteristic.setValue(new byte[] { (byte) 0x01, (byte) 0x00 });
        } else {
            characteristic.setValue(new byte[] { (byte) 0x00, (byte) 0x00 });
        }

        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
