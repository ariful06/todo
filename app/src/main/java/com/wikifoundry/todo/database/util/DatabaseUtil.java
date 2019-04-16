package com.wikifoundry.todo.database.util;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.wikifoundry.todo.database.MyAppDatabase;


public class DatabaseUtil {

    public static MyAppDatabase getAppDb(Context context){
        return Room.databaseBuilder(context,MyAppDatabase.class,MyAppDatabase.DATABASE_NAME).allowMainThreadQueries().build();
    }
}
