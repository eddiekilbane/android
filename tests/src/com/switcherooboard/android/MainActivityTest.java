package com.switcherooboard.android;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import java.util.concurrent.CountDownLatch;

// import org.mockito.Mockito;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = "MainActivityTest";

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        this.setActivityIntent(new Intent().putExtra(ScanActivity.EXTRA_SWITCHEROO, new LocalSwitcheroo() {
            @Override
            public void connect(final ISwitcherooCallback callback) {
                android.util.Log.d(TAG, "LocalSwitcheroo#connect()");
            }

            @Override
            public boolean flipRelay(final int index, final boolean state, Integer duration) {
                android.util.Log.d(TAG, "LocalSwitcheroo#flipRelay" + "(" +
                    Integer.toString(index) + "," +
                    Boolean.toString(state) +
                ")");

                return true;
            }

            @Override
            public synchronized void disconnect() {
                android.util.Log.d(TAG, "LocalSwitcheroo#disconnect()");
            }
        }));

        this.setActivityInitialTouchMode(true);

        this.getActivity();
    }

    public void testManually() throws InterruptedException {
        // TODO Replace this ugly hack with a new 'Instrumentation' subclass (launch using 'am' command)

        android.util.Log.d(TAG, "MainActivityTest#test()");

        final CountDownLatch latch = new CountDownLatch(1);

        Application.ActivityLifecycleCallbacks callback = new Application.ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
            public void onActivityPaused(Activity activity) {}
            public void onActivityResumed(Activity activity) {}
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            public void onActivityStarted(Activity activity) {}
            public void onActivityStopped(Activity activity) {}
            public void onActivityDestroyed(Activity activity) {
                if (activity.isFinishing()) {
                    latch.countDown();
                }
            }
        };

        this.getActivity().getApplication().registerActivityLifecycleCallbacks(callback);

        latch.await();

        this.getActivity().getApplication().unregisterActivityLifecycleCallbacks(callback);
    }

}
