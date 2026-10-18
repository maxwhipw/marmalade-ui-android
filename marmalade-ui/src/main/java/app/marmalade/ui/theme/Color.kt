package app.marmalade.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance

// =============================================================================
// Marmalade Orange Palette
// Matches the palette in marmalade-android for visual consistency.
// =============================================================================

val MarmaladeLightColors = lightColorScheme(
    primary = Color(0xFFF97316),            // Orange 500
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFED7AA),    // Orange 200
    onPrimaryContainer = Color(0xFF4A1808),  // Rich dark brown
    secondary = Color(0xFFEA580C),           // Orange 600
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF7ED),  // Orange 50
    onSecondaryContainer = Color(0xFF7C2D12),
    tertiary = Color(0xFFC2410C),            // Orange 700
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFED7AA),
    onTertiaryContainer = Color(0xFF7C2D12),
    surface = Color(0xFFFFF7ED),             // Orange 50
    onSurface = Color(0xFF1C1917),           // Stone 900
    surfaceVariant = Color(0xFFFFEDD5),      // Orange 100
    // Quiet text and hairlines are ink at a fixed strength (#83, webui
    // #259): the same token reads right on the page, a card or a sheet.
    // 64% ink is the quietest text (≥ 4.5:1 on every surface); dividers
    // and outlined borders are 13% ink. MarmaladeDepth carries the rest.
    onSurfaceVariant = Color(0xA31C1917),    // Stone 900 at 64%
    // The surfaceContainer family MUST be set explicitly. Material 3 fills
    // any role left unspecified from its *baseline* palette, which is
    // purple-tinted — and Card reads surfaceContainerLow, NavigationBar
    // reads surfaceContainer, ModalBottomSheet reads surfaceContainerLow.
    // Omitting these is why alias cards and the bottom bar rendered lavender
    // on a cream page for several releases. Ladder runs cream → toast as
    // elevation rises, so a card lifts off the page without a shadow.
    // Cream is the whole ladder. Material's filled Card reads
    // surfaceContainerHighest and NavigationBar reads surfaceContainer, so
    // making the top of the ladder Orange 200 (toast) turned every card into
    // a saturated slab that fought the page instead of sitting on it. Toast
    // is a *chip* colour, not a surface — see the brand scheme's "orange is
    // precious" rule.
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFFBF6),
    surfaceContainer = Color(0xFFFFEDD5),        // Orange 100 — nav bar
    surfaceContainerHigh = Color(0xFFFFEDD5),
    surfaceContainerHighest = Color(0xFFFFEDD5), // Orange 100 — cards
    // Bright/Dim too (#83): unset, they fall back to the lavender baseline.
    // Values match marmalade-android's shipped scheme.
    surfaceBright = Color(0xFFFFF7ED),
    surfaceDim = Color(0xFFEFE4D6),
    background = Color(0xFFFFF7ED),          // Orange 50
    onBackground = Color(0xFF1C1917),        // Stone 900
    error = Color(0xFFDC2626),               // Red 600
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),
    // Light's outline and orange are knowingly below WCAG AA (Stone 400 is
    // 2.2-2.5:1 on cream; Orange 500 2.6:1 as text, white on it 2.8:1): Max's
    // call, 2026-10-17 (#89). Dark is the mode that meets AA.
    outline = Color(0xFFA8A29E),             // Stone 400
    outlineVariant = Color(0x211C1917),      // Stone 900 at 13%
    inverseSurface = Color(0xFF1C1917),
    inverseOnSurface = Color(0xFFFAFAF9),
    inversePrimary = Color(0xFFFB923C),      // Orange 400
    surfaceTint = Color(0xFFF97316),
)

val MarmaladeDarkColors = darkColorScheme(
    primary = Color(0xFFFB923C),             // Orange 400
    onPrimary = Color(0xFF1C1917),           // Stone 900
    primaryContainer = Color(0xFF9A3412),    // Orange 800
    onPrimaryContainer = Color(0xFFFFFBF7),
    secondary = Color(0xFFF97316),           // Orange 500
    onSecondary = Color(0xFF1C1917),
    secondaryContainer = Color(0xFF7C2D12),  // Orange 900
    onSecondaryContainer = Color(0xFFFED7AA),
    tertiary = Color(0xFFFDBA74),            // Orange 300
    onTertiary = Color(0xFF1C1917),
    tertiaryContainer = Color(0xFF9A3412),
    onTertiaryContainer = Color(0xFFFED7AA),
    surface = Color(0xFF1C1917),             // Stone deep — bars sit on the page
    onSurface = Color(0xFFFAFAF9),           // Stone 50
    surfaceVariant = Color(0xFF292524),      // Stone 800
    onSurfaceVariant = Color(0xA3FAFAF9),    // Stone 50 at 64% (see light)
    // Same reason as the light scheme — see the note there. Warm-neutral
    // stone ladder rather than Material's baseline, which reads violet in
    // dark mode too. Per the brand scheme, dark mode gets no orange
    // surfaces: the warmth comes from the cream text on stone.
    // The ladder is the agent-cards lab's (#83): stone deep page → Stone 800
    // cards → a step above for menus; sheets sit just above the page.
    surfaceContainerLowest = Color(0xFF151311),
    surfaceContainerLow = Color(0xFF221E1C),     // sheets
    surfaceContainer = Color(0xFF24201E),        // nav bar
    surfaceContainerHigh = Color(0xFF292524),    // Stone 800 — cards
    surfaceContainerHighest = Color(0xFF2F2A28), // menus, raised rows
    surfaceBright = Color(0xFF383430),
    surfaceDim = Color(0xFF1C1917),
    background = Color(0xFF1C1917),          // Stone deep
    onBackground = Color(0xFFFAFAF9),        // Stone 50
    error = Color(0xFFEF4444),
    onError = Color(0xFF1C1917),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA),
    // Stone 500 nudged a hair lighter (#78716C was 2.95:1 on menus): the
    // edge of an unticked box or a text field needs 3:1 on every surface (#89).
    outline = Color(0xFF7C756F),
    outlineVariant = Color(0x1CFAFAF9),      // Stone 50 at 11%
    inverseSurface = Color(0xFFFAFAF9),
    inverseOnSurface = Color(0xFF1C1917),
    inversePrimary = Color(0xFFC2410C),      // Orange 700
    surfaceTint = Color(0xFFFB923C),
)

/**
 * Toast and its ink (brand v0): the default button, the selected chip and
 * the dark-mode stand-in for orange (an active tab, a primary badge), in
 * every preset. Never a large dark-mode surface.
 */
val MarmaladeToast = Color(0xFFFED7AA)               // Orange 200
val MarmaladeToastInk = Color(0xFF7C2D12)            // Orange 900

// =============================================================================
// Theme Presets
// =============================================================================

/**
 * Curated theme presets. [SYSTEM] uses Material You dynamic colors from the
 * device wallpaper on Android 12+. [MARMALADE] uses the hand-tuned orange
 * palette above. Other presets override the primary color family while
 * keeping the warm-stone surface tones from the Marmalade base scheme.
 *
 * Mirrors the marmalade-android ThemePreset enum so the two apps stay in
 * lockstep visually. Chat-specific tokens are intentionally absent — the
 * apps using this library don't need user-bubble / avatar colors.
 */
enum class ThemePreset(
    val displayName: String,
    val lightPrimary: Color,
    val darkPrimary: Color,
    val lightPrimaryContainer: Color,
    val darkPrimaryContainer: Color,
) {
    SYSTEM("System", Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified),
    MARMALADE("Marmalade", Color(0xFFF97316), Color(0xFFFB923C), Color(0xFFFED7AA), Color(0xFF9A3412)),
    MIDNIGHT("Midnight", Color(0xFF2563EB), Color(0xFF60A5FA), Color(0xFFDBEAFE), Color(0xFF1E3A5F)),
    FOREST("Forest", Color(0xFF16A34A), Color(0xFF4ADE80), Color(0xFFDCFCE7), Color(0xFF14532D)),
    BERRY("Berry", Color(0xFFDC2626), Color(0xFFF87171), Color(0xFFFEE2E2), Color(0xFF7F1D1D)),
    ;

    companion object {
        /** Tolerant parser used when reading the persisted preset string. */
        fun fromString(name: String): ThemePreset =
            entries.find { it.name.equals(name, ignoreCase = true) } ?: SYSTEM
    }
}

/** WCAG 2 contrast ratio of [fg] (composited over [bg] if translucent) on [bg]. */
fun contrastRatio(fg: Color, bg: Color): Float {
    val a = fg.compositeOver(bg).luminance() + 0.05f
    val b = bg.luminance() + 0.05f
    return maxOf(a, b) / minOf(a, b)
}

/**
 * White or Stone 900, whichever reads better on [fill]. Not a luminance
 * 0.5 cut: the two tie near luminance 0.18, so that cut put white on
 * Midnight's dark primary (#60A5FA) at 2.5:1 where ink gives 6.9:1 (#89).
 */
fun labelOn(fill: Color): Color {
    val ink = Color(0xFF1C1917)
    return if (contrastRatio(Color.White, fill) >= contrastRatio(ink, fill)) Color.White else ink
}

/**
 * Build a color scheme for a curated preset by recolouring the base scheme's
 * accent roles (the primary, secondary and tertiary families) in the
 * preset's colour. Surface / background tones and text come from the base,
 * so every preset keeps the same warm-stone look, and no orange is left in a
 * blue, green or red theme.
 */
fun buildPresetScheme(base: ColorScheme, preset: ThemePreset, isDark: Boolean): ColorScheme {
    val primary = if (isDark) preset.darkPrimary else preset.lightPrimary
    val primaryContainer = if (isDark) preset.darkPrimaryContainer else preset.lightPrimaryContainer
    // Dark takes the stronger label (white on Midnight's and Berry's dark
    // primaries was 2.5-2.8:1). Light keeps its luminance cut, which puts
    // white on Forest at 3.3:1: below AA by Max's call (#89, 2026-10-17).
    val onPrimary = when {
        isDark -> labelOn(primary)
        primary.luminance() < 0.5f -> Color.White
        else -> Color(0xFF1C1917)
    }
    // The pill: the selected tab's indicator, a selected chip or segment
    // (secondaryContainer). Light: the preset's pale tone on the cream bar, as
    // toast is Marmalade's. Dark: its light tone, 5.8-9.3:1 on the bar (its
    // dark tones are 1.4-1.8:1 there, Marmalade's Orange 900 is 1.7).
    val pill = if (isDark) preset.darkPrimary else preset.lightPrimaryContainer
    return base.copy(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = labelOn(primaryContainer),
        // Left as the base's, these kept Marmalade's orange in the other
        // presets: the pill above, WorkingDots and LiveDot (tertiary), and
        // Material's secondary text and icons.
        secondary = primary,
        onSecondary = onPrimary,
        secondaryContainer = pill,
        onSecondaryContainer = labelOn(pill),
        tertiary = primary,
        onTertiary = onPrimary,
        tertiaryContainer = primaryContainer,
        onTertiaryContainer = labelOn(primaryContainer),
        surfaceTint = primary,
        inversePrimary = if (isDark) preset.lightPrimary else preset.darkPrimary,
    )
}
