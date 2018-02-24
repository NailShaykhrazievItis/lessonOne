package com.itis.android.lessondb.ui.fragments.single;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.fragments.lists.book.main_book_list.BookListFragment;
import com.itis.android.lessondb.utils.FragmentHelper;
import com.itis.android.lessondb.utils.SimpleSession;
import com.itis.android.lessondb.utils.VidHelper;

import static com.itis.android.lessondb.utils.Const.isRoom;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class EnterFragment extends BaseFragment implements View.OnClickListener{

    private TextInputLayout tiUsername;

    private TextInputLayout tiPassword;

    private EditText etUsername;

    private EditText etPassword;

    private AppCompatButton btnEnter;

    public static Fragment newInstance() {

        return new EnterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_enter, container, false);

        etUsername = view.findViewById(R.id.et_name);
        etPassword = view.findViewById(R.id.et_password);
        tiUsername = view.findViewById(R.id.ti_username);
        tiPassword =view.findViewById(R.id.ti_password);
        btnEnter = view.findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(isRoom){
            checkRoomReader(username,password);
        } else {
            checkRealmReader(username,password);
        }
    }

    private void checkRoomReader(String username, String password) {
        if(!VidHelper.getReaderName().equals(username)) {
            RoomReader reader = AppDatabase.getAppDatabase().getReaderDao().getReaderByNameAndPassword(username, password);
            if (reader != null) {
                SimpleSession.setRoomReader(reader);
                goToBookList();
            } else {
                tiPassword.setError(getString(R.string.enter_correct_password));
                tiUsername.setError(getString(R.string.enter_correct_name));
                Toast.makeText(this.getActivity(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkRealmReader(String username, String password){
        if(!VidHelper.getReaderName().equals(username)) {
            RealmReader reader = RepositryProvider.provideReaderRepository().getReaderByNameAndPassword(username, password);
            if (reader != null) {
                SimpleSession.setRealmReader(reader);
                goToBookList();
            } else {
                tiPassword.setError(getString(R.string.enter_correct_password));
                tiUsername.setError(getString(R.string.enter_correct_name));
                Toast.makeText(this.getActivity(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToBookList(){
        Fragment fragment = BookListFragment.newInstance();
        FragmentHelper.changeFragment(this,fragment);
    }
}
