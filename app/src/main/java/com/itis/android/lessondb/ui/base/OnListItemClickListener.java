package com.itis.android.lessondb.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by valera071998@gamil.com on 20.02.2018.
 */

public interface OnListItemClickListener<T> {
    void onItemClick(@NonNull T item);
}
