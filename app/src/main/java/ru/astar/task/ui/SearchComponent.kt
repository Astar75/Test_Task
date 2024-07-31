package ru.astar.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.astar.task.MainViewModel
import ru.astar.task.R

@Composable
fun SearchComponent(
    viewModel: MainViewModel = viewModel(),
) {
    var textValue by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White),
        value = textValue,
        onValueChange = {
            textValue = it
            viewModel.setSearchString(it)
        },
        label = { Text(text = stringResource(id = R.string.product_search)) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        }
    )
}