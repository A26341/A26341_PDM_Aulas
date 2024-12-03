package com.example.a26341_myshoppinglist.ui.home.ListType

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a26341_myshoppinglist.Screen
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme
import com.example.a26341_myshoppinglist.ui.theme.appFontBold16


@Composable
fun ListTypeRowView(modifier: Modifier = Modifier,
                    listType: ListType,
                    onNavigateToListItems: (ListType) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onNavigateToListItems(listType)},
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = listType.name ?: "",
            style = appFontBold16
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = listType.description ?: ""
        )
    }
}

@Preview(showBackground = true)
@Composable


fun ListTypeRowViewPreview(){
    A26341_MyShoppingListTheme {
        val navController = rememberNavController()
        ListTypeRowView(listType =  ListType("",
            "Compras de casa",
            "As compras que são para casa",
            owners = listOf()),
            onNavigateToListItems = { listType ->
                navController.navigate("${Screen.ListItems.route}/${listType.docId}")
            }
        )
    }
}