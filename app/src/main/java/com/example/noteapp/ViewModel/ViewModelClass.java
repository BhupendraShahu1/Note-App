package com.example.noteapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.Repository.RepositoryClass;

import java.util.List;

public class ViewModelClass extends AndroidViewModel {
    private RepositoryClass repositoryClass;
    private LiveData<List<RoomModel>> allInsert;

    public ViewModelClass(@NonNull Application application) {
        super(application);
        repositoryClass = new RepositoryClass(application);
        allInsert = repositoryClass.getGetAllData();
    }

    public void insert(RoomModel roomModel) {
        repositoryClass.insert(roomModel);
    }

    public void delete(RoomModel roomModel) {
        repositoryClass.delete(roomModel);
    }

    public void updateData(RoomModel roomModel) {
        repositoryClass.updateData(roomModel);
    }

    public LiveData<List<RoomModel>> getAllDataInsert() {
        return allInsert;
    }
}
