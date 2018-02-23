package com.itis.android.lessondb.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 18.02.2018.
 */

public class MyDatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
    }

    @SuppressLint("SetTextI18n")
    private DatePickerDialog.OnDateSetListener dateSetListener =
            (view, year, month, day) -> {
                EditText edt = getActivity().findViewById(R.id.et_releaseDate);
                edt.setText(view.getYear() +
                        "-" + (view.getMonth() + 1) +
                        "-" + view.getDayOfMonth());
            };
}
