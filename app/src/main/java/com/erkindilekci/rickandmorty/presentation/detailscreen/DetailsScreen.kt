package com.erkindilekci.rickandmorty.presentation.detailscreen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erkindilekci.rickandmorty.R
import com.erkindilekci.rickandmorty.data.remote.dto.detail.CharacterDetail
import com.erkindilekci.rickandmorty.util.PaletteGenerator.convertImageUrlToBitmap
import com.erkindilekci.rickandmorty.util.PaletteGenerator.extractColorsFromBitmap
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val selectedCharacter by viewModel.selectedCharacter.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        selectedCharacter?.let { ListScreenItem(item = it, navController = navController) }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListScreenItem(
    item: CharacterDetail,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val colorPalette by viewModel.colorPalette
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#FFFFFF") }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(Color(parseColor(darkVibrant)))
    }

    LaunchedEffect(key1 = true) {
        val bitmap = item.image?.let {
            convertImageUrlToBitmap(
                imageUrl = it,
                context = context
            )
        }
        if (bitmap != null) {
            viewModel.setColorPalette(
                colors = extractColorsFromBitmap(bitmap)
            )
        }
    }

    scope.launch {
        vibrant = colorPalette["vibrant"] ?: "#000000"
        darkVibrant = colorPalette["darkVibrant"] ?: "#000000"
        onDarkVibrant = colorPalette["onDarkVibrant"] ?: "#CCCCCC"
    }

    if (darkVibrant != "#000000") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(parseColor(darkVibrant))),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 16.dp, start = 24.dp, bottom = 16.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    textAlign = TextAlign.Center
                )

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.name,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .height(300.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(Color(parseColor(vibrant)))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val rowColor = Color(parseColor(onDarkVibrant))

                    ItemRow(
                        icon = R.drawable.bunny,
                        title = "Species",
                        text = item.species,
                        color = rowColor
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(parseColor(onDarkVibrant)),
                        thickness = 0.5.dp
                    )

                    ItemRow(
                        icon = R.drawable.eyes,
                        title = "Gender",
                        text = item.gender,
                        color = rowColor
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(parseColor(onDarkVibrant)),
                        thickness = 0.5.dp
                    )

                    ItemRow(
                        icon = R.drawable.heartrate,
                        title = "Status",
                        text = item.status,
                        color = rowColor
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(parseColor(onDarkVibrant)),
                        thickness = 0.5.dp
                    )

                    ItemRow(
                        icon = R.drawable.map,
                        title = "Location",
                        text = item.location.name,
                        color = rowColor
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(parseColor(onDarkVibrant)),
                        thickness = 0.5.dp
                    )

                    ItemRow(
                        icon = R.drawable.shuttle,
                        title = "Origin",
                        text = item.origin.name,
                        color = rowColor
                    )
                }
            }
        }
    } else {
        vibrant = colorPalette["vibrant"] ?: "#318bbe"
    }
}

@Composable
fun ItemRow(
    @DrawableRes icon: Int,
    title: String,
    text: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 20.sp,
            textAlign = TextAlign.End
        )
    }
}
