package com.example.a26341_myshoppinglist.ui.home.ListType

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.repositories.ListTypeRepository

data class ListState(
    val listTypes : List<ListType> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListTypesViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun addList() {
        ListTypeRepository.add(
            ListType("","title", "description", listOf())
        ){

        }
    }

    fun loadListTypes(){
        ListTypeRepository.getAll { listTypes ->
            state.value = state.value.copy(
                listTypes = listTypes
            )

            for (item in listTypes){
                Log.d("TAG", item.name?:"no name")
            }
        }
    }

}

