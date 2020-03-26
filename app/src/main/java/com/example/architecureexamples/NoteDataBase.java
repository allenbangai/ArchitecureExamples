package com.example.architecureexamples;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    //normally, we can't cast abstract methods
    public abstract NoteDao noteDao();

    //create a singleton that returns a null database instance
    //synchronised meaning only one tread at a time can access this method
    public static synchronized NoteDataBase getInstance(Context context){
        //only instantiate database if database does not have an instance
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class, "note_database")
                    .addCallback(roomCallBack)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    //populate database right after it has being created... (so rather than starting with an empty table, we start with one with notes in it)
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }

        /**
         * Called when the database has been opened.
         *
         * @param db The database.
         */
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        /**
         * Called after the database was destructively migrated
         *
         * @param db The database.
         */
        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{
        protected NoteDao noteDao;

        private PopulateDatabaseAsyncTask(NoteDataBase dataBase){
            noteDao = dataBase.noteDao();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insertNote(new Note("Title 01", "Description", 1));
            noteDao.insertNote(new Note("Title 02", "Description ds", 2));
            noteDao.insertNote(new Note("Title 03", "Description as", 3));
            return null;
        }
    }
}
