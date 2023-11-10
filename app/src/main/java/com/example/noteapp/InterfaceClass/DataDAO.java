package com.example.noteapp.InterfaceClass;

//import android.arch.persistence.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapp.ModelClass.RoomModel;

import java.util.List;

@Dao
public interface DataDAO {
    @Query("select * from data_Table")
   LiveData<List<RoomModel>> getallData();

    @Query("select * from data_Table where tittle LIKE :search")
    List<RoomModel> searchTittle(String search);

    @Insert
    void addText(RoomModel roomModel);

    @Update
    void updateText(RoomModel roomModel);

    @Delete
    void deleteText(RoomModel roomModel);
}
