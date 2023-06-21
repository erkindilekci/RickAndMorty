package com.erkindilekci.rickandmorty.presentation.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erkindilekci.rickandmorty.data.remote.dto.Location
import com.erkindilekci.rickandmorty.data.remote.dto.Origin
import com.erkindilekci.rickandmorty.data.remote.dto.Result
import com.erkindilekci.rickandmorty.presentation.ui.theme.RickBlue
import com.erkindilekci.rickandmorty.util.Screen

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val characters = viewModel.pager.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            ListScreenTopAppBar()
        },
        content = {
            ListScreenContent(
                navController = navController,
                characters = characters,
                modifier = Modifier.padding(it)
            )
        }
    )
}

@Composable
fun ListScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<Result>
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Adaptive(180.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(characters.itemCount) { i ->
            val item = characters[i]
            item?.let {
                ListScreenItem(navController, item)
            }
        }

        if (characters.loadState.refresh is LoadState.Loading || characters.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(12.dp)
                            .padding(top = 16.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(12.dp)
                            .padding(top = 16.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun ListScreenItem(
    navController: NavController,
    item: Result
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(230.dp)
            .padding(end = 8.dp, start = 8.dp, bottom = 16.dp)
            .clickable { navController.navigate(Screen.DetailsScreen.passId(item.id)) },
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = RickBlue)
    ) {
        Column {
            Text(
                text = item.name, color = Color.White, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (item.status == "Alive") Color.Green else Color.Red)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Status: ${item.status}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .crossfade(700)
                    .build(),
                contentDescription = item.name,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val item = Result(
            created = "",
            episode = emptyList(),
            gender = "",
            id = 0,
            image = "",
            location = Location("", ""),
            name = "Rick Sanchez",
            origin = Origin("", ""),
            species = "",
            status = "Alive",
            type = "",
            url = ""
        )

        ListScreenItem(rememberNavController(), item)
    }
}
