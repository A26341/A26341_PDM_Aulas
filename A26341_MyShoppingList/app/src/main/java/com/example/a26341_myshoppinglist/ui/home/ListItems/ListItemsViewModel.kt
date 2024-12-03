package com.example.a26341_myshoppinglist.ui.home.ListItems

import android.util.Log
import androidx.activity.result.launch
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a26341_myshoppinglist.models.ListItem
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.repositories.ListTypeRepository

data class ListState(
    val listItems : List<ListItem> = emptyList(),
    val listName: String = "",
    val listDocId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListItemsViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun loadListItems( listTypeDocId: String, listTypeName: String){
        ListTypeRepository.getAllItems( { listItems ->
            state.value = state.value.copy(
                listItems = listItems
            )
            for (item in listItems){
                Log.d("TAG", item.tipo?:"no name")
            }
        }, listTypeDocId.toString(), listTypeName.toString())
    }

    fun updateListItem(updatedItem: ListItem, listName: String, listDocId: String) {
            ListTypeRepository.updateItem(updatedItem, listName, listDocId) {
                state.value = state.value.copy(listItems = state.value.listItems.map {
                    if (it.docId == updatedItem.docId) updatedItem else it
                })
            }
        }
    fun addOwner(listTypeDocId: String, email: String) {
        ListTypeRepository.addOwner(listTypeDocId, email)
    }
    }





