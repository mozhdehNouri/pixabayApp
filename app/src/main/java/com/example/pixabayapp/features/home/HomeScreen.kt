package com.example.pixabayapp.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    when (uiState) {
        HomeUiState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (uiState as HomeUiState.Error).message.toString())
            }
        }
        is HomeUiState.Success -> {
            HomeBody(
                uiState as HomeUiState.Success, scrollBehavior = scrollBehavior
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody(
    uiState: HomeUiState.Success,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Pixabay", color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme
                        .colorScheme.primaryContainer, scrolledContainerColor = Color
                        .Transparent
                ),
                scrollBehavior = scrollBehavior,
                modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            )
        }) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            VideoBanner(videoList = uiState.video)
            Text("E X P L O R E", style = MaterialTheme.typography.titleLarge, modifier= Modifier
                .padding(vertical = 10.dp))
            PhotoBanner(photoList = uiState.pic)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoBanner(
    videoList: List<String>,
    modifier: Modifier = Modifier
) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState {
            videoList.count()
        },
        modifier = modifier
            .width(412.dp)
            .height(221.dp).padding(top = 10.dp, bottom = 10.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 5.dp
    ) { i ->
        val item = videoList[i]
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(item)
                .memoryCachePolicy(CachePolicy.ENABLED).build()
        )
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
        Image(
            modifier = Modifier
                .height(400.dp)
                .clip(MaterialTheme.shapes.small),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PhotoBanner(modifier: Modifier = Modifier, photoList: List<String>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 5.dp,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier,
        content = {
            items(photoList) { photo ->
                AsyncImage(
                    model = photo,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .width(Random.nextInt(300, 500).dp)
                        .height(Random.nextInt(100, 200).dp)
                )
            }
        }
    )
}