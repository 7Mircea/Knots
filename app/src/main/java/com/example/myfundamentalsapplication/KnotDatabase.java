package com.example.myfundamentalsapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.security.keystore.KeyNotYetValidException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Knot.class},version = 1)
public abstract class KnotDatabase extends RoomDatabase {
    private static KnotDatabase instance;

    public abstract KnotDao knotDao();

    //syncronized means that only one thread can access this database at the same time
    public static synchronized KnotDatabase getInstance(Context context) {
        if(instance == null) {
            //we create the database, give it a name and if we update the database
            //the old database is deleted(fallBackToDestructiveMigration
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    KnotDatabase.class,"knot_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private KnotDao knotDao;

        private PopulateDbAsyncTask(KnotDatabase db) {
            this.knotDao = db.knotDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            knotDao.insert(new Knot("Coada vacii",R.string.coada_vacii_description,R.drawable.coada_vacii));
            knotDao.insert(new Knot("Doiul cu bolta",R.string.doiul_cu_bolta_description,R.drawable.doiul_cu_bolta));
            knotDao.insert(new Knot("Doiul simplu",R.string.doiul_simplu_description,R.drawable.doiul_simplu));
            knotDao.insert(new Knot("Jumatate de ochi",R.string.jumatate_de_ochi_description,R.drawable.jumatate_de_ochi));
            knotDao.insert(new Knot("Nodul chirurgului",R.string.nodul_chirurgului_description,R.drawable.nodul_chirurgului));
            knotDao.insert(new Knot("Optul de strangere",R.string.optul_de_strangere_description,R.drawable.optul_de_strangere));
            return null;
        }
    }

}
