package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.ui.list.TaskAdapter
import com.dicoding.todoapp.ui.list.TaskViewModel
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val title = findViewById<TextInputEditText>(R.id.detail_ed_title)
        val desc = findViewById<TextInputEditText>(R.id.detail_ed_description)
        val due = findViewById<TextInputEditText>(R.id.detail_ed_due_date)
        val deleteBtn = findViewById<Button>(R.id.btn_delete_task)

        val detailId = intent.extras?.getInt(TASK_ID)
        if (detailId !== null){
            val factory = ViewModelFactory.getInstance(this)
            detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)
            detailTaskViewModel.setTaskId(detailId)

            detailTaskViewModel.task.observe(this, Observer{
                if (it != null){
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it.dueDateMillis
                    val date = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)

                    title.setText(it.title)
                    desc.setText(it.description)
                    due.setText(date)
                }
            })

            deleteBtn.setOnClickListener {
                detailTaskViewModel.deleteTask()
                finish()
            }
        }


        //TODO 11 : Show detail task and implement delete action


    }
}