package com.example.todos.fragments_Activities//package com.example.todos.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.adapter.TaskAdapter
import com.example.todos.db.Task
import com.example.todos.databinding.FragmentHomeBinding
import com.example.todos.db.TaskDao
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment(), AddTaskFragment.DialogAddBtnClickListener,
    TaskAdapter.TaskAdapterClick{
   private lateinit var binding : FragmentHomeBinding
   private lateinit var auth : FirebaseAuth
   private lateinit var dbRef : DatabaseReference
   private lateinit var navController: NavController
   private var popUpFragment : AddTaskFragment? = null
   private lateinit var adapter: TaskAdapter
   private lateinit var list: MutableList<Task>
   private lateinit var dao : TaskDao
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        getData()
        addTask()
    }
    private fun getData(){
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()

                var dataFromDatabase = snapshot.children

                for(data in dataFromDatabase){
                    var id = data.key.toString()
                    var title = data.child("title").value.toString()
                    var desc = data.child("desc").value.toString()
                    var currentDate = data.child("dateOfCreation").value.toString()
                    var currentTime = data.child("timeOfCreation").value.toString()

                    var employee = Task(title,desc,currentDate,currentTime,id)
                    list.add(employee)
                }
                Log.d(TAG, "onDataChange: " + list)
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }
    private fun addTask() {

        binding.btnAdd.setOnClickListener {
            if (popUpFragment != null)
                childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()
            popUpFragment = AddTaskFragment()
            popUpFragment!!.setListener(this)
            popUpFragment!!.show(
                childFragmentManager,
                AddTaskFragment.TAG
            )
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference.child("Tasks")
            .child(auth.currentUser?.uid.toString())
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)
        list = mutableListOf()
        adapter = TaskAdapter(list)
        adapter.setlistener(this)
        binding.myRecyclerView.adapter = adapter
    }


    override fun onAddTask(task: Task) {
        dbRef.push().setValue(task).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
                popUpFragment!!.dismiss()
            }else{
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popUpFragment!!.dismiss()
        }
    }

    override fun onEditTask(task: Task, etAddTitle: TextInputEditText
    ) {

//        val hashMap =
//            HashMap<String, Any>()
//        hashMap["name"] = task.title
//        hashMap["position"] = task.desc
//        //update is required key, this is manually input to update
//        dao.update("-MXgqaKEPSYq9q8SQaVW", hashMap)
//            .addOnSuccessListener { suc: Void? ->
//                Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show()
//                finish()
//            }.addOnFailureListener { er: Exception ->
//                Toast.makeText(
//                    this,
//                    "" + er.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//    }
//        val map = HashMap<String,Any>()
//        map["title"] = task.title
//        dbRef.updateChildren(map).addOnCompleteListener {
//            if (it.isSuccessful) {
//                Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
//            }
//            popUpFragment?.dismiss()
//        }

    }


    override fun onDeleteBtn(task : Task, position : Int) {
        dbRef.child(task.count).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditBtn(task: Task) {
        if (popUpFragment != null)
            childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()

        popUpFragment = AddTaskFragment.newInstance(
            task.title,
            task.desc,
            task.dateOfCreation,
            task.timeOfCreation,
            task.count
        )
        popUpFragment!!.setListener(this)
        popUpFragment!!.show(childFragmentManager, AddTaskFragment.TAG)
    }
}
