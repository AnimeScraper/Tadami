package com.sf.tadami.ui.components.grid

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyScreen(
    actions: List<EmptyScreenAction> = emptyList(),
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            actions.forEach { action ->
                Action(
                    modifier = Modifier.weight(1f,false),
                    icon = action.icon,
                    title = stringResource(id = action.stringResId),
                    onClick = action.onClick
                )
            }
        }
    }
}

@Composable
private fun Action(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = title,
                textAlign = TextAlign.Center,
            )
        }
    }
}

data class EmptyScreenAction(
    @StringRes val stringResId: Int,
    val icon: ImageVector,
    val onClick: () -> Unit,
)