package app.marmalade.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PresetTest {

    @Test
    fun presetsRecolourTheAccentRolesAndKeepTheSurfaces() {
        for (dark in listOf(false, true)) {
            val base = if (dark) MarmaladeDarkColors else MarmaladeLightColors
            val scheme = buildPresetScheme(base, ThemePreset.MIDNIGHT, dark)
            assertEquals(if (dark) Color(0xFF60A5FA) else Color(0xFF2563EB), scheme.primary)
            assertEquals(scheme.primary, scheme.surfaceTint)
            assertEquals(scheme.primary, scheme.secondary)
            assertEquals(scheme.primary, scheme.tertiary)
            assertEquals(if (dark) Color(0xFF1E3A5F) else Color(0xFFDBEAFE), scheme.secondaryContainer)
            assertEquals(scheme.primaryContainer, scheme.tertiaryContainer)
            // Surfaces and text stay the base's.
            assertEquals(base.surface, scheme.surface)
            assertEquals(base.surfaceContainer, scheme.surfaceContainer)
            assertEquals(base.onSurface, scheme.onSurface)
            assertEquals(base.onSurfaceVariant, scheme.onSurfaceVariant)
            assertEquals(base.error, scheme.error)
        }
    }

    /**
     * The roles that follow a preset carry none of Marmalade's orange in the
     * blue, green and red presets (#87 review: the pill, the working dots and
     * a dark slider's track were left orange).
     */
    @Test
    fun noOrangeIsLeftInTheOtherPresets() {
        for (preset in listOf(ThemePreset.MIDNIGHT, ThemePreset.FOREST, ThemePreset.BERRY)) {
            for (dark in listOf(false, true)) {
                val s = buildPresetScheme(if (dark) MarmaladeDarkColors else MarmaladeLightColors, preset, dark)
                val roles = mapOf(
                    "primary" to s.primary, "primaryContainer" to s.primaryContainer,
                    "secondary" to s.secondary, "secondaryContainer" to s.secondaryContainer,
                    "tertiary" to s.tertiary, "tertiaryContainer" to s.tertiaryContainer,
                    "surfaceTint" to s.surfaceTint, "inversePrimary" to s.inversePrimary,
                )
                for ((role, color) in roles) {
                    assertFalse("$preset dark=$dark $role is orange: $color", color.isOrange())
                }
            }
        }
    }

    /**
     * The selected tab's pill, as marmaladeNavigationBarItemColors picks it,
     * stands off the nav bar. Dark: 3:1 for every preset (toast on the
     * Marmalade palette, primary elsewhere). Light: no pale pill reaches 3:1
     * on the cream bar (toast is 1.2:1), so a preset's pill is its own pale
     * tone, never the bar's cream.
     */
    @Test
    fun theSelectedTabPillStandsOffTheBar() {
        val bar = MarmaladeDarkColors.surfaceContainer
        assertTrue(contrastRatio(MarmaladeToast, bar) >= 3f)
        for (preset in listOf(ThemePreset.MIDNIGHT, ThemePreset.FOREST, ThemePreset.BERRY)) {
            val dark = buildPresetScheme(MarmaladeDarkColors, preset, isDark = true)
            val ratio = contrastRatio(dark.primary, dark.surfaceContainer)
            assertTrue("$preset dark pill on the bar: $ratio", ratio >= 3f)
            assertTrue("$preset dark pill icon", contrastRatio(dark.onPrimary, dark.primary) >= 3f)
            val light = buildPresetScheme(MarmaladeLightColors, preset, isDark = false)
            assertEquals(preset.lightPrimaryContainer, light.secondaryContainer)
            assertTrue("$preset light pill icon", contrastRatio(light.onSecondaryContainer, light.secondaryContainer) >= 3f)
        }
    }

    /**
     * A progress bar left on Material's default track (secondaryContainer,
     * under a primary bar) shows how far it has got: 3:1 between the two in
     * every preset and both modes (#11: a dark track the bar's own colour
     * looked full). Marmalade's light mode is its base scheme's, orange 500
     * on Orange 50 at 2.6:1, with light orange's other knowing shortfalls
     * (Max's call, #89).
     */
    @Test
    fun aDefaultTrackProgressBarStaysVisible() {
        for (preset in ThemePreset.entries - ThemePreset.SYSTEM) {
            for (dark in listOf(false, true)) {
                val base = if (dark) MarmaladeDarkColors else MarmaladeLightColors
                val s = if (preset == ThemePreset.MARMALADE) base else buildPresetScheme(base, preset, dark)
                val ratio = contrastRatio(s.primary, s.secondaryContainer)
                val min = if (preset == ThemePreset.MARMALADE && !dark) 2.6f else 3f
                assertTrue("$preset dark=$dark bar on its track: $ratio", ratio >= min)
            }
        }
    }

    /** A slider's unfilled track (the preset's container) stays apart from its fill, 3:1 in dark mode. */
    @Test
    fun theDarkSliderTrackStandsApartFromItsFill() {
        for (preset in ThemePreset.entries - ThemePreset.SYSTEM) {
            val s = if (preset == ThemePreset.MARMALADE) {
                MarmaladeDarkColors
            } else {
                buildPresetScheme(MarmaladeDarkColors, preset, isDark = true)
            }
            val ratio = contrastRatio(s.primary, s.primaryContainer)
            assertTrue("$preset: $ratio", ratio >= 3f)
        }
    }

    /** Orange: a saturated hue from 15 to 45 degrees (red is 0, amber 45). */
    private fun Color.isOrange(): Boolean {
        val max = maxOf(red, green, blue)
        val min = minOf(red, green, blue)
        if (max - min < 0.05f) return false // grey or near it (Orange 50 is 0.07)
        val hue = when (max) {
            red -> 60f * (((green - blue) / (max - min)) % 6f)
            green -> 60f * ((blue - red) / (max - min) + 2f)
            else -> 60f * ((red - green) / (max - min) + 4f)
        }.let { if (it < 0f) it + 360f else it }
        return hue in 15f..45f
    }

    @Test
    fun presetNamesParseWithSystemAsTheDefault() {
        assertEquals(ThemePreset.FOREST, ThemePreset.fromString("forest"))
        assertEquals(ThemePreset.SYSTEM, ThemePreset.fromString("nope"))
    }

    @Test
    fun themeModeResolves() {
        assertFalse(resolveThemeIsDark("light", isSystemDark = true))
        assertTrue(resolveThemeIsDark("dark", isSystemDark = false))
        assertTrue(resolveThemeIsDark("system", isSystemDark = true))
    }
}
