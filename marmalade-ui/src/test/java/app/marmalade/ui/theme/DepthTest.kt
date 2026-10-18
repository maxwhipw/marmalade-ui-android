package app.marmalade.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DepthTest {

    private fun surfaceRoles(s: ColorScheme) = mapOf(
        "surfaceContainerLowest" to s.surfaceContainerLowest,
        "surfaceContainerLow" to s.surfaceContainerLow,
        "surfaceContainer" to s.surfaceContainer,
        "surfaceContainerHigh" to s.surfaceContainerHigh,
        "surfaceContainerHighest" to s.surfaceContainerHighest,
        "surfaceBright" to s.surfaceBright,
        "surfaceDim" to s.surfaceDim,
    )

    @Test
    fun everySurfaceRoleIsSetInBothSchemes() {
        // An unset role falls back to Material's baseline, which leans
        // lavender (more blue than red). Ours are warm stone and cream.
        assertTrue(surfaceRoles(lightColorScheme()).values.any { it.blue > it.red })
        for (scheme in listOf(MarmaladeLightColors, MarmaladeDarkColors)) {
            surfaceRoles(scheme).forEach { (role, color) ->
                assertTrue("$role leans lavender: unset?", color.red >= color.blue)
            }
        }
    }

    private fun checkRamp(depth: MarmaladeDepth) {
        for (bg in listOf(depth.page, depth.card, depth.raised, depth.sheet)) {
            // dim is the quietest text: WCAG AA for body text.
            assertTrue("dim on $bg", contrastRatio(depth.dim, bg) >= 4.5f)
            // faint is for glyphs and icons: WCAG non-text 3:1.
            assertTrue("faint on $bg", contrastRatio(depth.faint, bg) >= 3f)
        }
    }

    @Test
    fun darkTextRampIsReadableOnEverySurface() = checkRamp(darkDepth(MarmaladeDarkColors))

    @Test
    fun lightTextRampIsReadableOnEverySurfaceForEveryPick() =
        LightDepth.entries.forEach { checkRamp(lightDepth(MarmaladeLightColors, it)) }

    @Test
    fun theSchemesQuietTextIsTheDimStep() {
        // Material components read onSurfaceVariant; it is the ramp's dim.
        assertEquals(darkDepth(MarmaladeDarkColors).dim, MarmaladeDarkColors.onSurfaceVariant)
        assertEquals(lightDepth(MarmaladeLightColors).dim, MarmaladeLightColors.onSurfaceVariant)
    }

    @Test
    fun flatLightPickHasNoCardShadow() {
        assertNull(lightDepth(MarmaladeLightColors, LightDepth.FLAT).cardShadow)
        assertTrue(lightDepth(MarmaladeLightColors, LightDepth.SOFT).cardShadow != null)
    }

    @Test
    fun cssBlurMapsToTheShadowLayerRadius() {
        // CSS sigma = 20 for a 40px blur; Skia sigma = 0.57735 r + 0.5.
        val r = shadowLayerRadius(40f)
        assertEquals(20f, 0.57735f * r + 0.5f, 1e-3f)
        assertEquals(0f, shadowLayerRadius(0f), 0f)
    }

    @Test
    fun reducedMotionIsAnAnimatorScaleOfZero() {
        assertTrue(isReducedMotion(0f))
        assertFalse(isReducedMotion(1f))
        assertFalse(isReducedMotion(0.5f))
    }
}
