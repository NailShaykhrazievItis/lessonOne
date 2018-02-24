package com.itis.android.lessondb.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.fragments.single.EnterFragment;
import com.itis.android.lessondb.ui.fragments.single.RegistrationFragment;
import com.itis.android.lessondb.utils.Const;
import com.itis.android.lessondb.utils.SimpleSession;
import com.itis.android.lessondb.utils.VidHelper;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class StartFragment extends BaseFragment implements View.OnClickListener {

    private Button enterBtn;

    private Button registrBtn;

    private String FRAGMENT_START_TAG = "Start_TAG";

    public static Fragment newInstance() {

        return new StartFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        RoomReader reader = SimpleSession.getRoomReader();

        Log.d(Const.TAG_LOG,"is reader null = " + (reader == null));

        View view = inflater.inflate(R.layout.fragment_start, container, false);

            enterBtn = view.findViewById(R.id.btn_enter);
            registrBtn = view.findViewById(R.id.btn_reg);

            enterBtn.setOnClickListener(this);
            registrBtn.setOnClickListener(this);

            VidHelper.createTypicalReader(); //костыль

            Log.d(Const.TAG_LOG, "readerId = " + VidHelper.getDebtorId());

        return view;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btn_enter:
                signIn();
                break;

            case R.id.btn_reg:
                goToRegistration();
                break;
        }
    }

    private void signIn() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, EnterFragment.newInstance())
                .commit();
    }

    private void goToRegistration() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, RegistrationFragment.newInstance())
                .addToBackStack(FRAGMENT_START_TAG)
                .commit();
    }
}
