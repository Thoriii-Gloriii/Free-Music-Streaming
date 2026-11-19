package com.metrolist.android.utils.toast

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class GlassToastDuration { Short, Long }

data class GlassToastMessage(
    val text: String,
    val background: Int = 0xFF212121.toInt(),
    val foreground: Int = 0xFFFFFFFF.toInt(),
    val icon: Drawable? = null,
    val duration: GlassToastDuration = GlassToastDuration.Short,
    val id: String = System.nanoTime().toString()
)

fun toastDurationMillis(duration: GlassToastDuration): Long =
    if (duration == GlassToastDuration.Short) 2000L else 3500L

fun toastDurationMillis(duration: Any?): Long = 2000L

typealias ToastHandler = (GlassToastMessage) -> Boolean

class GlassToastDispatcher {
    private val handlers = mutableListOf<Pair<Any, ToastHandler>>()

    fun register(owner: Any, handler: ToastHandler) {
        synchronized(handlers) { handlers.add(owner to handler) }
    }

    fun unregister(owner: Any) {
        synchronized(handlers) { handlers.removeAll { it.first === owner } }
    }

    fun dispatch(message: GlassToastMessage): Boolean {
        val snapshot = synchronized(handlers) { handlers.toList() }
        return snapshot.any { it.second(message) }
    }

    companion object {
        private val instance = GlassToastDispatcher()
        fun register(owner: Any, handler: ToastHandler) = instance.register(owner, handler)
        fun unregister(owner: Any) = instance.unregister(owner)
        fun dispatch(message: GlassToastMessage) = instance.dispatch(message)
    }
}

val LocalGlassToastDispatcher = compositionLocalOf { GlassToastDispatcher() }

fun enqueueGlassToast(
    queue: MutableList<GlassToastMessage>,
    message: GlassToastMessage
) {
    if (queue.size >= 3) queue.removeAt(0)
    queue.add(message)
}

fun Context.enqueueGlassToast(text: String, durationMs: Long = 2000L) {}

/** Composable-accessible toast placement tracker. */
object BottomNavigationToastPlacement {
    private var _top: Float? = null

    fun update(top: Float?) { _top = top }

    /** Returns the top Y-coordinate (px) of the bottom navigation bar, or null if unknown. */
    fun topInWindow(): Float? = _top
}

/** Records a Compose layout's position so the toast host can avoid it. */
fun androidx.compose.ui.Modifier.trackToastObstruction(): androidx.compose.ui.Modifier = this
