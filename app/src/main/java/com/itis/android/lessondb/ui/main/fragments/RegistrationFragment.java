package com.itis.android.lessondb.ui.main.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.main_book_list.BookListFragment;
import com.itis.android.lessondb.ui.utils.DatePickerFragment;
import com.itis.android.lessondb.ui.utils.OnDateListener;
import com.itis.android.lessondb.ui.utils.SimpleSession;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.itis.android.lessondb.ui.utils.Const.isRoom;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class RegistrationFragment extends BaseFragment implements View.OnClickListener,OnDateListener {

    private final String SHOW_DATE_DIALOG = "date_dialog";

    private EditText etUsername;

    private EditText etPassword;

    private EditText etCity;

    private AppCompatButton btnBirthday;

    private AppCompatButton btnRegistrNewReader;

    private TextInputLayout tiUsername;

    private Date birthday;

    public static Fragment newInstance() {

        return new RegistrationFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_registration , container, false);

        etUsername = view.findViewById(R.id.et_name);
        etPassword = view.findViewById(R.id.et_password);
        etCity = view.findViewById(R.id.et_city);
        btnBirthday = view.findViewById(R.id.btn_sel_birthday);
        btnRegistrNewReader = view.findViewById(R.id.btn_sign_up);

        tiUsername = view.findViewById(R.id.ti_username);

        setEditListener();

        btnBirthday.setOnClickListener(this);
        btnRegistrNewReader.setOnClickListener(this);

        return view;
    }

    public boolean shouldShowError() {
        String username = etUsername.getText().toString();
        List<RoomReader> readers = AppDatabase.getAppDatabase().getReaderDao().findExistReader(username);
        return readers.size() > 0 && username.length() > 0;
    }

    public void showError() {
        tiUsername.setError(getString(R.string.name_already_exist));
    }

    public void hideError() {
        tiUsername.setError("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sel_birthday:
                showDatePicker();
                break;

            case R.id.btn_sign_up:
                signUpNewReader();
                break;
        }
    }

    private void showDatePicker(){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setReleaseListener(this);
        newFragment.show(getFragmentManager(),SHOW_DATE_DIALOG);
    }

    private void signUpNewReader(){
        if(!shouldShowError()) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String city = etCity.getText().toString();

            if (isRoom) {
                roomFlow(username, password, city);
            } else {
                realmFlow(username, password, city);
            }
        } else {
            showError();
        }
    }

    private void goToBookList(RoomReader reader){

        SimpleSession.setRoomReader(reader);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, BookListFragment.newInstance())
                .commit();
    }

    private void realmFlow(String username, String password, String city) {
        RealmReader reader = new RealmReader();
        reader.setUsername(username);
        reader.setPassword(password);
        reader.setCity(city);
        reader.setBirthday(birthday);
        RepositryProvider.provideReaderRepository().insertReader(reader);
        SimpleSession.setRealmReader(reader);
    }

    private void roomFlow(String username, String password, String city) {
        RoomReader reader = new RoomReader();
        reader.setUsername(username);
        reader.setPassword(password);
        reader.setCity(city);
        reader.setBirthday(birthday);
        long id = AppDatabase.getAppDatabase().getReaderDao().insertReader(reader);
        reader.setId(id);

        goToBookList(reader);
    }


    @Override
    public void onDateChanged(Calendar calendar) {
        birthday = calendar.getTime();
        String date = calendar.get(Calendar.DAY_OF_MONTH)+ "." +
                                    calendar.get(Calendar.MONTH) + "." +
                                    calendar.get(Calendar.YEAR);
        btnBirthday.setText(date);
    }

    private void setEditListener(){
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (shouldShowError()) {
                    showError();
                } else {
                    hideError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }
}
