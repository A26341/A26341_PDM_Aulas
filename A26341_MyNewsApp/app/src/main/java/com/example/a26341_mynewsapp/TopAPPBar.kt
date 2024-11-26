package com.example.a26341_mynewsapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a26341_mynewsapp.ui.theme.A26341_MyNewsAppTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    isSearchVisible: Boolean = false,
    showSearchIcon: Boolean = false,
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit ={},
    onSearchClicked: () -> Unit={}
) {
    TopAppBar(
        title = {
            if (isSearchVisible) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Pesquisar...") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(title)
            }
        },
        actions = {
            if (showSearchIcon) {
                IconButton(onClick = onSearchClicked) {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    A26341_MyNewsAppTheme {
        MyTopAppBar("Teste")
    }
}
