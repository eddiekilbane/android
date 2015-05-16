package com.switcherooboard.android;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends PreferenceFragment {

    private static final String TAG = "SettingsFragment";

    /* Fragment */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

        PreferenceManager manager = this.getPreferenceManager();
        String address = ((MainActivity) this.getActivity()).getAddress();
        manager.setSharedPreferencesName(manager.getSharedPreferencesName() + "." + address);

        this.addPreferencesFromResource(R.xml.settings);

        this.findPreference("photo_preference").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent()
                    .setType("image/*")
                    .setAction(Intent.ACTION_GET_CONTENT)
                    .putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                SettingsFragment.this.startActivityForResult(Intent.createChooser(intent, null), 0);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        android.util.Log.d(TAG, "onActivityResult()");
        // TODO data.getExtras().get("data");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.getFragmentManager().popBackStack();
                return true;
            }

            default: {
                return false;
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        TypedValue value = new TypedValue();
        // this.getActivity().getTheme().resolveAttribute(android.R.attr.colorBackground, value, true);
        this.getActivity().getTheme().resolveAttribute(android.R.attr.windowBackground, value, true);
        view.setBackgroundResource(value.resourceId);

        return view;
    }

}
