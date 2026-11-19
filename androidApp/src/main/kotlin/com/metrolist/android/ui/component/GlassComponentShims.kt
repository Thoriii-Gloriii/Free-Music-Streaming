package com.metrolist.android.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon as M3Icon
import androidx.compose.material3.Button as M3Button
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.reservedIconActionCanvas(
    shape: Shape,
    interactionSource: MutableInteractionSource?,
    indication: Indication?,
    enabled: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    role: Role? = null,
    placementModifier: Modifier = Modifier,
): Modifier = placementModifier.clip(shape)
    .combinedClickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        onClick = onClick,
        onLongClick = onLongClick,
        role = role
    )
    .then(this)
    .clip(shape)

fun nativeControlIndication(): Indication = ripple(bounded = true)

@Composable
fun InlineCircularLoader(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color.White
) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = color,
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun UiIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    content: @Composable () -> Unit
) {
    IconButton(onClick = onClick, modifier = modifier, enabled = enabled, content = content)
}

open class Button {
    open val isEnabled: Boolean get() = true
    @Composable
    open fun ToolBarButton(modifier: Modifier = Modifier) {}
}

open class Icon : Button()

val LocalDialogPresentation = compositionLocalOf { true }

class NativeDialogDimming {
    val alpha: Float = 0.5f
}

fun dialogFadeAlpha(fraction: Float): Float = fraction
fun dialogScale(fraction: Float): Float = fraction

suspend fun animateDialogMotion(animatable: Animatable<Float, AnimationVector1D>, target: Float) {
    animatable.animateTo(target)
}

object TabToolBar {
    val HORIZONTAL_PADDING: Dp = 16.dp
    val VERTICAL_PADDING: Dp = 8.dp
    val TOOLBAR_ICON_SIZE: Dp = 24.dp

    @Composable
    operator fun invoke(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        Box(modifier = modifier) { content() }
    }

    @Composable
    fun Buttons(
        buttons: List<Button>,
        horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
        verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
        modifier: Modifier = Modifier,
        disableAnimation: Boolean = false,
    ) {
        Row(modifier, horizontalArrangement, verticalAlignment) {
            buttons.forEach { it.ToolBarButton() }
        }
    }
}

object EllipsisMenuComponent {
    fun init(overflowButtons: () -> List<Button>): Button = object : Button() {
        @Composable
        override fun ToolBarButton(modifier: Modifier) {
            IconButton(onClick = {}, modifier = modifier) {
                // ellipsis
            }
        }
    }

    @Composable
    operator fun invoke(onClick: () -> Unit, modifier: Modifier = Modifier) {
        IconButton(onClick = onClick, modifier = modifier) {
            // ellipsis icon
        }
    }
}
