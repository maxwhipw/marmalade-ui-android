package app.marmalade.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * WCAG 2.2 AA where we promise it (#89): 4.5:1 for text, 3:1 for icons and
 * the edges of controls.
 *
 * Dark mode meets AA for MARMALADE and every curated preset. Light mode's
 * quiet text and icons do too ([DepthTest]), but its orange (Orange 500 is
 * 2.6:1 as text on cream, white on it 2.8:1) and its Stone 400 outline
 * (2.2-2.5:1) are knowingly below AA: Max's call, 2026-10-17. SYSTEM is
 * Material You, tone-based and device-specific, so it is not checked here.
 */
class ThemeContrastTest {

    private val curated = ThemePreset.entries - ThemePreset.SYSTEM

    private fun darkScheme(preset: ThemePreset): ColorScheme =
        if (preset == ThemePreset.MARMALADE) {
            MarmaladeDarkColors
        } else {
            buildPresetScheme(MarmaladeDarkColors, preset, isDark = true)
        }

    private fun surfaces(depth: MarmaladeDepth) = mapOf(
        "page" to depth.page,
        "card" to depth.card,
        "raised" to depth.raised,
        "sheet" to depth.sheet,
    )

    private fun assertAtLeast(min: Float, fg: Color, bg: Color, what: String) {
        val ratio = contrastRatio(fg, bg)
        assertTrue("$what: %.2f:1 < $min:1".format(ratio), ratio >= min)
    }

    @Test
    fun quietTextAndIconsReadOnEverySurfaceForEveryPreset() {
        val depths = curated.flatMap { preset ->
            val light = if (preset == ThemePreset.MARMALADE) {
                MarmaladeLightColors
            } else {
                buildPresetScheme(MarmaladeLightColors, preset, isDark = false)
            }
            LightDepth.entries.map { "$preset light $it" to lightDepth(light, it) } +
                ("$preset dark" to darkDepth(darkScheme(preset)))
        }
        for ((name, depth) in depths) {
            for ((surface, bg) in surfaces(depth)) {
                assertAtLeast(4.5f, depth.dim, bg, "$name dim on $surface")
                assertAtLeast(3f, depth.faint, bg, "$name faint on $surface")
            }
        }
    }

    @Test
    fun darkControlEdgesMeetThreeToOne() = curated.forEach { preset ->
        val scheme = darkScheme(preset)
        for ((surface, bg) in surfaces(darkDepth(scheme))) {
            assertAtLeast(3f, scheme.outline, bg, "$preset dark outline on $surface")
        }
    }

    @Test
    fun darkFilledButtonLabelsReadOnPrimary() = curated.forEach { preset ->
        val scheme = darkScheme(preset)
        assertAtLeast(4.5f, scheme.onPrimary, scheme.primary, "$preset dark onPrimary")
        assertAtLeast(4.5f, scheme.onPrimaryContainer, scheme.primaryContainer, "$preset dark onPrimaryContainer")
    }

    @Test
    fun darkPrimaryReadsAsTextOnEverySurface() = curated.forEach { preset ->
        val scheme = darkScheme(preset)
        for ((surface, bg) in surfaces(darkDepth(scheme))) {
            assertAtLeast(4.5f, scheme.primary, bg, "$preset dark primary text on $surface")
        }
    }

    @Test
    fun labelOnPicksTheStrongerOfWhiteAndInk() {
        val ink = Color(0xFF1C1917)
        assertEquals(ink, labelOn(Color(0xFF60A5FA)))         // Midnight dark: 6.9 vs white 2.5
        assertEquals(ink, labelOn(Color(0xFFF87171)))         // Berry dark: 6.3 vs 2.8
        assertEquals(Color.White, labelOn(Color(0xFF9A3412))) // Orange 800: 7.3 vs 2.4
        assertEquals(Color.White, labelOn(Color(0xFF2563EB)))
        assertEquals(ink, labelOn(Color(0xFFFED7AA)))
    }

    @Test
    fun contrastRatioMatchesTheWcagFormula() {
        assertEquals(21f, contrastRatio(Color.Black, Color.White), 0.01f)
        assertEquals(1f, contrastRatio(Color.White, Color.White), 0.001f)
        // Orange 500 on the cream page: the light orange left below AA.
        assertEquals(2.64f, contrastRatio(Color(0xFFF97316), Color(0xFFFFF7ED)), 0.01f)
    }
}
