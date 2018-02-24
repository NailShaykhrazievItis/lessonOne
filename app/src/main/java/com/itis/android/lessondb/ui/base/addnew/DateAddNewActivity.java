package com.itis.android.lessondb.ui.base.addnew;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.itis.android.lessondb.ui.base.datepicker.DatePickerFragment;
import com.itis.android.lessondb.ui.base.datepicker.DatePickerResolver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public abstract class DateAddNewActivity extends AddNewActivity implements DatePickerResolver {
    protected Button btnDate;
    protected Date date;

    protected FragmentManager fragmentManager;

    @Override
    public void resolveDatePick(Date date) {
        if (btnDate != null) {
            this.date = date;

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            btnDate.setText(format.format(date));
        }
    }

    protected void datePickAction() {
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        DatePickerFragment pickerFragment = DatePickerFragment.newInstance(this);
        pickerFragment.show(fragmentManager, "date_picker");
    }

    @Override
    protected abstract void onButtonAddClicked(View view);
}
