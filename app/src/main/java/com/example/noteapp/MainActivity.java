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
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteapp.AdopterClass.AdopterCl;
import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.RoomDAta.DBHelper;
import com.example.noteapp.ViewModel.ViewModelClass;
import com.example.noteapp.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdopterCl.SendData {
    AdopterCl adopter;
    int position;
    DBHelper dbHelper;
    private static final String NIGHT_MODE_PREF = "night_mode_preference";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityMainBinding activityMainBinding;
    ViewModelClass viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        activityMainBinding.recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //MVVM implement here

        dbHelper = DBHelper.getDatabase(getApplication());
        viewmodal = new ViewModelProvider(this).get(ViewModelClass.class);
        viewmodal.getAllDataInsert().observe(this, new Observer<List<RoomModel>>() {
            @Override
            public void onChanged(List<RoomModel> roomModelList) {
                adopter = new AdopterCl((ArrayList<RoomModel>) roomModelList, MainActivity.this, dbHelper);
                activityMainBinding.recycle.setAdapter(adopter);
//                activityMainBinding.recycle.scrollToPosition(arrayList.size() - 1);
                adopter.notifyDataSetChanged();
            }
        });
        //MVVM implement end here

        SetDate();
        addSetting();
        sharedPref();
//        changeThemesMode();
        changeMode();
        searchData();
        StatusBar();

        activityMainBinding.floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddData.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void SetDate() {

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat yearFormate = new SimpleDateFormat("yyyy");
        DateFormat dayFomate = new SimpleDateFormat("MMM");
        DateFormat day = new SimpleDateFormat("dd");
        String todayAsString = yearFormate.format(today);
        String yearS = dayFomate.format(today);
        activityMainBinding.todayDay.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.bhu.setText(day.format(today));
        activityMainBinding.day.setText(yearS);
        activityMainBinding.year.setText(todayAsString);
        activityMainBinding.bhu.setText(day.format(today));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        activityMainBinding.day2.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.date2.setText(new SimpleDateFormat("dd").format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        activityMainBinding.day3.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.date3.setText(new SimpleDateFormat("dd").format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        activityMainBinding.day4.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.date4.setText(new SimpleDateFormat("dd").format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        activityMainBinding.day5.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.date5.setText(new SimpleDateFormat("dd").format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        activityMainBinding.day6.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.date6.setText(new SimpleDateFormat("dd").format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        activityMainBinding.day7.setText(new SimpleDateFormat("E").format(calendar.getTime()));
        activityMainBinding.date7.setText(new SimpleDateFormat("dd").format(calendar.getTime()));
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//            activityMainBinding.day3.setText(new SimpleDateFormat("E").format(calendar.getTime()));
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//            activityMainBinding.day4.setText(new SimpleDateFormat("E").format(calendar.getTime()));
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//            activityMainBinding.day6.setText(new SimpleDateFormat("E").format(calendar.getTime()));
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//            activityMainBinding.day7.setText(new SimpleDateFormat("E").format(calendar.getTime()));

    }
//


    private void addSetting() {
        activityMainBinding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });

    }

    private void changeLanguage() {
        final String language[] = {"English", "Hindi"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("");
                    recreate();
                } else {
                    setLocale("hi");
                    recreate();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void setLocale(String bhu) {
        Locale locale = new Locale(bhu);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putString("hindi", bhu);
        editor.apply();
    }

    private void sharedPref() {
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
        activityMainBinding.switcher.setOnClickListener(new View.OnClickListener() {
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
        activityMainBinding.btnSearch.clearFocus();
        activityMainBinding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<RoomModel> models = (ArrayList<RoomModel>) dbHelper.getDAO().searchTittle(query);
                activityMainBinding.recycle.setAdapter(new AdopterCl(models, MainActivity.this, dbHelper));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void send(int p) {
        position = p;
    }


    public void StatusBar() {
        Window window = MainActivity.this.getWindow();
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