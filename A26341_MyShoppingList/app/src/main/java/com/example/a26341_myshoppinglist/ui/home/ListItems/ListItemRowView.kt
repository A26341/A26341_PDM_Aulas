package com.example.a26341_myshoppinglist.ui.home.ListItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a26341_myshoppinglist.models.ListItem
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme
import com.example.a26341_myshoppinglist.ui.theme.appFontBold16
import org.checkerframework.framework.qual.DefaultQualifier


@Composable
fun ListItemRowView(modifier: Modifier = Modifier,
                    listItem: ListItem, onCheckedChange: (Boolean) -> Unit = {}
) {
    Row( modifier = modifier
        .height(80.dp)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column() {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = listItem.tipo ?: "",
                style = appFontBold16
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = listItem.objecto ?: ""
            )
        }
        Box(modifier = Modifier)
        {
            Checkbox(
                checked = listItem.ischecked,
                onCheckedChange = onCheckedChange
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTypeRowViewPreview(){
    A26341_MyShoppingListTheme {
        ListItemRowView(listItem=  ListItem("","cozinha",
            "faca",
            false))
    }
}