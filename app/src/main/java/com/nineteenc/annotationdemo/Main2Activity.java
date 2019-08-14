package com.nineteenc.annotationdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.nineteenc.module_annotation.Route;

@Route(path = "/main/Main2Activity")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
