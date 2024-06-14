package com.superman.movieticket.ui.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R

private const val KEY = "VIDEO_VIEWER_KEY"

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoViewer(
    url: String, image: String,
    onFullscreenToggle: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    var playWhenReady by rememberSaveable {
        mutableStateOf(false)
    }
    // ExoPlayer setup
    val player = remember { ExoPlayer.Builder(context).build() }
    val playerView = remember {
        PlayerView(context).apply {
            this.player = player
            useController = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        }
    }

    DisposableEffect(
        key1 = player,
        effect = {
            onDispose {
                player.release()
            }
        }
    )

    // Handle fullscreen state
    var isFullscreen by rememberSaveable { mutableStateOf(false) }

    fun enterFullScreen() {
//        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        playerView.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        onFullscreenToggle(true)
        isFullscreen = true
    }

    fun exitFullScreen() {
//        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        playerView.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        onFullscreenToggle(false)
        isFullscreen = false
    }

    // Fullscreen button listener
    playerView.setFullscreenButtonClickListener { isFullscreenClicked ->
        if (isFullscreenClicked) {
            enterFullScreen()
        } else {
            exitFullScreen()
        }
    }
    playerView.keepScreenOn = true

    // Prepare and play video
    LaunchedEffect(player) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()

        player.playWhenReady = playWhenReady
    }

    // AndroidView to display the PlayerView
    val isPlay = remember {
        mutableStateOf(false)
    }
    if (isPlay.value == false) {
        AnimatedVisibility(visible = !isPlay.value) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (t, b) = createRefs()
                Image(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(t) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)

                        end.linkTo(parent.end)
                        start.linkTo(parent.start)

                    }
                    .height(200.dp), painter = rememberAsyncImagePainter(
                    model = image, error = painterResource(
                        id = R.drawable.error_img
                    )
                ), contentDescription = "", contentScale = ContentScale.FillBounds)
                Icon(
                    Icons.Default.PlayCircle,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                        .constrainAs(b) {
                            top.linkTo(t.top)
                            bottom.linkTo(t.bottom)

                            end.linkTo(t.end)
                            start.linkTo(t.start)

                        }
                        .clickable {
                            playWhenReady = true
                            isPlay.value = !isPlay.value
                        },
                    tint = Color.White
                )
            }
        }

    } else {
        AnimatedVisibility(visible = true) {
            AndroidView(
                factory = { playerView },
                modifier = if(isFullscreen) Modifier.fillMaxSize() else Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .height(200.dp),
            )
        }
    }


}



