package ru.astar.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.astar.task.MainViewModel
import ru.astar.task.R

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        productId = 1,
        productName = "IPhone 13",
        productTags = listOf("Телефон", "Гаджет"),
        stockCount = 13,
        addedDate = "30.07.2024"
    )
}

@Composable
fun ProductCard(
    productId: Long,
    productName: String,
    productTags: List<String>,
    stockCount: Int,
    addedDate: String,
    viewModel: MainViewModel = viewModel(),
) {
    var showEditProductDialog by remember { mutableStateOf(false) }
    var showDeleteProductDialog by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = productName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row {
                    IconButton(onClick = {
                        showEditProductDialog = true
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp),
                            tint = colorResource(id = R.color.edit),
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit)
                        )
                    }
                    IconButton(onClick = {
                        showDeleteProductDialog = true
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp),
                            tint = colorResource(id = R.color.delete),
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Tags(elements = productTags)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TwoLineText(
                    primary = stringResource(id = R.string.in_stock),
                    secondary = "$stockCount"
                )
                TwoLineText(
                    primary = stringResource(id = R.string.date_added),
                    secondary = addedDate
                )
            }
        }
    }

    if (showDeleteProductDialog) {
        ConfirmDialog(
            title = stringResource(id = R.string.delete_product),
            text = stringResource(id = R.string.ask_delete_product),
            onConfirm = {
                showDeleteProductDialog = false
                viewModel.deleteProduct(productId)
            },
            onDismiss = {
                showDeleteProductDialog = false
            }
        )
    }

    if (showEditProductDialog) {
        ProductCountDialog(
            stockCount = stockCount,
            onConfirm = { amount ->
                viewModel.setNumberProducts(productId, amount)
                showEditProductDialog = false
            },
            onDismiss = {
                showEditProductDialog = false
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Tags(elements: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        elements.forEach { element ->
            SuggestionChip(
                label = { Text(text = element) },
                onClick = {}
            )
        }
    }
}

@Composable
fun TwoLineText(primary: String, secondary: String) {
    Column {
        Text(text = primary, fontSize = 12.sp, color = Color.Gray)
        Text(text = secondary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

