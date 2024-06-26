package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextInterNote;
    private RadioButton radioButtonPriorityLow;
    private RadioButton radioButtonPriorityMedium;
    private Button buttonSave;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initViews() {
        editTextInterNote = findViewById(R.id.editTextInterNote);
        radioButtonPriorityLow = findViewById(R.id.radioButtonPriorityLow);
        radioButtonPriorityMedium = findViewById(R.id.radioButtonPriorityMedium);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote() {
        String text = editTextInterNote.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(
                    AddNoteActivity.this,
                    R.string.error_inter_note,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            int priority = getPriority();
            Note note = new Note( text, priority);
            viewModel.saveNote(note);
        }
    }

    private int getPriority() {
        int priority;
        if (radioButtonPriorityLow.isChecked()) {
            priority = 0;
        } else if (radioButtonPriorityMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}