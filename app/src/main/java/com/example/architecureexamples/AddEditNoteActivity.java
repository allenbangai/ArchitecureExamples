package com.example.architecureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import static android.nfc.NfcAdapter.EXTRA_ID;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.architecureexamples.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.architecureexamples.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.architecureexamples.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.example.architecureexamples.EXTRA_ID";

    private EditText editTextTitle, editTextDescription;
    private NumberPicker numberPickerPriority;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editText_title);
        editTextDescription = findViewById(R.id.editText_description);
        numberPickerPriority = findViewById(R.id.numberPicker_priority);

        //set max and min value for NumberPicker
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //set title of actionBar
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "One of your input are empty, pls fill them", Toast.LENGTH_LONG).show();
            return;
        }

        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_TITLE, title);
        dataIntent.putExtra(EXTRA_DESCRIPTION, description);
        dataIntent.putExtra(EXTRA_PRIORITY, priority);

        int note_id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(note_id != 1){
            dataIntent.putExtra(EXTRA_ID, note_id);
        }

        setResult(RESULT_OK, dataIntent);
        finish();
    }
}
