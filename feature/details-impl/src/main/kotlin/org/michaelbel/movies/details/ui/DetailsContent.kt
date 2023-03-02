package org.michaelbel.movies.details.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder
import org.michaelbel.movies.common.converter.DateConverter
import org.michaelbel.movies.details.ui.preview.MoviePreviewParameterProvider
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.data.ktx.isNotEmpty
import org.michaelbel.movies.ui.ktx.isErrorOrEmpty
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsContent(
    movie: MovieDb,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false
) {
    val context: Context = LocalContext.current
    val scrollState: ScrollState = rememberScrollState()
    var isNoImageVisible: Boolean by remember { mutableStateOf(false) }
    var movieReleaseDate = ""
    if (movie.releaseDate.isNotEmpty()){
        movieReleaseDate = DateConverter.formatDisplayDate(movie.releaseDate)
        movieReleaseDate = stringResource(id = R.string.release_date) + " " + movieReleaseDate
    }

    ConstraintLayout(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        val (image, noImageText, title, overview, releaseDate) = createRefs()

        val imageRequest: ImageRequest? = if (placeholder) {
            null
        } else {
            ImageRequest.Builder(context)
                .data(movie.backdropPath)
                .crossfade(true)
                .build()
        }

        val imageModifier: Modifier = if (placeholder) {
            Modifier
                .constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.value(220.dp)
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
        } else {
            Modifier
                .constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.value(220.dp)
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .clip(MaterialTheme.shapes.small)
        }

        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = imageModifier,
            onState = { state ->
                isNoImageVisible = movie.isNotEmpty && state.isErrorOrEmpty
            },
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = isNoImageVisible,
            modifier = Modifier
                .constrainAs(noImageText) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(title.top)
                },
            enter = fadeIn()
        ) {
            Text(
                text = stringResource(R.string.details_no_image),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        val titleModifier: Modifier = if (placeholder) {
            Modifier
                .constrainAs(title) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(image.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
        } else {
            Modifier
                .constrainAs(title) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(image.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        }

        Text(
            text = movie.title,
            modifier = titleModifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = MaterialTheme.typography.titleLarge
        )

        val overviewModifier: Modifier = if (placeholder) {
            Modifier
                .constrainAs(overview) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
        } else {
            Modifier
                .constrainAs(overview) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        }

        val releaseDateModifier: Modifier = if (placeholder) {
            Modifier
                .constrainAs(releaseDate) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(overview.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
        } else {
            Modifier
                .constrainAs(releaseDate) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(overview.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        }

        Text(
            text = movie.overview,
            modifier = overviewModifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            overflow = TextOverflow.Ellipsis,
            maxLines = 10,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = movieReleaseDate,
            modifier = releaseDateModifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@DevicePreviews
private fun DetailsContentPreview(
    @PreviewParameter(MoviePreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme {
        DetailsContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            movie = movie
        )
    }
}