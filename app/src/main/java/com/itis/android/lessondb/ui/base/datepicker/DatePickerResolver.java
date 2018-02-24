package com.itis.android.lessondb.ui.base.datepicker;

import com.itis.android.lessondb.ui.base.DatabaseTypeConstant;

import java.util.Date;

/**
 * Created by valera071998@gamil.com on 20.02.2018.
 */

public interface DatePickerResolver extends DatabaseTypeConstant {
    void resolveDatePick(Date date);
}
