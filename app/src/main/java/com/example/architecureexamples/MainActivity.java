package com.example.architecureexamples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    //Create member variable for allViewModel
    private static NoteViewModel noteViewModel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Action uon clicking floating action button
        FloatingActionButton floatingActionButton = findViewById(R.id.button_add_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //it is true if you know your recylcerView size won't change
        recyclerView.setHasFixedSize(true);

        final NoteAdapter mAdapter = new NoteAdapter();
        recyclerView.setAdapter(mAdapter);

        //viewModel is distroyed when the acitivity is finished...
        /*___ViewModelProviders.of(this).get(NoteViewModel.class);___ this is the deprecated method for Accessing
        * your NoteViewModel from java in the activity or fragment*/
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_SHORT).show();
                mAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(mAdapter.getNotAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "All notes deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        mAdapter.setOnItemClickListenerR(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            //passing your notes to the note Constructor
            Note note = new Note(title, description, priority);
            //inserting your new note the your dataBase using the NoteViewModel
            noteViewModel.insert(note);
            //toast message to indicate note is saved
            Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG).show();
        }else if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(MainActivity.this, "Note can't be Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
        }else {
            //toast message to indicate note is not saved
            Toast.makeText(this, "Note Note Saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                noteViewModel.deleteAllNote();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
