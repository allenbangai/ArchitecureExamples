package com.example.architecureexamples;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAllNotes();
    }
    void insertNote(Note note){
        //executing instance of InsertAsychTask
        new InsertNoteAsychTask(noteDao).execute(note);
    }
    void updateNote(Note note){
        new UpdateNoteAsychTask(noteDao).execute(note);
    }
    void deleteNote(Note note){
        new DeleteNoteAsynchTask(noteDao).execute(note);
    }
    void deleteAllNotes(){
        new DeleteAllNoteAsynchTask(noteDao).execute();
    }
    LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteAsychTask extends AsyncTask<Note, Void, Void>{
        //create member variable for NoteDao and we need the noteDao to make database Operation
        private NoteDao noteDao;

        //Because class is static, we can't access the @NoteDao of our Repository directly, so we
        //pass it over a constructor
        private  InsertNoteAsychTask(NoteDao noteDao1){
            this.noteDao = noteDao1;
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
         * @param notes The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsychTask extends AsyncTask<Note, Void, Void>{
        //create member variable for NoteDao and we need the noteDao to make database Operation
        private NoteDao noteDao;

        //Because class is static, we can't access the @NoteDao of our Repository directly, so we
        //pass it over a constructor
        private  UpdateNoteAsychTask(NoteDao noteDao1){
            this.noteDao = noteDao1;
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
         * @param notes The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsynchTask extends AsyncTask<Note, Void, Void>{
        //create member variable for NoteDao and we need the noteDao to make database Operation
        private NoteDao noteDao;

        //Because class is static, we can't access the @NoteDao of our Repository directly, so we
        //pass it over a constructor
        private  DeleteNoteAsynchTask(NoteDao noteDao1){
            this.noteDao = noteDao1;
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
         * @param notes The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNoteAsynchTask extends AsyncTask<Void, Void, Void>{
        //create member variable for NoteDao and we need the noteDao to make database Operation
        private NoteDao noteDao;

        //Because class is static, we can't access the @NoteDao of our Repository directly, so we
        //pass it over a constructor
        private  DeleteAllNoteAsynchTask(NoteDao noteDao1){
            this.noteDao = noteDao1;
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
         * @param notes The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
