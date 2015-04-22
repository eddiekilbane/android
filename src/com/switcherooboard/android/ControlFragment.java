package com.switcherooboard.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class ControlFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ControlFragment";

    /* Fragment */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.control_menu, menu);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control, container, false);

        ((SwitchButton) view.findViewById(R.id.switch_0)).setOnClickListener(this);
        ((SwitchButton) view.findViewById(R.id.switch_1)).setOnClickListener(this);
        ((SwitchButton) view.findViewById(R.id.switch_2)).setOnClickListener(this);
        ((SwitchButton) view.findViewById(R.id.switch_3)).setOnClickListener(this);

        return view;
    }

    /* OnClickListener */

    @Override
    public void onClick(View view) {
        SwitchButton button = (SwitchButton) view;

        final Integer index;

        if (button.getId() == R.id.switch_0) {
            index = 0;
        } else if (button.getId() == R.id.switch_1) {
            index = 1;
        } else if (button.getId() == R.id.switch_2) {
            index = 2;
        } else if (button.getId() == R.id.switch_3) {
            index = 3;
        } else {
            throw new NullPointerException();
        }

        final boolean state = button.isChecked();

        ((MainActivity) this.getActivity()).flipRelay(index, state, null);
    }

}
