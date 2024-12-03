package com.example.a26341_mynewsapp.ui.bookmarks

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a26341_mynewsapp.AppDatabase
import com.example.a26341_mynewsapp.models.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

data class CatState(
    val cats: ArrayList<Cat> = arrayListOf<Cat>(),
    val isLoading: Boolean = false,
    val error: String? = null

)


class CatsViewModel : ViewModel() {
    private val _state = MutableStateFlow(CatState())
    val state: StateFlow<CatState> = _state.asStateFlow()

    fun getCats(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
          val catsDataCache = AppDatabase
              .getDatabase(context)
              ?.catDataCacheDao()
              ?.getAll()

            val cats : ArrayList<Cat> = arrayListOf()
            for (cat in catsDataCache?: emptyList()) {

                cats.add(Cat.fromJSON(JSONObject(cat.catJsonString)))
            }
            viewModelScope.launch(Dispatchers.Main) {
                _state.value = CatState(
                    cats = cats
                )
            }

        }

    }
}