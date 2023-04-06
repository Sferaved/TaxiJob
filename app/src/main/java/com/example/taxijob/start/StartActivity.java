package com.example.taxijob.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.taxijob.R;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
    }

    public void onClick (View view) {
        Intent intent = new Intent(this, com.example.taxijob.application.MainActivity.class);
        startActivity(intent);
    }

}
