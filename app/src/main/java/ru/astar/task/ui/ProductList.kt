package ru.astar.task.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.astar.task.MainViewModel

@Composable
fun ProductList(
    viewModel: MainViewModel = viewModel(),
) {
    val products by viewModel.filteredProducts.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(
                productId = product.id,
                productName = product.name,
                productTags = product.tags,
                stockCount = product.amount,
                addedDate = product.time,
            )
        }
    }
}
