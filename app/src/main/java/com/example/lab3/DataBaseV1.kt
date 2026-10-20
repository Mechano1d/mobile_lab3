package com.example.lab3

import android.R.attr.subtitle
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Snackbar
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DB_entry{
    var ID = "0"
    var Name = "John"
    var Time = "00:00"
}

var result = mutableListOf<DB_entry>()

class DataBaseV1 : ComponentActivity() {
    @OptIn(ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_v1)

        var dbHelper = DataBaseLayout(this.baseContext)

        val db = dbHelper.writableDatabase

        //dbHelper.onCreate(db)
        var names = arrayOf("michael khodos", "victor emmanuel II", "john doe", "jane doe", "gregory williams")

        //var values = ContentValues()
        for (name in names)
        {
            add_entry(name, db)
        }

        var show_button = findViewById<Button>(R.id.show_button1)
        show_button.setOnClickListener {
            show_db(db)
            val intent = Intent(this, DataBaseView1::class.java)
            startActivity(intent)
        }

        var add_button = findViewById<Button>(R.id.add_button1)
        var input_name = findViewById<EditText>(R.id.enter_name1)
        add_button.setOnClickListener {
            add_entry(input_name.text.toString(), db)
        }

        var change_button = findViewById<Button>(R.id.change_button1)
        change_button.setOnClickListener {
            val new_name = "Петренко Петро Петрович"
            change_db(new_name, db)
        }
    }

    @OptIn(ExperimentalTime::class)
    fun add_entry(name: String, db: SQLiteDatabase){
        // Create a new map of values, where column names are the keys
        var values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, name)
            val currentInstant = Clock.System.now().toString()
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, currentInstant)
        }
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME,
            null, values)
    }

    fun show_db(db: SQLiteDatabase){
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, FeedReaderContract.FeedEntry.COLUMN_NAME_TIME)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf("lab3")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TIME} DESC"

        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val itemIds = mutableListOf<String>()
        result = mutableListOf<DB_entry>()
        with(cursor) {
            while (moveToNext()) {
                //val itemId = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME))
                //itemIds.add(itemId)
                var new_entry = DB_entry()
                new_entry.ID = getString(getColumnIndexOrThrow(BaseColumns._ID))
                new_entry.Name = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME))
                new_entry.Time = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME))
                result.add(new_entry)
            }
        }
        cursor.close()
    }

    fun change_db(new_name: String, db: SQLiteDatabase){
        // New value for one column
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, new_name)
        }

        // Which row to update, based on the title
        val selection = "${BaseColumns._ID} = (SELECT MAX(${BaseColumns._ID}) FROM " +
                "${FeedReaderContract.FeedEntry.TABLE_NAME})"
        val selectionArgs = null
        val count = db.update(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }

}