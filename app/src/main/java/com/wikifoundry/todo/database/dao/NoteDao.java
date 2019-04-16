package com.wikifoundry.todo.database.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.wikifoundry.todo.database.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void addNote(Note notes);

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Delete
    public void deleteNote(Note note);

    @Update
    void update(Note note);
}
