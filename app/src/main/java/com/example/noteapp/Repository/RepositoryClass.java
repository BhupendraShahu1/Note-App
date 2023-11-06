package com.example.noteapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.RoomDAta.DBHelper;

import java.util.List;

public class RepositoryClass {
    private DBHelper dbHelper;
    LiveData<List<RoomModel>> getAllData;

    public RepositoryClass(Application application) {
        dbHelper = DBHelper.getDatabase(application);
        getAllData = dbHelper.getDAO().getallData();
    }

    public void insert(RoomModel roomModel) {
        dbHelper.getDAO().addText(roomModel);
    }

    public void delete(RoomModel roomModel) {
        dbHelper.getDAO().deleteText(roomModel);
    }

    public void updateData(RoomModel roomModel) {
        dbHelper.getDAO().updateText(roomModel);
    }

    public LiveData<List<RoomModel>> getGetAllData() {
        return getAllData;
    }

//    static class InsertAsynTask extends AsyncTask<List<RoomModel>, Void, Void> {
//        private DataDAO dataDAO;
//
//        public InsertAsynTask(DBHelper dbHelper) {
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
