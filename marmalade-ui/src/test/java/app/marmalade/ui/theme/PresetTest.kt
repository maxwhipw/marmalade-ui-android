package app.marmalade.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PresetTest {

    @Test
    fun presetsSwapOnlyThePrimaryFamily() {
        for (dark in listOf(false, true)) {
            val base = if (dark) MarmaladeDarkColors else MarmaladeLightColors
            val scheme = buildPresetScheme(base, ThemePreset.MIDNIGHT, dark)
            assertEquals(if (dark) Color(0xFF60A5FA) else Color(0xFF2563EB), scheme.primary)
            assertEquals(scheme.primary, scheme.surfaceTint)
            // Surfaces, text and the secondary/tertiary roles stay the base's.
            assertEquals(base.surface, scheme.surface)
            assertEquals(base.onSurface, scheme.onSurface)
            assertEquals(base.onSurfaceVariant, scheme.onSurfaceVariant)
            assertEquals(base.secondary, scheme.secondary)
            assertEquals(base.tertiary, scheme.tertiary)
        }
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
