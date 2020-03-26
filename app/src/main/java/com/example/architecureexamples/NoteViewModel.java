package com.example.architecureexamples;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allData = repository.getAllNotes();
    }

    //Our Activity only has reference to the viewModel, not to the repository... (Hence we..)
    //We create Reaper methods for our dataBase operation methods from our repository...
    public void insert(Note note){
        repository.insertNote(note);
    }
    public void delete(Note note){
        repository.deleteNote(note);
    }
    public void update(Note note){
        repository.updateNote(note);
    }
    public void deleteAllNote(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllData(){
        return allData;
    }
}
