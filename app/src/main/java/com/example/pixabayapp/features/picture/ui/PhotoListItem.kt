package com.example.pixabayapp.features.picture.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.pixabayapp.features.PixabayDarkPreview
import com.example.pixabayapp.features.picture.ui.data.HitPictureUIResponse

@Composable
fun PhotoListItem(item: HitPictureUIResponse) {
    ConstraintLayout(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
    ) {
        val (image, title, subtitle, shareButton, saveButton) = createRefs()

        AsyncImage(
            model = item.largeImageURL,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                    )
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = "tags: ${item.tags}",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    bottom.linkTo(subtitle.top)
                    top.linkTo(image.bottom)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 4.dp)
        )
        Text(
            text = "${item.views}k views.",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .constrainAs(subtitle) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(title.start)
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 24.dp, top = 4.dp)
        )
        IconButton(
            onClick = { },
            modifier = Modifier
                .constrainAs(saveButton) {
                    top.linkTo(image.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                Icons.Default.MoreVert,
                tint = Color.Gray,
                contentDescription = null
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .constrainAs(shareButton) {
                    top.linkTo(image.bottom)
                    end.linkTo(saveButton.start)
                }
        ) {
            Icon(
                Icons.Default.Share,
                tint = Color.Gray,
                contentDescription = null
            )
        }

    }
}

@PixabayDarkPreview
@Composable
fun Preview(modifier: Modifier = Modifier) {
    PhotoListItem(
        HitPictureUIResponse(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            "",
            0,
            "",
            0,
            "",
            0,
            "",
            "",
            "",
            0,
            "",
            0,
            0,
            "",
            0
        )
    )
}