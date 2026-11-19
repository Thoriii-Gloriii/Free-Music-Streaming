package com.metrolist.android.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

/** Navigation bar position shim for N-Zik glass components. */
enum class NavigationBarPosition {
    Left,
    Bottom,
    Right;

    companion object {
        private val LocalNavigationBarPosition =
            compositionLocalOf { Bottom }

        /** Returns the current navigation bar position from the composition. */
        @Composable
        fun current(): NavigationBarPosition = LocalNavigationBarPosition.current
    }
}
