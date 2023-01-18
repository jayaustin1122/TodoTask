package com.example.todos.fragments_Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todos.db.Task
import com.example.todos.databinding.FragmentAddTaskBinding
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var listener: DialogAddBtnClickListener
    private lateinit var list: MutableList<Task>
    private var task: Task? = null


    fun setListener(listener: HomeFragment) {
        this.listener = listener
    }

    companion object {
        const val TAG = "addTaskPopUpFragment"

        @JvmStatic
        fun newInstance(
            title: String,
            desc: String,
            dateOfCreation: String,
            timeOfCreation: String,
            count: String
        ) = AddTaskFragment().apply {
            arguments = Bundle().apply {
                putString("title", title)
                putString("desc", desc)
                putString("dateOfCreation", dateOfCreation)
                putString("timeOfCreation", timeOfCreation)
                putString("count", count)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            task = Task(
                arguments?.getString("title").toString(),
                arguments?.getString("desc").toString(),
                arguments?.getString("dateOfCreation").toString(),
                arguments?.getString("timeOfCreation").toString(),
                arguments?.getString("count").toString()
            )

            binding.etAddTitle.setText(task?.title)
            binding.etAddDescription.setText(task?.desc)
        }
        addNewTasks()
    }

    private fun addNewTasks() {

        //get id of the object from DB
        binding.btnAdd2.setOnClickListener {
            val title = binding.etAddTitle.text.toString()
            val description = binding.etAddDescription.text.toString()
            val currentTime = getCurrentTime()
            val currentDate = getCurrentDate()
            list = mutableListOf()
            var id = 0
            for (i in list.size.toString()) {
                id += 1
            }
            id + 1
            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (task ==null){
                    var tasks = Task(title.capitalize(), description, currentDate, currentTime, id.toString())
                    listener.onAddTask(tasks)
                }else{
                    task?.title = title
                    task?.desc = description
                    listener.onEditTask(task!!,binding.etAddTitle)
                }


            } else {
                Toast.makeText(context, "Cannot add a empty task", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgBtnClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogAddBtnClickListener {
        fun onAddTask(task: Task)
        fun onEditTask(
            task: Task,
            etAddTitle: TextInputEditText

            )

    }

    private fun getCurrentTime(): String {
        val tz = TimeZone.getTimeZone("GMT+08:00")
        val c = Calendar.getInstance(tz)
        val hours = String.format("%02d", c.get(Calendar.HOUR))
        val minutes = String.format("%02d", c.get(Calendar.MINUTE))
        return "$hours:$minutes"
    }


    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val currentDateObject = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(currentDateObject)
    }
}

