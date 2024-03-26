package com.cheonjaeung.compose.grid.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SampleDrawerSheet(
    modifier: Modifier = Modifier,
    content: SampleDrawerSheetScope.(LazyListScope) -> Unit,
) {
    ModalDrawerSheet(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            SampleDrawerSheetScopeImpl.content(this)
        }
    }
}

@LayoutScopeMarker
interface SampleDrawerSheetScope {
    fun LazyListScope.title(text: String)

    fun LazyListScope.menu(
        onClick: () -> Unit,
        text: String,
    )

    fun LazyListScope.divider()
}

private object SampleDrawerSheetScopeImpl : SampleDrawerSheetScope {
    override fun LazyListScope.title(text: String) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    text = text,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
    }

    override fun LazyListScope.menu(
        onClick: () -> Unit,
        text: String,
    ) {
        item {
            SampleDrawerItem(
                modifier = Modifier.padding(horizontal = 12.dp),
                onClick = onClick,
            ) {
                Text(text = text)
            }
        }
    }

    override fun LazyListScope.divider() {
        item {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
            )
        }
    }
}

@Composable
private fun SampleDrawerItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(percent = 100))
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}
