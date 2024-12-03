package com.example.a26341_myshoppinglist.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.example.a26341_myshoppinglist.models.ListItem
import com.example.a26341_myshoppinglist.models.ListType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

object ListTypeRepository {
    val db = Firebase.firestore
    val auth = Firebase.auth

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

    fun getAll(onSuccess: (List<ListType>) -> Unit) {
        db.collection("ListTypes")
            .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { value, error ->
                val listTypes = value?.documents?.mapNotNull {
                    val itemList = it.toObject(ListType::class.java)
                    itemList?.docId = it.id
                    itemList
                }
                listTypes?.let { onSuccess(it) }
            }
    }

    fun addItem(listItem: ListItem, listName: String, docId: String, onAddListSuccess: () -> Unit) {

        Log.d("ListTypeRepository", "A adicionar item à lista com ID: $docId, ListName: $listName") // Adicionar Log

        db.collection("ListTypes")
            .document(docId)
            .collection(listName)
            .add(listItem)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun getAllItems(onSuccess: (List<ListItem>) -> Unit,
                    listTypeDocId: String,
                    listTypeName: String) {

        Log.d(
            "ListTypeRepository",
            "A procurar itens para a lista com ID: $listTypeDocId, ListTypeName: $listTypeName"
        ) // Adicionar Log

        db.collection("ListTypes")
            .document(listTypeDocId)
            .collection(listTypeName)
            .addSnapshotListener { value, error ->
                val listItems = value?.documents?.mapNotNull {
                    val itemList = it.toObject(ListItem::class.java)
                    itemList?.docId = it.id
                    itemList
                }

                Log.d(
                    "ListTypeRepository",
                    "Dados recebidos do Firebase: $listItems"
                ) // Adicionar Log

                listItems?.let { onSuccess(it) }
            }
    }

        fun updateItem(
            updatedItem: ListItem,
            listName: String,
            docId: String,
            onUpdateItemSuccess: () -> Unit
        ) {
            Log.d(
                "ListTypeRepository",
                "A atualizar item na lista com ID: $docId, ListName: $listName"
            )
            updatedItem.docId?.let {
                val itemRef = db.collection("ListTypes")
                    .document(docId)
                    .collection(listName)
                    .document(it)
                    .update(
                        "tipo",
                        updatedItem.tipo,
                        "objecto",
                        updatedItem.objecto,
                        "ischecked",
                        updatedItem.ischecked
                    )
                    .addOnSuccessListener {
                        Log.d(TAG, "Item atualizado com sucesso")
                        onUpdateItemSuccess() // Chamar a função de sucesso após a atualização
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Erro ao atualizar item", e)
                        // Lidar com o erro, por exemplo, exibir uma mensagem ao usuário
                    }
            }
        }



    fun addOwner(listTypeDocId: String, email: String, onAddOwnerSuccess: () -> Unit = {}) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val uid = it.documents[0].getString("uid")
                    if (uid != null) {
                        db.collection("ListTypes")
                            .document(listTypeDocId)
                            .update("owners", FieldValue.arrayUnion(uid))
                            .addOnSuccessListener {
                                onAddOwnerSuccess()
                                }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Erro ao adicionar proprietário", e)
                            }
                    }
                    }
                }
            }

    }

