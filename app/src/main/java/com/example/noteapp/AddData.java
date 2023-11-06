package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.ViewModel.ViewModelClass;
import com.example.noteapp.databinding.ActivityAddDataBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddData extends AppCompatActivity {
    Date currentTime;
    ActivityAddDataBinding activityAddDataBinding;
    int no = 0;
    ViewModelClass viewModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddDataBinding = DataBindingUtil.setContentView(AddData.this, R.layout.activity_add_data);
//        DBHelper dbHelper = DBHelper.getDatabase(this);
//        dbHelper
        currentTime = Calendar.getInstance().getTime();
        StatusBar();
        activityAddDataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no++;
                String t = activityAddDataBinding.editTittle.getText().toString();
                String c = activityAddDataBinding.editContent.getText().toString();
                String currTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//                dbHelper.getDAO().addText(new RoomModel(t, c, currTime));
                viewModelClass = new ViewModelClass(getApplication());
                viewModelClass.insert(new RoomModel(t, c, currTime));
                Intent intent = new Intent(AddData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        activityAddDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                Intent intent = new Intent(AddData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddData.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void StatusBar() {
        Window window = AddData.this.getWindow();
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