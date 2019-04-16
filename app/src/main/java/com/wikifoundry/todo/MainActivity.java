package com.wikifoundry.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.wikifoundry.todo.adapter.NoteAdapter;
import com.wikifoundry.todo.common.base.AddNewNoteEvent;
import com.wikifoundry.todo.database.MyAppDatabase;
import com.wikifoundry.todo.database.entity.Note;
import com.wikifoundry.todo.database.util.DatabaseUtil;
import com.wikifoundry.todo.dialogfragment.AddNoteDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Note> noteList;
    NoteAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    BottomNavigationView navigationView;
    private static final int REQUEST_CODE = 101;
    Toolbar toolbar;

    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myAppDatabase = DatabaseUtil.getAppDb(MainActivity.this);
        fab = ((FloatingActionButton)findViewById(R.id.fab_note));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoteDialogFragment noteDialogFragment = new AddNoteDialogFragment();
                noteDialogFragment.show(getSupportFragmentManager(),"Add New Note");
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        noteList = new ArrayList<Note>();
        getNoteList();
        initializeRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
          if (EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(AddNewNoteEvent event) {
        myAppDatabase.noteDao().addNote(event.getNote());
        noteList = getNoteList();
        adapter.notify(noteList);
        Toast.makeText(MainActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
    }


    private void initializeRecyclerView(){
        adapter= new NoteAdapter(this,noteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onOptionClickListener(final View v, final int position) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
                popupMenu.getMenuInflater()
                        .inflate(R.menu.option_menu_note, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Are You sure you want to delete the item?");
                                builder.setCancelable(true);

                                builder.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                deleteNote(position);
                                                noteList = myAppDatabase.noteDao().getAllNotes();
                                                adapter.notify(noteList);
                                                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }
                                        });
                                builder.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                            case R.id.share:
                                shareOption(v,position);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(MainActivity.this,NoteDetailsActivity.class);
                i.putExtra("details", noteList.get(position));
                startActivity(i);
            }
        });
    }

    private void shareOption(View view,int position) {
        String title = noteList.get(position).getNoteTitle();
        String description = noteList.get(position).getNoteDescription();
        Intent i=new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_SUBJECT,title);
        i.putExtra(android.content.Intent.EXTRA_TEXT, description);
        startActivity(Intent.createChooser(i,"Share via"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE && data !=null) {
                List<Note> nList = data.getParcelableExtra("updatedNote");
                noteList = nList;
                adapter.notify(noteList);
            }
        }
    }
    public List<Note> getNoteList() {
        return  noteList = myAppDatabase.noteDao().getAllNotes();
    }

    @Override
    public void onResume() {
        super.onResume();
        noteList = getNoteList();
        adapter.notify(noteList);
    }

    private void deleteNote(int position ){
        myAppDatabase.noteDao().deleteNote(noteList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
