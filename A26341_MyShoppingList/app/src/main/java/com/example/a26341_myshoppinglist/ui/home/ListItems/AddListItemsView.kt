package com.example.a26341_myshoppinglist.ui.home.ListItems

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme


@Composable
fun AddListItemsView(modifier: Modifier = Modifier,
                     listTypeName: String = "",
                     listTypeDocId: String = "",
                    navController : NavController = rememberNavController()
) {

    val viewModel : AddListItemsViewModel = viewModel()
    val state = viewModel.state
    state.value.listName = listTypeName
    state.value.listDocId = listTypeDocId

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            TextField(value = state.value.tipo,
                onValueChange = viewModel::onTipoChange,
                placeholder = {
                    Text(text = "Tipo do objecto")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.value.objecto,
                onValueChange = viewModel::onObjectoChange,
                placeholder = {
                    Text(text = "Descrição do objecto")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {

                viewModel.addList()
                navController.popBackStack()
            }) {
                Text(text = "Adicionar")
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddListTypeViewPreview() {
    A26341_MyShoppingListTheme {
        AddListItemsView()
    }
}


