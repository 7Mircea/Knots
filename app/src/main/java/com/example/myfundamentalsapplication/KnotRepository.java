package com.example.myfundamentalsapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class KnotRepository {
    private KnotDao knotDao;
    private LiveData<List<Knot>> allKnots;

    public KnotRepository(Application application) {
        KnotDatabase database = KnotDatabase.getInstance(application);
        knotDao = database.knotDao();
        allKnots = knotDao.getAllKnots();
    }

    //we need these function to made this operation on a background thread
    public void insert(Knot knot) {
        new InsertKnotAsyncTask(knotDao).execute(knot);
    }

    //we need these function to made this operation on a background thread
    public void delete(Knot knot) {
        new DeleteKnotAsyncTask(knotDao).execute(knot);
    }

    //we need these function to made this operation on a background thread
    public void update(Knot knot) {
        new UpdateKnotAsyncTask(knotDao).execute(knot);
    }

    //we need these function to made this operation on a background thread
    public void deleteAllKnots() {
        new DeleteAllKnotsAsyncTask(knotDao).execute();
    }

    //the execution of methods that return LiveDate takes place automatically on other thread
    //than main thread
    public LiveData<List<Knot>> getAllKnots() {
        return allKnots;
    }

    private static class InsertKnotAsyncTask extends AsyncTask<Knot,Void,Void> {
        private KnotDao knotDao;

        private InsertKnotAsyncTask(KnotDao knotDao) {
            this.knotDao = knotDao;
        }

        @Override
        protected Void doInBackground(Knot... knots) {
            knotDao.insert(knots[0]);
            return null;
        }
    }

    private static class UpdateKnotAsyncTask extends AsyncTask<Knot,Void,Void> {
        private KnotDao knotDao;

        private UpdateKnotAsyncTask(KnotDao knotDao) {this.knotDao = knotDao;}

        @Override
        protected Void doInBackground(Knot... knots) {
            knotDao.update(knots[0]);
            return null;
        }
    }

    private static class DeleteKnotAsyncTask extends AsyncTask<Knot,Void,Void> {
        private KnotDao knotDao;

        private DeleteKnotAsyncTask(KnotDao knotDao) {this.knotDao = knotDao;}

        @Override
        protected Void doInBackground(Knot... knots) {
            knotDao.delete(knots[0]);
            return null;
        }
    }

    private static class DeleteAllKnotsAsyncTask extends AsyncTask<Void,Void,Void> {
        private KnotDao knotDao;

        private DeleteAllKnotsAsyncTask(KnotDao knotDao) {this.knotDao = knotDao;}

        @Override
        protected Void doInBackground(Void... voids) {
            knotDao.deleteAllKnots();
            return null;
        }
    }
}
