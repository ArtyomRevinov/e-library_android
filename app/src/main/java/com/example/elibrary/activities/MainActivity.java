package com.example.elibrary.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.elibrary.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.elibrary.MESSAGE";
    public EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.search_field);
        editText.setOnClickListener(this);
    }

    public void sendMessage(View view){
        Intent intent = new Intent
                (this, SearchResultsDisplayActivity.class);

        EditText editText = findViewById(R.id.search_field);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (editText.getText().toString()
                .equals(getApplicationContext()
                        .getString(R.string.search_field))){

            editText.getText().clear();
        }
    }
}
