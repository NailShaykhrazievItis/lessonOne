package com.itis.android.lessondb.ui.fragments.lists.vid;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.realm.entity.RealmVid;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.room.entity.RoomVid;
import com.itis.android.lessondb.utils.DateUtil;
import com.itis.android.lessondb.utils.VidHelper;

import static com.itis.android.lessondb.utils.Const.isRoom;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class VidDetailsFragment extends Fragment implements View.OnClickListener{

    private TextView tvReaderFieldName;

    private TextView tvReaderName;
    private TextView tvBookName;
    private TextView tvStartDate;
    private TextView tvEndDate;

    private AppCompatButton btnBookAction;

    private long vidId;
    private String readerRole;

    public static Fragment newInstance() {

        return new VidDetailsFragment();
    }

    public static Fragment newInstance(Bundle args) {
        VidDetailsFragment fragment = new VidDetailsFragment();
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

        View view = inflater.inflate(R.layout.fragment_vid_details, container, false);

        vidId = getArguments().getLong(getString(R.string.vidId), 0L);
        readerRole = getArguments().getString(getString(R.string.readerRole));

        initFields(view);

        if (isRoom) {
            roomFlow(vidId);
        } else {
            realmFlow(vidId);
        }

        return view;
    }

    private void initFields(View view) {
        tvReaderFieldName = view.findViewById(R.id.tv_field_name_reader_);

        tvReaderName = view.findViewById(R.id.tv_vid_name_reader);
        tvBookName = view.findViewById(R.id.tv_book_name);
        tvStartDate =  view.findViewById(R.id.tv_start_date);
        tvEndDate =  view.findViewById(R.id.tv_end_date);

        btnBookAction = view.findViewById(R.id.btn_book_action);
        btnBookAction.setOnClickListener(this);

        if(readerRole.equals(getString(R.string.debtor))){
            tvReaderFieldName.setText(getString(R.string.debtor_name));
        } else {
            tvReaderFieldName.setText(getString(R.string.lender_name));
        }
    }

    private void realmFlow(long id) {

        RealmVid vid = RepositryProvider.provideVidRepository().getVidById(id);
        RealmReader reader;

        if(readerRole.equals(getString(R.string.debtor))){
            reader = vid.getDebtor();
        }else{
            reader = vid.getLender();
        }

        RealmBook book= vid.getBook();

        tvReaderName.setText(reader.getUsername());
        tvBookName.setText(book.getTitle());
        tvStartDate.setText(DateUtil.getDateInStr(vid.getStart_date()));
        tvEndDate.setText(DateUtil.getDateInStr(vid.getEnd_date()));
    }

    private void roomFlow(long id) {

        RoomVid vid = AppDatabase.getAppDatabase().getVidDao().getVidById(id);
        RoomReader reader;

        if(readerRole.equals(getString(R.string.debtor))){
            reader = AppDatabase.getAppDatabase().getReaderDao().getReaderById(vid.getDebtorId());
        }else{
            reader = AppDatabase.getAppDatabase().getReaderDao().getReaderById(vid.getLenderId());
        }

        RoomBook book= AppDatabase.getAppDatabase().getBookDao().getBookById(vid.getBookId());

        tvReaderName.setText(reader.getUsername());
        tvBookName.setText(book.getTitle());
        tvStartDate.setText(DateUtil.getDateInStr(vid.getStart_date()));
        tvEndDate.setText(DateUtil.getDateInStr(vid.getEnd_date()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_book_action:
                takeBookBack();
                break;
        }
    }

    private void takeBookBack(){
        AppDatabase
                .getAppDatabase()
                .getVidDao()
                .updateVid(vidId,VidHelper.getDebtorId(),
                            VidHelper.getTypicalDate().getTime(),VidHelper.getTypicalDate().getTime());

        Toast.makeText(this.getActivity(),"takeBookBack",Toast.LENGTH_LONG).show();

        getFragmentManager().popBackStack();
    }


}
