package com.nineteenc.annotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nineteenc.module_annotation.NCRoute;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            NCRoute.getInstance().build("/main/Main2Activity").navigation(this, 1);
        }
    }
}
