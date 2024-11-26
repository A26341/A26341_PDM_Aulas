package com.example.a26341_mynewsapp

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.a26341_mynewsapp.ui.theme.A26341_MyNewsAppTheme
import com.example.a26341_mynewsapp.ui.theme.models.Cat
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.text.toFloat


@Composable
fun CenteredLoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Gray,           // Cor da roda
                strokeWidth = 10.dp,           // Largura da linha
                modifier = Modifier.size(80.dp) // Tamanho do indicador
            )
        }
    }
}

@Composable
fun CatsView(navController: NavHostController = rememberNavController()) {
    var isLoading by remember { mutableStateOf(true) }
    var originalCats by remember { mutableStateOf<List<Cat>>(emptyList()) }
    var filteredCats by remember { mutableStateOf<List<Cat>>(emptyList()) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Menu dos gatos",
                isSearchVisible = false,
                showSearchIcon = true,
                searchQuery = "",
                onSearchQueryChanged = {},
                onSearchClicked = {}
            )
        },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                CenteredLoadingIndicator(isLoading = isLoading)

                LaunchedEffect(Unit) {
                    println("Carregando dados...")
                    val catApi = CatAPI()
                    originalCats = catApi.fetchCatImages()
                    filteredCats = originalCats
                    isLoading = false
                    println("Dados carregados.")
                }

                if (!isLoading && filteredCats.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 300.dp),
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredCats) { cat ->
                            CatCard(cat = cat)
                        }
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun CatsViewPreview() {
    CatsView()
}

