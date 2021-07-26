package com.dicoding.courseschedule.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListActivity
import com.dicoding.courseschedule.ui.setting.SettingsActivity
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private var startTime: String = ""
    private var endTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this,factory).get(AddCourseViewModel::class.java)
    }

    private fun insertCourse(){
        val courseName = findViewById<TextInputEditText>(R.id.tv_course_name).text.toString()
        val day = findViewById<Spinner>(R.id.day).selectedItemPosition
        val lecturer = findViewById<TextInputEditText>(R.id.tv_lecturer).text.toString()
        val note = findViewById<TextInputEditText>(R.id.tv_note).text.toString()


        if (startTime.isNotEmpty()
            && endTime.isNotEmpty()
            && courseName.isNotEmpty()
            && lecturer.isNotEmpty()){
            viewModel.insertCourse(courseName=courseName, day=day, lecturer=lecturer, note=note, startTime=startTime, endTime=endTime)
            finish()
        } else {
            Toast.makeText(this,"Input is invalid", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
                return true
            }
            else -> null
        } ?: return super.onOptionsItemSelected(item)

        startActivity(intent)
        return true
    }

    fun showStartTimePicker(view: View){
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager,"startTimePicker")
    }

    fun showEndTimePicker(view: View){
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager,"endTimePicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int){
        val startTimeIndication = findViewById<TextView>(R.id.start_time_indication)
        val endTimeIndication = findViewById<TextView>(R.id.end_time_indication)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag){
            "startTimePicker" -> {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE, minute)

                startTime = formatter.format(cal.time)
                startTimeIndication.text = startTime
            }
            "endTimePicker" -> {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE, minute)

                endTime = formatter.format(cal.time)
                endTimeIndication.text = endTime
            }
        }
    }

}