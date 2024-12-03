package com.example.a26341_myshoppinglist.ui.home.ListItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a26341_myshoppinglist.R
import com.example.a26341_myshoppinglist.models.ListItem
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme



@Composable
fun ListItemsView(
    modifier: Modifier = Modifier,
    listTypeName: String,
    listTypeDocId: String,
    onNavigateToAddList: (String, String) -> Unit
){

    val viewModel by remember { mutableStateOf(ListItemsViewModel()) }
    val state = viewModel.state

    ListItemsViewContent(
        modifier = modifier,
        state = state.value,
        listTypeName = listTypeName, // Passar listTypeName
        listTypeDocId = listTypeDocId, // Passar listTypeDocId
        onNavigateToAddList = { listTypeName, listTypeDocId ->
            onNavigateToAddList(listTypeName, listTypeDocId)
        },
        viewModel = viewModel
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.loadListItems(listTypeDocId, listTypeName)

    }
}
@Composable
fun ListItemsViewContent(
    modifier: Modifier = Modifier,
    state: ListState,
    listTypeName: String, // Adicionar listTypeName
    listTypeDocId: String, // Adicionar listTypeDocId
    onNavigateToAddList: (String, String) -> Unit, // Atualizar onNavigateToAddList
    viewModel: ListItemsViewModel
){
    var addUserDialogVisible by remember { mutableStateOf(false) }


    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(
                    items = state.listItems
                ) { index, item ->
                    ListItemRowView(
                        listItem = item,
                        onCheckedChange = { isChecked ->
                            val updatedItem = item.copy(ischecked = isChecked)
                            viewModel.updateListItem(updatedItem, listTypeName, listTypeDocId)
                        }
                    )
                }
            }
        }
        Column( modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)){
            Button(
                modifier = Modifier
                    .padding(5.dp)
                    .height(80.dp)
                    .width(80.dp)
                ,
                onClick = { addUserDialogVisible = true }) {
                Image(
                    modifier = Modifier
                        .size(60.dp),
                    painter = painterResource(R.drawable.baseline_person_add_24),
                    contentDescription = "add list",
                    colorFilter = ColorFilter.tint(Color.White))
            }
            Button(
                modifier = Modifier
                    .padding(5.dp)
                    .height(80.dp)
                    .width(80.dp)
                ,
                onClick = {
                    onNavigateToAddList(listTypeName, listTypeDocId)
                }) {
                Image(
                    modifier = Modifier
                        .size(60.dp),
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = "add list",
                    colorFilter = ColorFilter.tint(Color.White))
            }
            if (addUserDialogVisible) {
                var email by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { addUserDialogVisible = false },
                    title = { Text("Inserir Email") },
                    text = {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") }
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Fazer algo com o email inserido
                                viewModel.addOwner(listTypeDocId, email)
                                addUserDialogVisible = false
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { addUserDialogVisible = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }

        }

    }

}



@Preview(showBackground = true)
@Composable
fun AddListTypesViewPreview() {
    A26341_MyShoppingListTheme {
        ListItemsViewContent(
            state = ListState(listItems = arrayListOf(
                ListItem("", "tipo", "objecto", false),
                ListItem("", "tipo", "objecto", false)
            )),
            listTypeName = "Nome da Lista", // Fornecer um valor para listTypeName
            listTypeDocId = "ID da Lista",
            onNavigateToAddList = { _, _ -> },
            viewModel = ListItemsViewModel()
        )

    }
}