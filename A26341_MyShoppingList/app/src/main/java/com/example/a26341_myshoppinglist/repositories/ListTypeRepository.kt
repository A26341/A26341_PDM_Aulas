package com.example.a26341_myshoppinglist.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.example.a26341_myshoppinglist.models.ListType
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object ListTypeRepository {
    val db = Firebase.firestore

    fun add(listType: ListType, onAddListSuccess: () -> Unit) {


        var currentUser = Firebase.auth.currentUser

        //if (currentUser == null) {
        //    state.value = state.value.copy(error = "User not logged in")
        //    return
        //}

        currentUser?.uid?.let {
            listType.owners = arrayListOf(it)
        }

        db.collection("ListTypes")
            .add(listType)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun getAll(onSuccess: (List<ListType>) -> Unit){
        db.collection("ListTypes")
            .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { value, error ->
                val listTypes = value?.documents?.mapNotNull {
                    val itemList  = it.toObject(ListType::class.java)
                    itemList?.docId = it.id
                    itemList
                }
                listTypes?.let {  onSuccess(it) }
            }
    }

}