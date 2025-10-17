package com.cheonjaeung.compose.grid.sample

import androidx.compose.runtime.Composable

@Composable
internal expect fun PlatformBackHandler(enabled: Boolean = true, onBack: () -> Unit)
