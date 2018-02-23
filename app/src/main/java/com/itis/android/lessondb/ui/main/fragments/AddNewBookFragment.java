package com.itis.android.lessondb.ui.main.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.utils.DatePickerFragment;
import com.itis.android.lessondb.ui.utils.OnDateListener;

import java.util.Calendar;
import java.util.Date;

import static com.itis.android.lessondb.ui.utils.Const.isRoom;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewBookFragment extends BaseFragment implements OnDateListener,View.OnClickListener {

    private final String SHOW_DATE_DIALOG = "date_dialog";

    private EditText etName;
    private EditText etAuthor;
    private EditText etGenre;
    private EditText etDesc;

    private Button btnReleaseDate;

    private AppCompatButton btnAddBook;

    private Date releaseDate;

    public static Fragment newInstance() {
        return new AddNewBookFragment();
    }

    public static Fragment newInstance(Bundle args) {
        AddNewBookFragment fragment = new AddNewBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etName = view.findViewById(R.id.et_name);
        etAuthor = view.findViewById(R.id.et_author);
        etGenre = view.findViewById(R.id.et_genre);
        etDesc = view.findViewById(R.id.et_desc);

        btnAddBook = view.findViewById(R.id.btn_add);
        btnReleaseDate = view.findViewById(R.id.btn_release_date);

        btnAddBook.setOnClickListener(this);
        btnReleaseDate.setOnClickListener(this);

        return view;
    }

    public void onPickDateClicked(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setReleaseListener(this);
        newFragment.show(getFragmentManager(), SHOW_DATE_DIALOG);
    }

    public void onAddBookClicked(View v) {
        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (isRoom) {
            roomFlow(name, authorName, genre, desc);
        } else {
            realmFlow(name, authorName, genre, desc);
        }

        Toast.makeText(this.getActivity(), getString(R.string.add_book), Toast.LENGTH_SHORT).show();

        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_add:
                onAddBookClicked(v);
                break;

            case R.id.btn_release_date:
                onPickDateClicked(v);
                break;
        }
    }

    private void realmFlow(String name, String authorName, String genre, String desc) {
        if(!checkIsRealmBookExist(name,authorName,genre)) {
            RealmBook book = new RealmBook();
            book.setTitle(name);
            book.setDesc(desc);
            book.setGenre(genre);
            book.setReleaseDate(releaseDate);
            RealmAuthor realmAuthor = checkRealmAuthor(authorName);
            realmAuthor.setName(authorName);
            RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
            book.setRealmAuthor(realmAuthor);
            RepositryProvider.provideBookRepository().insertBook(book);
        }
    }

    private void roomFlow(String name, String authorName, String genre, String desc) {
       if(!checkIsRoomBookExist(name,authorName,genre)) { //чтобы не было одинаковых книг у юзеров.
           RoomBook book = new RoomBook();
           book.setTitle(name);
           book.setDesc(desc);
           book.setGenre(genre);
           book.setReleaseDate(releaseDate);
           RoomAuthor author = checkRoomAuthor(authorName); //проверка на существование автора
           if (author.getName() == null) {
               author.setName(authorName);
               long authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
               author.setId(authorId);
           }
           book.setAuthorId(author.getId());
           AppDatabase.getAppDatabase().getBookDao().insertBook(book);
       }
    }

    private RoomAuthor checkRoomAuthor(String authorName) throws IllegalArgumentException{
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorByName(authorName);
        if(author == null){
            author = new RoomAuthor();
        }
        return author;
    }

    private RealmAuthor checkRealmAuthor(String authorName){
        RealmAuthor author = RepositryProvider.provideAuthorRepository().getAuthorByName(authorName);
        if(author == null){
            author = new RealmAuthor();
        }
        return author;
    }

    private boolean checkIsRoomBookExist(String name, String authorName, String genre) throws IllegalArgumentException{
        return AppDatabase.getAppDatabase().getBookDao().isRoomBookExist(name,authorName,genre).size() > 0;
    }

    private boolean checkIsRealmBookExist(String name, String authorName, String genre){
        return true;
    }
    @Override
    public void onDateChanged(Calendar calendar) {
        releaseDate = calendar.getTime();
        String date = calendar.get(Calendar.DAY_OF_MONTH)+ "." +
                                    calendar.get(Calendar.MONTH) + "." +
                                    calendar.get(Calendar.YEAR);
        btnReleaseDate.setText(date);
    }
}
