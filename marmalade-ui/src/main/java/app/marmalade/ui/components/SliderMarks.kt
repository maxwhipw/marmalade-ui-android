package app.marmalade.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable

// -----------------------------------------------------------------------------
// The track of a stepped slider (#84). Material draws a dot at every detent,
// which on the 15-stop speed slider read as a dotted line ("the sliders with
// the dots look weird", Max, #83 lab); he picked a clean track with no marks
// over one mark at 1× or faint dots. The stops still snap and TalkBack still
// reads each value; each stop the thumb reaches gives a light haptic tick.
// -----------------------------------------------------------------------------

@Composable
fun SteppedSliderTrack(state: SliderState) {
    SliderDefaults.Track(sliderState = state, colors = marmaladeSliderColors(), drawTick = { _, _ -> })
}

/**
 * Slider colours for every slider in the app, stepped or not. The inactive
 * track is the preset's container in both modes. Material's default
 * (secondaryContainer) is all but the page colour in light mode: on a
 * stepped slider the stop dots were the only sign of where the track ran,
 * and the effect editor's smooth sliders (#88) had nothing at all. In a dark
 * preset it is the pill tone, the active track's own colour; Marmalade's
 * dark track is Orange 800 (3.2:1 under the Orange 400 fill).
 */
@Composable
fun marmaladeSliderColors(): SliderColors =
    SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer)
