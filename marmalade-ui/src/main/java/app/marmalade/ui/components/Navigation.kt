package app.marmalade.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

/**
 * A bottom bar tab's label (a `NavigationBarItem`'s `label`): one line,
 * ellipsized when it doesn't fit, and bold on the selected tab so the
 * selection reads in the label as well as the pill. [style] defaults to the
 * bar's labelMedium; an app that scales it (say, capped for large font
 * sizes) passes its own and still gets the bold.
 */
@Composable
fun MarmaladeNavigationBarLabel(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelMedium,
) {
    Text(
        text = text,
        modifier = modifier,
        style = marmaladeNavigationBarLabelStyle(style, selected),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

/** [style] for a tab label: bold when [selected], unchanged otherwise. */
fun marmaladeNavigationBarLabelStyle(style: TextStyle, selected: Boolean): TextStyle =
    if (selected) style.copy(fontWeight = FontWeight.Bold) else style
