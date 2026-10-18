package app.marmalade.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import app.marmalade.ui.theme.MarmaladeMotion
import app.marmalade.ui.theme.depth
import app.marmalade.ui.theme.rememberReducedMotion
import app.marmalade.ui.theme.softShadow

// =============================================================================
// The polished building blocks (#83): one card, one sheet, one menu, one
// chevron and one "working" mark, all spending the theme's depth and motion
// tokens, so every screen lifts and moves the same way.
// =============================================================================

/**
 * A card on the page: the scheme's card fill, a hairline edge and the soft
 * card shadow (agent-cards lab). Never an accent border; the only coloured
 * edge a card gets is the error red, through [border].
 *
 * [selected] washes the fill with toast (the lab's active row) and animates
 * the change; [selectedFill] overrides that wash where a screen needs a
 * stronger one. Clickable when [onClick] is set.
 */
@Composable
fun MarmaladeCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    selected: Boolean = false,
    selectedFill: Color? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val depth = MaterialTheme.depth
    val fill by animateColorAsState(
        // Opaque, so the shadow under the card never shows through the wash.
        targetValue = when {
            !selected -> depth.card
            selectedFill != null -> selectedFill
            else -> depth.selected.compositeOver(depth.card)
        },
        animationSpec = MarmaladeMotion.fast(),
        label = "cardFill",
    )
    val edge = border ?: BorderStroke(1.dp, depth.line2)
    // Set, not derived: Surface derives its ink by matching the fill to a
    // scheme role, and an animated or washed fill matches none, which left
    // card text in whatever quiet ink surrounded the card.
    val ink = MaterialTheme.colorScheme.onSurface
    val inner: @Composable () -> Unit = {
        Column(content = content)
    }
    val shadowed = modifier.softShadow(depth.cardShadow, shape)
    if (onClick != null) {
        Surface(
            onClick = onClick,
            enabled = enabled,
            modifier = shadowed,
            shape = shape,
            color = fill,
            contentColor = ink,
            border = edge,
            content = inner,
        )
    } else {
        Surface(
            modifier = shadowed,
            shape = shape,
            color = fill,
            contentColor = ink,
            border = edge,
            content = inner,
        )
    }
}

/**
 * The app's bottom sheet: the sheet step of the surface ramp, the warm
 * scrim, a quiet drag handle and no tonal tint.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarmaladeSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val depth = MaterialTheme.depth
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = depth.sheet,
        tonalElevation = 0.dp,
        scrimColor = depth.scrim,
        dragHandle = { BottomSheetDefaults.DragHandle(color = depth.ghost) },
        content = content,
    )
}

/** A dropdown menu on the raised step, with a hairline edge and the menu shadow. */
@Composable
fun MarmaladeMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit,
) {
    val depth = MaterialTheme.depth
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        offset = offset,
        properties = properties,
        shape = MaterialTheme.shapes.small,
        containerColor = depth.raised,
        tonalElevation = 0.dp,
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, depth.line2),
        content = content,
    )
}

/**
 * The angle for an expand chevron: [openDegrees] when [expanded], 0
 * otherwise, turning over the lab's 250 ms. Use with `Modifier.rotate`.
 */
@Composable
fun chevronAngle(expanded: Boolean, openDegrees: Float = 180f): Float {
    val angle by animateFloatAsState(
        targetValue = if (expanded) openDegrees else 0f,
        animationSpec = tween(MarmaladeMotion.TURN_MS, easing = MarmaladeMotion.Ease),
        label = "chevron",
    )
    return angle
}

/**
 * "Working": three dots that pulse in turn (the lab's 1.4 s live pulse).
 * Replaces the spinning ring, which Max rejected. Under reduced motion the
 * dots stand still. Like the ring, it tells TalkBack "in progress"
 * ([announce]); pass false where a live text line already says it.
 */
@Composable
fun WorkingDots(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary,
    dotSize: Dp = 8.dp,
    announce: Boolean = true,
) {
    val still = rememberReducedMotion()
    val pulse = rememberInfiniteTransition(label = "workingDots")
    Row(
        modifier = if (announce) modifier.progressSemantics() else modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSize * 0.75f),
    ) {
        repeat(3) { i ->
            val a by pulse.animateFloat(
                initialValue = 1f,
                targetValue = 0.3f,
                animationSpec = pulseSpec(i),
                label = "dot$i",
            )
            Box(
                Modifier
                    .size(dotSize)
                    .alpha(if (still) 1f else a)
                    .background(color, CircleShape),
            )
        }
    }
}

/** One live dot (the lab's running mark), for a "playing" or "speaking" state. */
@Composable
fun LiveDot(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary,
    dotSize: Dp = 7.dp,
) {
    val still = rememberReducedMotion()
    val pulse = rememberInfiniteTransition(label = "liveDot")
    val a by pulse.animateFloat(1f, 0.35f, pulseSpec(0), label = "live")
    Box(
        modifier
            .size(dotSize)
            .alpha(if (still) 1f else a)
            .background(color, CircleShape),
    )
}

private fun pulseSpec(index: Int): InfiniteRepeatableSpec<Float> = infiniteRepeatable(
    animation = tween(durationMillis = 700, easing = MarmaladeMotion.Ease),
    repeatMode = RepeatMode.Reverse,
    initialStartOffset = StartOffset(index * 230),
)
