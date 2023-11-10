package com.example.noteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.noteapp.AdopterClass.AdapterClass2;
import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.RoomDAta.DataBaseHelper;
import com.example.noteapp.ViewModel.ViewModelClass;
import com.example.noteapp.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding main2Binding;
    AdapterClass2 adopter;
    int position;
    DataBaseHelper dataBaseHelper;
    private static final String NIGHT_MODE_PREF = "night_mode_preference";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ViewModelClass viewModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        main2Binding = DataBindingUtil.setContentView(MainActivity2.this, R.layout.activity_main2);
        main2Binding.recycle.setLayoutManager(new LinearLayoutManager(this));

        //MVVM implement here

        dataBaseHelper = DataBaseHelper.getDatabase(getApplication());
        viewModelClass = new ViewModelProvider(this).get(ViewModelClass.class);
        viewModelClass.getAllDataInsert().observe(this, new Observer<List<RoomModel>>() {
            @Override
            public void onChanged(List<RoomModel> roomModelList) {
                if (roomModelList.size() != 0) {
                    main2Binding.recycle.setVisibility(View.VISIBLE);
                    main2Binding.noDataFoundHere.setVisibility(View.GONE);
                    adopter = new AdapterClass2((ArrayList<RoomModel>) roomModelList, MainActivity2.this, dataBaseHelper);
                    main2Binding.recycle.setAdapter(adopter);
//                main2Binding.recycle.scrollToPosition(roomModelList.size() - 1);
                    adopter.notifyDataSetChanged();
                } else {
                    main2Binding.recycle.setVisibility(View.GONE);
                    main2Binding.noDataFoundHere.setVisibility(View.VISIBLE);
                }

            }
        });
        //MVVM implement end here


        // calling the method
        addSetting();
        sharedPreferences();
        changeMode();
        searchData();
        StatusBar();

        main2Binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, AddSecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addSetting() {
        main2Binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });

    }

    private void changeLanguage() {
        final String language[] = {"English", "Hindi", "SecondUI"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("");
                    recreate();
                } else if (which == 1) {
                    setLocale("hi");
                    recreate();
                } else {
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putString("hindi", language);
        editor.apply();
    }

    private void sharedPreferences() {
        SharedPreferences editor = getSharedPreferences("setting", MODE_PRIVATE);
        setLocale(editor.getString("hindi", ""));
    }

    private void changeMode() {
        sharedPreferences = getSharedPreferences("mode", MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean("night", true);
        if (b) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            activityMainBinding.switcher.setBackgroundColor(R.color);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        main2Binding.switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });
    }

    public void searchData() {
        main2Binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, SearchActivityAn.class);
                startActivity(intent);
            }
        });
    }


    public void StatusBar() {
        Window window = MainActivity2.this.getWindow();
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