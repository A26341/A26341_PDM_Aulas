package com.example.a26341_mynewsapp.ui.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.a26341_mynewsapp.models.Cat
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a26341_mynewsapp.ui.api.CatAPI
import com.example.a26341_mynewsapp.ui.components.MyTopAppBar
import com.example.a26341_mynewsapp.ui.components.CatCard
import com.example.a26341_mynewsapp.ui.components.MyBottomBar


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
fun BookmarksView(modifier: Modifier, navController: NavHostController) {

    val context = LocalContext.current
    val viewModel: CatsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    BookmarksViewContent(
        modifier = modifier,
        state = state
    )
    LaunchedEffect(Unit) {
        viewModel.getCats(context)
    }
}

@Composable
BookmarksViewContent(
    modifier: Modifier,
    state: CatState,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Bookmarks dos gatos",
                isSearchVisible = false,
                showSearchIcon = true,
                searchQuery = "",
                onSearchQueryChanged = {},
                onSearchClicked = {}
            )
        },
        bottomBar = { MyBottomBar(navController = navController) },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                CenteredLoadingIndicator(isLoading = state.isLoading)
            }
        }



//    @Preview(showBackground = true)
//    @Composable
//    fun BookmarksViewPreview() {
//        BookmarksView()
//    }
//

