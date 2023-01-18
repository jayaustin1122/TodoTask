package com.example.todos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.databinding.RowItemsBinding
import com.example.todos.db.Task

class TaskAdapter(val list : MutableList<Task>):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding : RowItemsBinding):RecyclerView.ViewHolder(binding.root)
    private var listener : TaskAdapterClick? = null
    fun setlistener(listener : TaskAdapterClick){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = RowItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.tvTitle.text = this.title
                binding.tvDesc.text = this.desc
                binding.textViewNoteDate.text = this.dateOfCreation
                binding.textViewNoteTime.text = this.timeOfCreation
                binding.btnImgDelete.setOnClickListener {
                    listener?.onDeleteBtn(this, position)
                }
//                binding.btnImgEdit.setOnClickListener {
//                    listener?.onEditBtn(this)
//                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface TaskAdapterClick{
        fun onDeleteBtn(task: Task, position : Int)
        fun onEditBtn(task: Task)
    }
}