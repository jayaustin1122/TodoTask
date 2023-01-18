package com.example.todos.db

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TaskDao {
    var dbref : DatabaseReference = Firebase.database.reference

    fun add(task: Task){
        dbref.push().setValue(task)
    }
    fun get (): Query {
        return dbref.orderByKey()
    }
    fun remove(key:String){
        dbref.child(key).removeValue()
    }
    fun update(key: String,map: Map<String,String>){
        dbref.child(key).updateChildren(map)
    }
}