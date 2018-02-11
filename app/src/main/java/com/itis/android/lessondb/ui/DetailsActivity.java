package com.itis.android.lessondb.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.itis.android.lessondb.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvFieldOne;
    private TextView tvFieldTwo;
    private TextView tvFieldThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initFields();
        String name = getIntent().getStringExtra("item");
        tvName.setText(name);
    }

    private void initFields() {
        tvName = findViewById(R.id.tv_name);
        tvAuthor = findViewById(R.id.tv_author);
        tvFieldOne = findViewById(R.id.tv_field_one);
        tvFieldTwo = findViewById(R.id.tv_field_two);
        tvFieldThree = findViewById(R.id.tv_field_three);
    }
}
