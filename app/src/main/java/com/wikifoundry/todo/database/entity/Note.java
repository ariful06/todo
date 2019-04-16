package com.wikifoundry.todo.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

@Entity(tableName = "note")
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Nullable
    @ColumnInfo(name = "title")
    private String noteTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String noteDescription;

    @Nullable
    @ColumnInfo(name = "date_time")
    private String currentDateAndTime;

    public Note(){}

    public Note(int id, String noteTitle, String noteDescription, String currentDateAndTime) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.currentDateAndTime = currentDateAndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(@Nullable String noteTitle) {
        this.noteTitle = noteTitle;
    }

    @Nullable
    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(@Nullable String noteDescription) {
        this.noteDescription = noteDescription;
    }

    @Nullable
    public String getCurrentDateAndTime() {
        return currentDateAndTime;
    }

    public void setCurrentDateAndTime( String currentDateAndTime) {
        this.currentDateAndTime = currentDateAndTime;
    }

    public static Creator<Note> getCREATOR() {
        return CREATOR;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        noteTitle = in.readString();
        noteDescription = in.readString();
        currentDateAndTime = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(noteTitle);
        dest.writeString(noteDescription);
        dest.writeString(currentDateAndTime);
    }
}
