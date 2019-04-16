package com.wikifoundry.todo.dialogfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.wikifoundry.todo.R;
import com.wikifoundry.todo.adapter.NoteAdapter;
import com.wikifoundry.todo.common.base.AddNewNoteEvent;
import com.wikifoundry.todo.database.MyAppDatabase;
import com.wikifoundry.todo.database.entity.Note;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddNoteDialogFragment extends DialogFragment implements View.OnClickListener {

    Button btnAdd,btnCancel;

    TextView cuttentTime;
    EditText noteTitle;
    EditText description;
    public static MyAppDatabase myAppDatabase;

    private Note notes;
    List<Note> notesList;
    NoteAdapter adapter;
    RecyclerView recyclerView;

    String strDate="";

    public AddNoteDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        myAppDatabase = Room.databaseBuilder(getContext(), MyAppDatabase.class,"userDb").build();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_note_dialog,container,false);

        notesList = new ArrayList<Note>();
        adapter = new NoteAdapter(getContext(),notesList);

        cuttentTime = view.findViewById(R.id.dateTime);
        noteTitle = view.findViewById(R.id.note_title);
        description = view.findViewById(R.id.description);
        btnAdd = view.findViewById(R.id.btn_add);
        btnCancel= view.findViewById(R.id.btn_cancel);

        recyclerView = getActivity().findViewById(R.id.recyclerView);

        Calendar c = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("dd MMM,yyyy  hh:mm a", Locale.ENGLISH);
        strDate = sdf.format(c.getTime());
        cuttentTime.setText(strDate);



        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        getDialog().setTitle("Add New Note");
        return  view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_add:
                String nTitle = noteTitle.getText().toString().trim();
                String noteDescription = description.getText().toString().trim();
                if(noteDescription.length()>0){
                    String date = cuttentTime.getText().toString();
                    notes = new Note();
                    notes.setId(0);
                    notes.setNoteTitle(nTitle);
                    notes.setNoteDescription(noteDescription);
                    notes.setCurrentDateAndTime(cuttentTime.getText().toString());

//                new saveDataInBackground().execute(notes);
//                notesList.add(notes);

                    EventBus.getDefault().post(new AddNewNoteEvent(notes));
                    getDialog().dismiss();
                }


              break;
            case R.id.btn_cancel:
               getDialog().dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @SuppressLint("StaticFieldLeak")
    public class saveDataInBackground extends AsyncTask<Note,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Note... notes) {
//            myAppDatabase.myDao().addNewNote(notes[0]);
//            notesList = myAppDatabase.myDao().getAllNotes();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
