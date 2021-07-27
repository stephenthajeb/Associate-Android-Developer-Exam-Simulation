package com.dicoding.habitapp.ui.random

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.ui.countdown.CountDownActivity
import com.dicoding.habitapp.utils.HABIT

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view

        fun bind(pageType: PageType, pageData: Habit) {
            with(itemView){
                findViewById<TextView>(R.id.pager_tv_title).text = pageData.title
                findViewById<TextView>(R.id.pager_tv_start_time).text = pageData.startTime
                findViewById<TextView>(R.id.pager_tv_minutes).text = pageData.minutesFocus.toString()

                val ivPriority = findViewById<ImageView>(R.id.item_priority_level)
                when(pageType){
                    PageType.HIGH -> ivPriority.setImageDrawable(resources.getDrawable(R.drawable.ic_priority_high))
                    PageType.MEDIUM -> ivPriority.setImageDrawable(resources.getDrawable(R.drawable.ic_priority_medium))
                    else -> ivPriority.setImageDrawable(resources.getDrawable(R.drawable.ic_priority_low))
                }

                findViewById<Button>(R.id.btn_open_count_down).setOnClickListener{
                    val intent = Intent(context, CountDownActivity::class.java)
                    intent.putExtra(HABIT,pageData)

                    context.startActivity(intent)
                }
            }
        }
    }
}
