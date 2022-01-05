package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils;
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import  java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listofTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val OnlongClickListener= object : TaskItemAdapter.OnlongClickListener {
            override fun onItemLongClicked(position: Int) {

            listofTasks.removeAt(position)

            adapter.notifyItemRemoved(position)

                saveItems()
            }
        }

    //listofTasks.add("Do laundry")
    //listofTasks.add("Go for a walk")

        LoadItems()

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    adapter = TaskItemAdapter(listofTasks, OnlongClickListener)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

      val inputTextField = findViewById<EditText>(R.id.addTaskField)

    findViewById<Button>(R.id.button).setOnClickListener {

        val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()

        listofTasks.add(userInputtedTask)

        adapter.notifyItemInserted(listofTasks.size - 1)

        inputTextField.setText("")

        saveItems()
    }
    }

    fun getDataFile() : File {
        return File(filesDir,"data.txt")
    }

    fun LoadItems() {
        try {
            listofTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    fun saveItems() {
        try {
        FileUtils.writeLines(getDataFile(), listofTasks)
    }   catch (ioException: IOException) {
        ioException.printStackTrace()
    }
    }
}
