package net.pedromalta.oficina.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VerticalScrollBar(
    stateVertical: ScrollState,
    modifier: Modifier,
)