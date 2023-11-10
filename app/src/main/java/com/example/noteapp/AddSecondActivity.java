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
import com.example.noteapp.databinding.ActivityAddSecondBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSecondActivity extends AppCompatActivity {
    Date currentTime;
    ActivityAddSecondBinding activityAddDataBinding;
    ViewModelClass viewModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddDataBinding = DataBindingUtil.setContentView(AddSecondActivity.this, R.layout.activity_add_second);
        currentTime = Calendar.getInstance().getTime();
        StatusBar();
        activityAddDataBinding.saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle = activityAddDataBinding.editTittle.getText().toString();
                String content = activityAddDataBinding.editContent.getText().toString();
                if (tittle.isEmpty()) {
                    Toast.makeText(AddSecondActivity.this, "Tittle is empty", Toast.LENGTH_SHORT).show();
                } else if (content.isEmpty()) {
                    Toast.makeText(AddSecondActivity.this, "Content is empty", Toast.LENGTH_SHORT).show();
                } else {
                    String currTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    viewModelClass = new ViewModelClass(getApplication());
                    viewModelClass.insert(new RoomModel(tittle, content, currTime));
                    Intent intent = new Intent(AddSecondActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        activityAddDataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle = activityAddDataBinding.editTittle.getText().toString();
                String tittleTrim = tittle.trim();
                String content = activityAddDataBinding.editContent.getText().toString();
                if (tittle.isEmpty()) {
                    Toast.makeText(AddSecondActivity.this, "Tittle is empty", Toast.LENGTH_SHORT).show();
                } else if (content.isEmpty()) {
                    Toast.makeText(AddSecondActivity.this, "Content is empty", Toast.LENGTH_SHORT).show();
                } else {
                    String currTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    viewModelClass = new ViewModelClass(getApplication());
                    viewModelClass.insert(new RoomModel(tittleTrim, content, currTime));
                    Intent intent = new Intent(AddSecondActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        activityAddDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSecondActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddSecondActivity.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

    public void StatusBar() {
        Window window = AddSecondActivity.this.getWindow();
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