package com.example.noteapp.RoomDAta;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.InterfaceCl.DataDAO;
import com.example.noteapp.ModelClass.RoomModel;

@Database(entities = RoomModel.class, exportSchema = false, version = 1)
public abstract class DBHelper extends RoomDatabase {
    private static final String DB_NAME = "noteData";
    private static DBHelper instance;

    public static synchronized DBHelper getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DBHelper.class, DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        }
        return instance;
    }

    public abstract DataDAO getDAO();
}
