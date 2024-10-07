package com.example.pixabayapp.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.pixabayapp.R
import com.example.pixabayapp.features.ColumnAllPaddingLocal
import com.example.pixabayapp.features.RowVerticalPaddingLocal
import com.example.pixabayapp.features.TextVerticalPaddingLocal

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        HomeUiState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = (uiState as HomeUiState.Error).message.toString())
                Button(onClick = viewModel::getHomeBannerData, modifier = Modifier.padding(12.dp)) {
                    Text(
                        stringResource(R.string.lbl_try_agan),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

        }

        is HomeUiState.Success -> {
            HomeBody(
                uiState as HomeUiState.Success, onTabBarClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody(
    uiState: HomeUiState.Success,
    onTabBarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val columnPadding = ColumnAllPaddingLocal.current
    val textVerticalPadding = TextVerticalPaddingLocal.current
    Scaffold(modifier = modifier
        .fillMaxSize()
        .padding(columnPadding)
        .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.lbl_pixabay),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme
                        .colorScheme.primaryContainer, scrolledContainerColor = Color
                        .Transparent
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .clickable {
                        onTabBarClick()
                    },

                )
        }) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                stringResource(R.string.lbl_color),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(textVerticalPadding)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            VideoBanner(videoList = uiState.video)
            Text(
                stringResource(R.string.lbl_explore),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(textVerticalPadding)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            PhotoBanner(photoList = uiState.explorePic)

            Text(
                stringResource(R.string.lbl_animal),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(textVerticalPadding)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            PhotoBanner(photoList = uiState.animalPic)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoBanner(
    videoList: List<String>,
    modifier: Modifier = Modifier
) {
    val rowPadding = RowVerticalPaddingLocal.current
    val context = LocalContext.current
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState {
            videoList.count()
        },
        modifier = modifier
            .width(412.dp)
            .height(221.dp)
            .padding(rowPadding),
        preferredItemWidth = 186.dp,
        itemSpacing = 5.dp
    ) { i ->
        val item = videoList[i]
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context).data(item)
                .memoryCachePolicy(CachePolicy.ENABLED).build()
        )
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
        Image(
            modifier = Modifier
                .heightIn(400.dp)
                .clip(MaterialTheme.shapes.small),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PhotoBanner(
    photoList: List<String>,
    modifier: Modifier = Modifier
) {
    val rowPadding = RowVerticalPaddingLocal.current
    LazyRow(modifier = modifier.fillMaxWidth(), contentPadding = rowPadding) {
        items(photoList) { photo ->
            AsyncImage(
                model = photo,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(100.dp)
                    .height(221.dp)
            )
        }
    }
}