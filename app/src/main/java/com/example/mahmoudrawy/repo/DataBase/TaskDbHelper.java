/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.mahmoudrawy.repo.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */

/*
this class for repo database to cash data
 */
public class TaskDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "RepositoriesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY, " +
                TaskContract.TaskEntry.NAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.Description + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.FORK + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.REPOHTML + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.OWNERUSERNAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.OWNERHTML + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    /**
     * This method discards the old table of com.example.ph_data01221240053.todolist.data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
