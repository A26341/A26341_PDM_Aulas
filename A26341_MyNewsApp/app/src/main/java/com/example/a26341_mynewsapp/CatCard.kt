package com.example.a26341_mynewsapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a26341_mynewsapp.ui.theme.A26341_MyNewsAppTheme
import com.example.a26341_mynewsapp.ui.theme.models.Cat
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter


@Composable
fun CatCard(cat: Cat) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .border(
                BorderStroke(5.dp, Color.LightGray),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .clickable { cat.wikipediaUrl?.let{ url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } }
    ) {
        Row { Text(text = cat.id.toString()) }
        Row {
            cat.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .clip(CircleShape),
                    contentDescription = "Cat Image"
                )
            }
        }

        // Exibe cada linha apenas se o valor não for nulo
        cat.name?.let { name ->
            Row { Text(text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis) }
        }
        cat.origin?.let { origin ->
            Row { Text(text = origin,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis) }
        }
        cat.temperament?.let { temperament ->
            Row { Text(text = temperament,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis) }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CatBoardPreview() {
        A26341_MyNewsAppTheme {
            CatCard(cat = Cat(id = "1", name = "Cat", imageUrl = "https://example.com/cat.jpg", url = "Cat", origin = "Origin", temperament = "Temperament", wikipediaUrl = null))
        }
    }
