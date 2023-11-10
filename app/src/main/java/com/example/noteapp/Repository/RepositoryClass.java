package com.example.noteapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.RoomDAta.DataBaseHelper;

import java.util.List;

public class RepositoryClass {
    private DataBaseHelper dataBaseHelper;
    LiveData<List<RoomModel>> getAllData;

    public RepositoryClass(Application application) {
        dataBaseHelper = DataBaseHelper.getDatabase(application);
        getAllData = dataBaseHelper.getDaoData().getallData();
    }

    public void insert(RoomModel roomModel) {
        dataBaseHelper.getDaoData().addText(roomModel);
    }

    public void delete(RoomModel roomModel) {
        dataBaseHelper.getDaoData().deleteText(roomModel);
    }

    public void updateData(RoomModel roomModel) {
        dataBaseHelper.getDaoData().updateText(roomModel);
    }

    public LiveData<List<RoomModel>> getGetAllData() {
        return getAllData;
    }

//    static class InsertAsynTask extends AsyncTask<List<RoomModel>, Void, Void> {
//        private DataDAO dataDAO;
//
//        public InsertAsynTask(DataBaseHelper dbHelper) {
//            dataDAO = dbHelper.getDAO();
//        }
//
//        @Override
//        protected Void doInBackground(List<RoomModel>... lists) {
//            dataDAO.getInsertData(lists[0]);
//            return null;
//        }
//    }
}
