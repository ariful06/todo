package com.wikifoundry.todo.common.base;

import com.wikifoundry.todo.database.entity.Note;

public class AddNewNoteEvent {


    private Note note;

    public AddNewNoteEvent(Note note){
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
