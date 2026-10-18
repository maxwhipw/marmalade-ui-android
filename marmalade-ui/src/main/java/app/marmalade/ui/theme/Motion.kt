package app.marmalade.ui.theme

import android.content.Context
import android.provider.Settings
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

// =============================================================================
// Motion: one spec for the whole app (#83), the webui's (marmalade.css #259)
// and the agent-cards lab's. One easing, three durations:
//   fast 150 ms  colour and state changes (a selected row, a pressed card)
//   base 200 ms  fades, small reveals, menus
//   slow 320 ms  expand / collapse, a card rising into a list
//
// Reduced motion: Compose already scales every finite animation by the
// system's animator duration scale (Developer options, or Accessibility →
// "Remove animations", which sets it to 0), so these specs need nothing
// extra. Endless loops (a pulsing "speaking" dot) must check
// [rememberReducedMotion] and stand still instead.
// =============================================================================

object MarmaladeMotion {
    /** cubic-bezier(0.2, 0.8, 0.2, 1): a quick start that settles softly. */
    val Ease = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)

    const val FAST_MS = 150
    const val BASE_MS = 200
    const val SLOW_MS = 320

    /** A chevron turning as its section opens (the lab's 0.25 s). */
    const val TURN_MS = 250

    fun <T> fast(): FiniteAnimationSpec<T> = tween(FAST_MS, easing = Ease)
    fun <T> base(): FiniteAnimationSpec<T> = tween(BASE_MS, easing = Ease)
    fun <T> slow(): FiniteAnimationSpec<T> = tween(SLOW_MS, easing = Ease)

    /** A section opening: height 0 → full over 320 ms, fading in. */
    val expand: EnterTransition =
        expandVertically(animationSpec = tween(SLOW_MS, easing = Ease)) +
            fadeIn(animationSpec = tween(BASE_MS, easing = Ease))

    /** The reverse of [expand]. */
    val collapse: ExitTransition =
        shrinkVertically(animationSpec = tween(SLOW_MS, easing = Ease)) +
            fadeOut(animationSpec = tween(FAST_MS, easing = Ease))

    val fadeEnter: EnterTransition = fadeIn(animationSpec = tween(BASE_MS, easing = Ease))
    val fadeExit: ExitTransition = fadeOut(animationSpec = tween(FAST_MS, easing = Ease))
}

/**
 * The lab's "rise": 8 dp up and a fade, for something arriving (a card in a
 * list, a status line, a new step).
 */
@Composable
fun riseIn(durationMs: Int = MarmaladeMotion.SLOW_MS): EnterTransition {
    val rise = with(LocalDensity.current) { 8.dp.roundToPx() }
    return slideInVertically(
        animationSpec = tween<IntOffset>(durationMs, easing = MarmaladeMotion.Ease),
        initialOffsetY = { rise },
    ) + fadeIn(animationSpec = tween(durationMs, easing = MarmaladeMotion.Ease))
}

/** True when the system asks for no animation (animator duration scale 0). */
fun reducedMotion(context: Context): Boolean = isReducedMotion(
    Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f),
)

internal fun isReducedMotion(animatorDurationScale: Float): Boolean = animatorDurationScale == 0f

@Composable
fun rememberReducedMotion(): Boolean {
    val context = LocalContext.current
    return remember(context) { reducedMotion(context) }
}
