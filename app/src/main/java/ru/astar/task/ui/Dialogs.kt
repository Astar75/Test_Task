package ru.astar.task.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.astar.task.R


@Composable
fun ConfirmDialog(
    title: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.delete_product)
            )
        },
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = {
            onDismiss()
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(text = stringResource(id = R.string.yes))
            }
        }
    )
}


@Composable
fun ProductCountDialog(
    stockCount: Int,
    onConfirm: (count: Int) -> Unit,
    onDismiss: () -> Unit,
) {

    var amount by remember { mutableIntStateOf(stockCount) }

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings)
            )
        },
        title = { Text(text = stringResource(R.string.quantity_of_goods)) },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (amount > 0) amount-- }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_decrement),
                        contentDescription = stringResource(R.string.less)
                    )
                }

                Text(text = "$amount", fontSize = 22.sp, modifier = Modifier.padding(16.dp))

                IconButton(onClick = { amount++ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_increment),
                        contentDescription = stringResource(R.string.more)
                    )
                }
            }
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(amount)
            }) {
                Text(text = stringResource(id = R.string.accept))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}