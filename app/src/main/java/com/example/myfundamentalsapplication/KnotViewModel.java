package com.example.myfundamentalsapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class KnotViewModel extends AndroidViewModel {
    private KnotRepository repository;
    private LiveData<List<Knot>> allKnots;
    public KnotViewModel(@NonNull Application application) {
        super(application);
        repository = new KnotRepository(application);
        allKnots = repository.getAllKnots();
    }

    public void insert(Knot knot) {
        repository.insert(knot);
    }

    public void update(Knot knot) {
        repository.update(knot);
    }

    public void delete(Knot knot) {
        repository.delete(knot);
    }

    public void deleteAllKnots() {
        repository.deleteAllKnots();
    }

    public LiveData<List<Knot>> getAllKnots(){
        return allKnots;
    }
}
