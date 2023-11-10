package com.example.noteapp.RoomDAta;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.InterfaceClass.DataDAO;
import com.example.noteapp.ModelClass.RoomModel;

@Database(entities = RoomModel.class, exportSchema = false, version = 1)
public abstract class DataBaseHelper extends RoomDatabase {
    private static final String NOTE_DATA = "noteData";
    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DataBaseHelper.class, NOTE_DATA).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        }
        return instance;
    }

    public abstract DataDAO getDaoData();
}
