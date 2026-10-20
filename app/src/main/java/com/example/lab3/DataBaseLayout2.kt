package com.example.lab3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object ClassmateContract2 {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "Classmates"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_SURNAME = "surname"
        const val COLUMN_NAME_MIDDLE_NAME = "middle_name"
        const val COLUMN_NAME_TIME = "time"
    }
}



private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"

class DataBaseLayout2(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL("""
            DROP TABLE IF EXISTS Classmates
        """.trimIndent())
        db.execSQL("""
        CREATE TABLE Classmates (
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            surname TEXT,
            name TEXT,
            middle_name TEXT,
            time TEXT
        )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "FeedReader.db"
    }
}
