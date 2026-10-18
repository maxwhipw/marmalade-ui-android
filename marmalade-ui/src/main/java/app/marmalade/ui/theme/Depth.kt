package app.marmalade.ui.theme

import android.graphics.Paint
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// =============================================================================
// Depth: the layer ramp, ink ramp and soft shadows (#83).
//
// Ported from the agent-cards polish bar (design-lab ac.css / conversation-flow
// app.css) and the shipped webui tokens (packages/webui marmalade.css, #259).
// Depth comes from layered surfaces and shadow, never from a new hue: the
// surfaces are the scheme's own surfaceContainer roles, and every line and
// quiet text colour is the scheme's ink (onSurface) at a fixed strength, so
// the same token holds on any surface and in both modes.
// =============================================================================

/**
 * A CSS-style box shadow: `0 [offsetY] [blur] [spread] [color]`. The values
 * are the lab's own, read as dp.
 */
@Immutable
data class SoftShadow(
    val offsetY: Dp,
    val blur: Dp,
    val spread: Dp,
    val color: Color,
)

/**
 * Depth tokens for one scheme. Read with [MaterialTheme.depth].
 *
 * Text ramp (webui #259 rule): [dim] is the quietest *text* (≥ 4.5:1 on
 * every surface in the ramp); [faint] is for glyphs and control icons only
 * (≥ 3:1); [ghost] is decoration (drag handles, unticked marks).
 */
@Immutable
data class MarmaladeDepth(
    /** The page is dark: the cue for the brand's dark-mode swaps. */
    val isDark: Boolean,
    val dim: Color,
    val faint: Color,
    val ghost: Color,
    /** Hairline between rows and around raised surfaces. */
    val line: Color,
    /** The stronger hairline: card and menu borders. */
    val line2: Color,
    /** Pressed / hovered row wash. */
    val hover: Color,
    /** The active row's wash (a toast tint, not a border). */
    val selected: Color,
    /** Surfaces, low to high: page → card → raised (menus, the voice row). */
    val page: Color,
    val card: Color,
    val raised: Color,
    val sheet: Color,
    val scrim: Color,
    /** Null where the mode's pick is flat (no shadow). */
    val cardShadow: SoftShadow?,
    val menuShadow: SoftShadow?,
    val barShadow: SoftShadow?,
)

/**
 * How light mode shows depth: the labs only draw dark mode, so this is Max's
 * pick (#83 lab). [FLAT] is the cream ladder with hairlines and no shadow;
 * [SOFT] adds the webui's softer light shadow; [LIFTED] also lifts cards to
 * the near-white step so they sit above the cream page.
 */
enum class LightDepth { FLAT, SOFT, LIFTED }

/** The shipped light-mode pick. */
val LIGHT_DEPTH = LightDepth.SOFT

/** Dark: Stone 800 cards on stone deep; shadows from the agent-cards lab. */
internal fun darkDepth(scheme: ColorScheme): MarmaladeDepth {
    val ink = scheme.onSurface
    return MarmaladeDepth(
        isDark = true,
        dim = ink.copy(alpha = 0.64f),
        faint = ink.copy(alpha = 0.40f),
        ghost = ink.copy(alpha = 0.22f),
        line = ink.copy(alpha = 0.07f),
        line2 = ink.copy(alpha = 0.11f),
        hover = ink.copy(alpha = 0.05f),
        selected = MarmaladeToast.copy(alpha = 0.10f),
        page = scheme.background,
        card = scheme.surfaceContainerHigh,
        raised = scheme.surfaceContainerHighest,
        sheet = scheme.surfaceContainerLow,
        scrim = Color(0xFF0C0B0A).copy(alpha = 0.55f),
        // ac.css .ac-card / .ac-more-m / .ac-strip
        cardShadow = SoftShadow(16.dp, 40.dp, (-24).dp, Color.Black.copy(alpha = 0.9f)),
        menuShadow = SoftShadow(18.dp, 40.dp, (-12).dp, Color.Black.copy(alpha = 0.8f)),
        barShadow = SoftShadow((-10).dp, 24.dp, (-14).dp, Color.Black.copy(alpha = 0.9f)),
    )
}

/**
 * Light: webui's light ramp (ink-3 at 52%, ink-4 at 24%, hairlines 8 / 13%)
 * and its softer, warm shadow (the --shadow-pop ink-dim tint, #7C2D12).
 */
internal fun lightDepth(scheme: ColorScheme, pick: LightDepth = LIGHT_DEPTH): MarmaladeDepth {
    val ink = scheme.onSurface
    val warm = MarmaladeToastInk
    val shadowed = pick != LightDepth.FLAT
    return MarmaladeDepth(
        isDark = false,
        dim = ink.copy(alpha = 0.64f),
        faint = ink.copy(alpha = 0.52f),
        ghost = ink.copy(alpha = 0.24f),
        line = ink.copy(alpha = 0.08f),
        line2 = ink.copy(alpha = 0.13f),
        hover = ink.copy(alpha = 0.05f),
        selected = MarmaladeToast.copy(alpha = 0.55f),
        page = scheme.background,
        card = if (pick == LightDepth.LIFTED) scheme.surfaceContainerLow else scheme.surfaceContainerHigh,
        raised = scheme.surfaceContainerLowest,
        sheet = scheme.surfaceContainerLow,
        scrim = Color(0xFF1C1917).copy(alpha = 0.32f),
        cardShadow = if (shadowed) SoftShadow(10.dp, 28.dp, (-16).dp, warm.copy(alpha = 0.28f)) else null,
        menuShadow = SoftShadow(12.dp, 32.dp, (-8).dp, warm.copy(alpha = 0.18f)),
        barShadow = if (shadowed) SoftShadow((-8).dp, 24.dp, (-12).dp, warm.copy(alpha = 0.16f)) else null,
    )
}

internal val LocalMarmaladeDepth = staticCompositionLocalOf<MarmaladeDepth?> { null }

/**
 * The depth tokens for the current theme. Outside [MarmaladeTtsTheme]
 * (a bare preview) they are derived from whatever scheme is in scope.
 */
val MaterialTheme.depth: MarmaladeDepth
    @Composable
    @ReadOnlyComposable
    get() = LocalMarmaladeDepth.current ?: run {
        val scheme = colorScheme
        if (scheme.background.luminanceIsDark()) darkDepth(scheme) else lightDepth(scheme)
    }

private fun Color.luminanceIsDark(): Boolean = (0.2126f * red + 0.7152f * green + 0.0722f * blue) < 0.5f

/**
 * The Android shadow-layer radius that matches a CSS blur. CSS blurs with a
 * Gaussian of sigma = blur / 2; Skia turns a shadow-layer radius r into
 * sigma = 0.57735·r + 0.5.
 */
internal fun shadowLayerRadius(blurPx: Float): Float =
    ((blurPx / 2f - 0.5f) / 0.57735f).coerceAtLeast(0f)

/**
 * Draws [shadow] behind the content, shaped like [shape]. A no-op for null.
 *
 * Compose 1.7 has no blurred, offset or spread shadow (`Modifier.shadow` is
 * the platform's light-source elevation), so this draws the lab's CSS shadow
 * with a framework shadow layer (hardware-accelerated for any shape on
 * API 28+, the app's minSdk). The shape itself is drawn transparent; only
 * its shadow shows, and the content drawn on top hides the part under it.
 */
fun Modifier.softShadow(shadow: SoftShadow?, shape: Shape): Modifier {
    if (shadow == null) return this
    return drawWithCache {
        val spread = shadow.spread.toPx()
        val w = size.width + 2 * spread
        val h = size.height + 2 * spread
        if (w <= 0f || h <= 0f) return@drawWithCache onDrawBehind {}
        val outline = shape.createOutline(Size(w, h), layoutDirection, this)
        val path = Path().apply {
            when (outline) {
                is Outline.Rectangle -> addRect(outline.rect)
                is Outline.Rounded -> addRoundRect(outline.roundRect)
                is Outline.Generic -> addPath(outline.path)
            }
            translate(Offset(-spread, -spread))
        }.asAndroidPath()
        // A shadow layer takes the paint's alpha when its colour is opaque;
        // keep it translucent so the transparent paint still casts it.
        val color = shadow.color.copy(alpha = shadow.color.alpha.coerceAtMost(0.99f))
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = android.graphics.Color.TRANSPARENT
            setShadowLayer(shadowLayerRadius(shadow.blur.toPx()), 0f, shadow.offsetY.toPx(), color.toArgb())
        }
        onDrawBehind {
            drawIntoCanvas { it.nativeCanvas.drawPath(path, paint) }
        }
    }
}
