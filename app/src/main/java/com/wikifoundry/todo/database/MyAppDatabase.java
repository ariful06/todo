package com.wikifoundry.todo.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.wikifoundry.todo.database.dao.NoteDao;
import com.wikifoundry.todo.database.entity.Note;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "com.example.virus.timetable.note_db";

    public abstract NoteDao noteDao();
}
