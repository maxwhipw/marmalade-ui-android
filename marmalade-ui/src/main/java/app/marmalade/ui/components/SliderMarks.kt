package app.marmalade.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import app.marmalade.ui.theme.depth

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
 * Slider colours for every slider in the app, stepped or not. Light mode's
 * default inactive track (secondaryContainer) is all but the page colour: on
 * a stepped slider the stop dots were the only sign of where the track ran,
 * and the effect editor's smooth sliders (#88) had nothing at all. It takes
 * the toast container instead, a quiet step darker, as dark mode's rich brown
 * track already is.
 */
@Composable
fun marmaladeSliderColors(): SliderColors = if (MaterialTheme.depth.isDark) {
    SliderDefaults.colors()
} else {
    SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer)
}
