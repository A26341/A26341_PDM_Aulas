package com.example.a26341_myshoppinglist.ui.home.ListItems

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a26341_myshoppinglist.models.ListItem
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.repositories.ListTypeRepository


data class AddListItemState(
    var tipo: String = "",
    var objecto: String = "",
    var ischecked: Boolean = false,
    var listDocId: String = "",
    var listName: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListItemsViewModel() : ViewModel() {

    var state = mutableStateOf(AddListItemState())
        private set

    private val tipo
        get() = state.value.tipo
    private val objecto
        get() = state.value.objecto
    private val ischecked
        get() = state.value.ischecked
    private val listDocId
        get() = state.value.listDocId
    private val listName
        get() = state.value.listName

    fun onTipoChange(newValue: String) {
        state.value = state.value.copy(tipo = newValue)
    }

    fun onObjectoChange(newValue: String) {
        state.value = state.value.copy(objecto = newValue)
    }



    fun addList(){
        ListTypeRepository.addItem(
            listItem = ListItem(docId = "", tipo = tipo, objecto = objecto, ischecked = false),
            listName = listName,
            docId = listDocId,
            onAddListSuccess = {
                Log.d("TAG", "addList: success")
            }
        )
    }
    }
