package com.example.a26341_myshoppinglist.ui.home.ListType

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.repositories.ListTypeRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


data class AddListTypeState(
    var name: String = "",
    var description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypeViewModel : ViewModel() {

    var state = mutableStateOf(AddListTypeState())
        private set

    private val name
        get() = state.value.name
    private val description
        get() = state.value.description

    fun onNameChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(description = newValue)
    }

    fun addList(){
        val auth = Firebase.auth

        val currentUser = auth.currentUser
        ListTypeRepository.add(
            listType = ListType("", name, description, listOf(currentUser.toString())), onAddListSuccess =  {}
        )
    }
}