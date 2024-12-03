package com.example.a26341_myshoppinglist.ui.home.ListType

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a26341_myshoppinglist.R
import com.example.a26341_myshoppinglist.Screen
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme


@Composable
fun ListTypesView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToAddList : ()->Unit,
){

    val viewModel by remember { mutableStateOf(ListTypesViewModel()) }
    val state = viewModel.state

    ListTypesViewContent(
        modifier = modifier,
        state = state.value,
        navController = navController,
        onNavigateToAddList = onNavigateToAddList
    )

    LaunchedEffect (key1 = Unit){
        viewModel.loadListTypes()
    }
}
@Composable
fun ListTypesViewContent(
    modifier: Modifier = Modifier,
    state: ListState,
    navController: NavHostController,
    onNavigateToAddList : ()->Unit = {}
){

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(
                    items = state.listTypes
                ) { index, item ->
                    ListTypeRowView(
                        listType = item,
                        onNavigateToListItems = { listType ->
                            val route = Screen.ListItems.route + "/${listType.name}/${listType.docId}"
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp)
            ,
            onClick = {
                onNavigateToAddList()
            }) {
            Image(
                modifier = Modifier
                    .size(60.dp),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add list",
                colorFilter = ColorFilter.tint(Color.White))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddListTypesViewPreview() {
    A26341_MyShoppingListTheme {
        val navController = rememberNavController() // Create a mock navController
        ListTypesViewContent(
            state = ListState(
                listTypes = arrayListOf(
                    ListType("", "Compras de Casa", "As compras que vão para casa", listOf()),
                    ListType("", "Compras de Escritório", "As compras que vão para o trabalho", listOf())
                )
            ),
            navController = navController // Pass the mock navController
        )
    }
}