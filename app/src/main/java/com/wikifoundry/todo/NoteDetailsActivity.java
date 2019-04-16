package com.wikifoundry.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wikifoundry.todo.database.MyAppDatabase;
import com.wikifoundry.todo.database.entity.Note;
import com.wikifoundry.todo.database.util.DatabaseUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDetailsActivity extends AppCompatActivity {

    Note note;
    @BindView(R.id.edit_title)  EditText editTitle;
    @BindView(R.id.edit_description) EditText editDescription;
    @BindView(R.id.currentTime) TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int position = 0;
    private MyAppDatabase myAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_note));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        myAppDatabase = DatabaseUtil.getAppDb(NoteDetailsActivity.this);

        Intent i = getIntent();
        Note note = i.getParcelableExtra("details");

        position = note.getId();
        editTitle.setText(note.getNoteTitle());
        editDescription.setText(note.getNoteDescription());
        time.setText(note.getCurrentDateAndTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                myAppDatabase.noteDao().update(updateNote());
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Note updateNote() {
        String title = editTitle.getText().toString().trim();
        String descriptoin = editDescription.getText().toString().trim();
        String currentTime = time.getText().toString().trim();

        note = new Note();
        note.setCurrentDateAndTime(currentTime);
        note.setNoteTitle(title);
        note.setNoteDescription(descriptoin);
        note.setId(position);
        return note;
    }

    @Override
    public void onBackPressed() {
        myAppDatabase.noteDao().update(updateNote());
        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
        finish();
        return;
    }
}
