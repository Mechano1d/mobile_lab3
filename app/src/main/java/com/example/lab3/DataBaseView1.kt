package com.example.lab3

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class DataBaseView1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_v1_db)
        update()
    }

    fun update(){
        print(result)
        val table1 = findViewById<TableLayout>(R.id.table1)
        val row = TableRow(this)
        val id = TextView(this)
        id.text = "ID"
        val name = TextView(this)
        name.text = "Name"
        val time = TextView(this)
        time.text = "Time"
        row.addView(id)
        row.addView(name)
        row.addView(time)
        table1.addView(row)
        for (r in result)
        {
            val row = TableRow(this)
            val id = TextView(this)
            id.text = r.ID
            val name = TextView(this)
            name.text = r.Name
            val time = TextView(this)
            time.text = r.Time
            row.addView(id)
            row.addView(name)
            row.addView(time)
            table1.addView(row)
        }
    }
}