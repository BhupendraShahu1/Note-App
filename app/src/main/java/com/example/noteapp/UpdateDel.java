package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.ViewModel.ViewModelClass;
import com.example.noteapp.databinding.ActivityUpdateDelBinding;

public class UpdateDel extends AppCompatActivity {
    ActivityUpdateDelBinding activityUpdateDelBinding;
    ViewModelClass viewModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateDelBinding = DataBindingUtil.setContentView(UpdateDel.this, R.layout.activity_update_del);
        viewModelClass = new ViewModelClass(getApplication());
        StatusBar();
        Intent intent = getIntent();
        String tittle = intent.getStringExtra("n");
        String about = intent.getStringExtra("c");
        String time = intent.getStringExtra("t");
        int id = intent.getIntExtra("id", 0);
        activityUpdateDelBinding.updateTittle.setText(tittle);
        activityUpdateDelBinding.updateContent.setText(about);
        activityUpdateDelBinding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTittle = activityUpdateDelBinding.updateTittle.getText().toString();
                String tittleTrim = getTittle.trim();
                String getContent = activityUpdateDelBinding.updateContent.getText().toString();
                if (getTittle.isEmpty()) {
                    Toast.makeText(UpdateDel.this, "Tittle is empty", Toast.LENGTH_SHORT).show();
                } else if (getContent.isEmpty()) {
                    Toast.makeText(UpdateDel.this, "Content is empty", Toast.LENGTH_SHORT).show();
                } else {
                    RoomModel roomModel = new RoomModel(id, tittleTrim, getContent, time);
                    viewModelClass.updateData(roomModel);
                    Intent intent1 = new Intent(UpdateDel.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
        activityUpdateDelBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
    }

    public void StatusBar() {
        Window window = UpdateDel.this.getWindow();
        View decorView = window.getDecorView();
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_NO) {
            // Night mode is active
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else {
            // Day mode is active
            decorView.setSystemUiVisibility(0); // Clear any previous flags
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        int statusBarColor = ContextCompat.getColor(this, AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? R.color.black : R.color.white);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(statusBarColor);
        }

    }
}