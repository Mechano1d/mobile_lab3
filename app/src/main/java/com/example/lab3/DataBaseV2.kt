package com.example.lab3

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.lab3.DataBaseLayout2.Companion.DATABASE_NAME
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DB_entry2{
    var ID = "0"
    var Name = "John"
    var Surname = "Doe"
    var Middle_Name = "Jack"
    var Time = "00:00"
}

var result2 = mutableListOf<DB_entry2>()

class DataBaseV2 : ComponentActivity() {
    @OptIn(ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_v2)

        var dbHelper = DataBaseLayout2(this.baseContext)

        val db = dbHelper.writableDatabase

        dbHelper.onCreate(db)
        var names = arrayOf("michael khodos z", "victor emmanuel II", "john doe s", "jane doe hhee", "gregory williams 123")

        //var values = ContentValues()
        for (name in names)
        {
            var name_list = name.split(" ")
            add_entry(name_list[0], name_list[1], name_list[2], db)
        }

        var show_button = findViewById<Button>(R.id.show_button2)
        show_button.setOnClickListener {
            show_db(db)
            val intent = Intent(this, DataBaseView2::class.java)
            startActivity(intent)
        }

        var add_button = findViewById<Button>(R.id.add_button2)
        var input_name = findViewById<EditText>(R.id.enter_name2)
        var input_surname = findViewById<EditText>(R.id.enter_surname2)
        var input_middle_name = findViewById<EditText>(R.id.enter_middle_name2)
        add_button.setOnClickListener {
            add_entry(input_name.text.toString(), input_surname.text.toString(),
                input_middle_name.text.toString(), db)
        }

        var change_button = findViewById<Button>(R.id.change_button2)
        change_button.setOnClickListener {
            val new_name = "Петро"
            val new_surname = "Петро"
            val new_middle_name = "Петрович"
            change_db(new_name, new_surname, new_middle_name, db)
        }
    }

    @OptIn(ExperimentalTime::class)
    fun add_entry(name: String, surname: String, middle_name: String, db: SQLiteDatabase){
        // Create a new map of values, where column names are the keys
        var values = ContentValues().apply {
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_NAME, name)
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_SURNAME, surname)
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_MIDDLE_NAME, middle_name)
            val currentInstant = Clock.System.now().toString()
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_TIME, currentInstant)
        }
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(ClassmateContract2.FeedEntry.TABLE_NAME,
            null, values)
    }

    fun show_db(db: SQLiteDatabase){
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, ClassmateContract2.FeedEntry.COLUMN_NAME_NAME,
            ClassmateContract2.FeedEntry.COLUMN_NAME_SURNAME,
            ClassmateContract2.FeedEntry.COLUMN_NAME_MIDDLE_NAME,
            ClassmateContract2.FeedEntry.COLUMN_NAME_TIME)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${ClassmateContract2.FeedEntry.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf("lab3")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${ClassmateContract2.FeedEntry.COLUMN_NAME_TIME} DESC"

        val cursor = db.query(
            ClassmateContract2.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val itemIds = mutableListOf<String>()
        result2 = mutableListOf<DB_entry2>()
        with(cursor) {
            while (moveToNext()) {
                //val itemId = getString(getColumnIndexOrThrow(ClassmateContract2.FeedEntry.COLUMN_NAME_NAME))
                //itemIds.add(itemId)
                var new_entry = DB_entry2()
                new_entry.ID = getString(getColumnIndexOrThrow(BaseColumns._ID))
                new_entry.Name = getString(getColumnIndexOrThrow(ClassmateContract2.FeedEntry.COLUMN_NAME_NAME))
                new_entry.Surname = getString(getColumnIndexOrThrow(ClassmateContract2.FeedEntry.COLUMN_NAME_SURNAME))
                new_entry.Middle_Name = getString(getColumnIndexOrThrow(ClassmateContract2.FeedEntry.COLUMN_NAME_MIDDLE_NAME))
                new_entry.Time = getString(getColumnIndexOrThrow(ClassmateContract2.FeedEntry.COLUMN_NAME_TIME))
                result2.add(new_entry)
            }
        }
        cursor.close()
    }

    fun change_db(new_name: String, new_surname: String, new_middle_name: String, db: SQLiteDatabase){
        // New value for one column
        val values = ContentValues().apply {
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_NAME, new_name)
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_SURNAME, new_surname)
            put(ClassmateContract2.FeedEntry.COLUMN_NAME_MIDDLE_NAME, new_middle_name)
        }

        // Which row to update, based on the title
        val selection = "${BaseColumns._ID} = (SELECT MAX(${BaseColumns._ID}) FROM " +
                "${ClassmateContract2.FeedEntry.TABLE_NAME})"
        val selectionArgs = null
        val count = db.update(
            ClassmateContract2.FeedEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }
}
