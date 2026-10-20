package com.example.lab3

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class DataBaseView2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_v2_db)
        update()
    }

    fun update(){
        print(result2)
        val table2 = findViewById<TableLayout>(R.id.table2)
        var row = TableRow(this)
        val id = TextView(this)
        id.text = "ID"
        val name = TextView(this)
        name.text = "Name"
        val surname = TextView(this)
        surname.text = "Surname"
        val middle_name = TextView(this)
        middle_name.text = "Middle Name"
        val time = TextView(this)
        time.text = "Time"
        row.addView(id)
        row.addView(name)
        row.addView(surname)
        row.addView(middle_name)
        row.addView(time)
        table2.addView(row)
        for (r in result2)
        {
            var row = TableRow(this)
            val id = TextView(this)
            id.text = r.ID
            val name = TextView(this)
            name.text = r.Name
            val surname = TextView(this)
            surname.text = r.Surname
            val middle_name = TextView(this)
            middle_name.text = r.Middle_Name
            val time = TextView(this)
            time.text = r.Time
            row.addView(id)
            row.addView(name)
            row.addView(surname)
            row.addView(middle_name)
            row.addView(time)
            table2.addView(row)
        }
    }
}