package app.marmalade.ui.components

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationTest {

    @Test
    fun `the selected tab's label is bold, the others keep their style`() {
        val label = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)
        assertEquals(label.copy(fontWeight = FontWeight.Bold), marmaladeNavigationBarLabelStyle(label, selected = true))
        assertEquals(label, marmaladeNavigationBarLabelStyle(label, selected = false))
    }
}
