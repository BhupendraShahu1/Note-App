package com.example.noteapp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.AdopterClass.AdapterClass2;
import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.RoomDAta.DataBaseHelper;

import java.util.ArrayList;

public class SearchActivityAn extends AppCompatActivity {
    SearchView searchView;
    DataBaseHelper dataBaseHelper;
    RecyclerView recyclerView;
    AdapterClass2 adapterClass;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_an);
        searchView = findViewById(R.id.button_searchView);
        recyclerView = findViewById(R.id.show_search_recycle);
        imageView = findViewById(R.id.back_image_button);
        statusBar();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dataBaseHelper = DataBaseHelper.getDatabase(getApplication());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String queryText = newText.trim();
                ArrayList<RoomModel> models = (ArrayList<RoomModel>) dataBaseHelper.getDaoData().searchTittle(queryText);
                adapterClass = new AdapterClass2(models, SearchActivityAn.this, dataBaseHelper);
                recyclerView.setAdapter(adapterClass);
                adapterClass.notifyDataSetChanged();
                return true;
            }
        });
    }
    public void statusBar() {
        Window window = SearchActivityAn.this.getWindow();
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