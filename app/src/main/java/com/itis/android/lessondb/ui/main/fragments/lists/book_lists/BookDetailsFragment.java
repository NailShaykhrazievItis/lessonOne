package com.itis.android.lessondb.ui.main.fragments.lists.book_lists;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.room.entity.RoomVid;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.VidItem;
import com.itis.android.lessondb.ui.utils.Const;
import com.itis.android.lessondb.ui.utils.DateUtil;
import com.itis.android.lessondb.ui.utils.SimpleSession;
import com.itis.android.lessondb.ui.utils.VidHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.itis.android.lessondb.ui.utils.Const.isRoom;

public class BookDetailsFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvGenre;
    private TextView tvReleaseDate;
    private TextView tvDesc;

    private AppCompatButton btnDebtBook;
    private AppCompatButton btnLendBook;

    private AlertDialog.Builder dialogBuilder;

    private long bookId;
    private long vidId;

    private List<VidItem> vidItems;

    public static Fragment newInstance() {

        return new BookDetailsFragment();
    }

    public static Fragment newInstance(Bundle args) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        bookId = getArguments().getLong(getString(R.string.bookId), 0L);
        boolean isOwnBook = getArguments().getBoolean(getString(R.string.isOwnBook));

        initFields(view);

        if(isOwnBook){
            setBtnActionsInvisible();
        }

        if (isRoom) {
            roomFlow(bookId);
        } else {
            realmFlow(bookId);
        }

        return view;
    }

    private void initFields(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvAuthor = view.findViewById(R.id.tv_author);
        tvGenre =  view.findViewById(R.id.tv_field_one);
        tvReleaseDate =  view.findViewById(R.id.tv_field_two);
        tvDesc =  view.findViewById(R.id.tv_field_three);
        btnLendBook = view.findViewById(R.id.btn_have_book);
        btnDebtBook = view.findViewById(R.id.btn_get_book);

        initPickLendDialog();

        btnLendBook.setOnClickListener(this);
        btnDebtBook.setOnClickListener(this);

        checkReaderConstraints();
    }

    private void initPickLendDialog(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getString(R.string.lender_choice));
        dialogBuilder.setItems(getVidNames(), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                BookDetailsFragment.this.vidId = vidItems.get(which).getId();
                BookDetailsFragment.this.addAfterClick();
            }

        });
    }

    private String[] getVidNames(){
        List<RoomVid> vids = AppDatabase.getAppDatabase().getVidDao().getVidByBookWithLimit(bookId);
        vidItems = getVidItems(vids);
        String [] names = new String[vidItems.size()];
        for(int i = 0; i < names.length; i++){
            names[i] = vidItems.get(i).getReaderName();
        }
        return names;

    }

    private void realmFlow(long id) {

        RealmBook book = RepositryProvider.provideBookRepository().getBookById(id);

        tvName.setText(book.getTitle());
        tvAuthor.setText(book.getRealmAuthor().getName());
        tvGenre.setText(book.getGenre().toString());
        tvReleaseDate.setText(book.getReleaseDate().toString());
        tvDesc.setText(book.getDesc());
    }

    private void roomFlow(long id) {

        RoomBook book = AppDatabase.getAppDatabase().getBookDao().getBookById(id);
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId());

        tvName.setText(book.getTitle());
        tvAuthor.setText(author.getName());
        tvGenre.setText(book.getGenre().toString());
        tvReleaseDate.setText(DateUtil.getDateInStr(book.getReleaseDate()));
        tvDesc.setText(book.getDesc());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_get_book:
                addOneToDebts();
                break;

            case R.id.btn_have_book:
                addOneToLends();
                break;
        }
    }

    private void addOneToDebts(){
        dialogBuilder.show();
    }

    private void addAfterClick(){
        long debtorId = SimpleSession.getRoomReader().getId();

        RoomVid roomVid = AppDatabase.getAppDatabase().getVidDao().getVidById(vidId);
        roomVid.setDebtorId(debtorId);

        Date startDate = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + 10);

        roomVid.setStart_date(startDate);
        roomVid.setEnd_date(calendar.getTime());
        AppDatabase
                .getAppDatabase()
                .getVidDao()
                .updateVid(roomVid.getId(),roomVid.getDebtorId(),
                            roomVid.getStart_date().getTime(),
                            roomVid.getEnd_date().getTime());

        Toast.makeText(this.getActivity(),"addedToDebts",Toast.LENGTH_LONG).show();

       setBtnActionDisabled();
    }

    private void addOneToLends(){
        long lenderId = SimpleSession.getRoomReader().getId();

        RoomVid roomVid = new RoomVid();
        roomVid.setLenderId(lenderId);
        roomVid.setBookId(bookId);
        VidHelper.setAllNullParams(roomVid); // заглушки для Foreign Key
        AppDatabase.getAppDatabase().getVidDao().insertVid(roomVid);

        Log.d(Const.TAG_LOG,"lenderId + " + lenderId + "\nbookId = " + bookId +
                "\nendDate = " + VidHelper.getTypicalDate().toString() +
                "\ndebtorId = " + VidHelper.getDebtorId());

        Toast.makeText(this.getActivity(),"addedToLends",Toast.LENGTH_LONG).show();

        setBtnActionDisabled();

    }

    private List<VidItem> getVidItems(List<RoomVid> vids){
        List<VidItem> vidItems = new ArrayList<>();
        for(RoomVid vid : vids){
            vidItems.add(new VidItem(vid,true));
        }
        return vidItems;
    }

    private void setDebtBtnDisabled(){
        btnDebtBook.setEnabled(false);
    }

    private void setLendBtnDisabled(){
        btnLendBook.setEnabled(false);
    }

    private void setBtnActionDisabled(){
        setDebtBtnDisabled();
        setLendBtnDisabled();
    }

    private void setBtnActionsInvisible(){
        btnLendBook.setVisibility(View.GONE);
        btnDebtBook.setVisibility(View.GONE);
    }

    private void checkReaderConstraints(){
        RoomReader reader = SimpleSession.getRoomReader();
        List<RoomVid> vids = AppDatabase.getAppDatabase().getVidDao().getVidByBook(bookId);

        for(RoomVid vid: vids) {
            if (vid.getDebtorId() == reader.getId() || vid.getLenderId() == reader.getId()) {
                setBtnActionsInvisible();
                break;
            }
        }

    }

}
