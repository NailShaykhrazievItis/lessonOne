package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.itis.android.lessondb.R;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString();
        String author = etAuthor.getText().toString();
    }
}
