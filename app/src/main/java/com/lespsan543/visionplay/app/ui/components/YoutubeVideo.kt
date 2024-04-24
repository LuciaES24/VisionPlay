package com.lespsan543.visionplay.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeVideo(id:String,
                 lifecycleOwner: LifecycleOwner,
                 width: Dp,
                 height: Dp
){

    AndroidView(
        factory = { cntx->
            YouTubePlayerView(cntx).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(id, 0f)
                    }
                })
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = width * 0.05f,
                end = width * 0.05f
            )
            .height(height * 0.25f)
    )
}