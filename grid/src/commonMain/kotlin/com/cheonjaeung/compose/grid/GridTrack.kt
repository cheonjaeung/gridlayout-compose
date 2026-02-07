package com.cheonjaeung.compose.grid

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines the size of a track in [SimpleGridCells.Track].
 */
@Stable
sealed interface GridTrack {
    /**
     * A track with a fixed size.
     *
     * @param size The size of the track.
     */
    data class Fixed(val size: Dp) : GridTrack {
        init {
            require(size > 0.dp) { "Fixed size must be positive, but was $size" }
        }
    }

    /**
     * A track with a weighted size.
     * The size of the track is calculated by dividing the remaining space by the total weight.
     *
     * @param weight The weight of the track.
     */
    data class Weight(val weight: Float) : GridTrack {
        init {
            require(weight > 0f) { "Weight must be positive, but was $weight" }
        }
    }
}
