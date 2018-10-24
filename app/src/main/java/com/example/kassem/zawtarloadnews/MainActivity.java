package com.example.kassem.zawtarloadnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
String Id;
EditText inputId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputId =(EditText)findViewById(R.id.inputId);
        Id=inputId.getText().toString();

    }
}
