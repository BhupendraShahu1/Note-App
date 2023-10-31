package com.example.noteapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AndroidViModel extends AndroidViewModel {
    Model model;

    public AndroidViModel(@NonNull Application application) {
        super(application);
        model = new Model("jai mata di ", "jai shree ram");

    }

    public Model getModel() {
        return this.model;
    }
}
