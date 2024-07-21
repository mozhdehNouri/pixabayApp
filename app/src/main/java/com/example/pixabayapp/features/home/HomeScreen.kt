package com.example.pixabayapp.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlin.random.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToPhoto: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = modifier.fillMaxSize())
            }
        }
        is HomeUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (uiState as HomeUiState.Error).message.toString())
            }
        }
        is HomeUiState.Success -> {
            HomeBody(uiState as HomeUiState.Success, navigateToPhoto)
        }
    }
}

@Composable
fun HomeBody(
    uiState: HomeUiState.Success,
    navigateToPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .clickable { navigateToPhoto() },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Search Photo",
                style = MaterialTheme.typography.headlineSmall,
                color =
                MaterialTheme.colorScheme.onBackground
            )
            Image(
                imageVector = Icons.Filled.Search,
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = null
            )

        }

        PhotoBanner(photoList = uiState.pic)

        Text(
            text = "Search Video",
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        VideoBanner(modifier = Modifier.weight(1f), videoList = uiState.video)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoBanner(
    photoList: List<String>,
    modifier: Modifier = Modifier
) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState {
            photoList.count()
        },
        modifier = modifier
            .width(412.dp)
            .height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp
        ) { i ->
        val item = photoList[i]
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(item)
                .memoryCachePolicy(CachePolicy.ENABLED).build()
        )
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
        Image(
            modifier = Modifier
                .height(205.dp)
                .clip(MaterialTheme.shapes.medium),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun VideoBanner(modifier: Modifier = Modifier, videoList: List<String>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(videoList, key = { it }) { photo ->
                AsyncImage(
                    model = photo,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = modifier
                        .clip(MaterialTheme.shapes.small)
                        .width(Random.nextInt(300, 700).dp)
                        .height(Random.nextInt(300, 500).dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}