package app.marmalade.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import app.marmalade.ui.theme.MarmaladeToast
import app.marmalade.ui.theme.MarmaladeToastInk
import app.marmalade.ui.theme.depth

/**
 * Colours for a bottom bar's tabs: the selected tab's pill stands off the
 * bar. On the Marmalade palette ([marmaladePalette]: Marmalade, or an app
 * preset built on it) the pill is toast with its ink in both modes. Other
 * schemes: their own pale secondaryContainer in light mode; in dark mode
 * primary, 5.8-9.3:1 on the bar, where the dark secondaryContainer is
 * 1.4-1.8:1. The label sits under the pill, on the bar, in onSurface.
 */
@Composable
fun marmaladeNavigationBarItemColors(marmaladePalette: Boolean): NavigationBarItemColors {
    val scheme = MaterialTheme.colorScheme
    return when {
        marmaladePalette -> NavigationBarItemDefaults.colors(
            indicatorColor = MarmaladeToast,
            selectedIconColor = MarmaladeToastInk,
            selectedTextColor = scheme.onSurface,
        )
        MaterialTheme.depth.isDark -> NavigationBarItemDefaults.colors(
            indicatorColor = scheme.primary,
            selectedIconColor = scheme.onPrimary,
            selectedTextColor = scheme.onSurface,
        )
        else -> NavigationBarItemDefaults.colors(selectedTextColor = scheme.onSurface)
    }
}
