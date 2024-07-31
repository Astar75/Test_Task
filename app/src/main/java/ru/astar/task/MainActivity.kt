package ru.astar.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.astar.task.ui.theme.TaskTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TaskTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = ("Список товаров")) })
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        InputMyText(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .background(Color.White)
                        )
                        ProductList()
                    }
                }
            }
        }
    }
}

@Composable
fun ProductList(viewModel: MainViewModel = viewModel()) {

    val state by viewModel.filteredProducts.collectAsState(initial = emptyList())

    LazyColumn {
        items(state) { product ->
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
    var showEditQuantityDialog by remember { mutableStateOf(false) }
    var showDeleteProductDialog by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
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
                        showEditQuantityDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        showDeleteProductDialog = true
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
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
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = stringResource(R.string.delete_product)
                )
            },
            title = { Text(text = stringResource(id = R.string.delete_product)) },
            text = { Text(text = stringResource(R.string.ask_delete_product)) },
            onDismissRequest = {
                showDeleteProductDialog = false
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteProductDialog = false
                }) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteProductDialog = false
                    viewModel.deleteProduct(productId)
                }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            }
        )
    }

    if (showEditQuantityDialog) {
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
            onDismissRequest = { showEditQuantityDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setNumberProducts(productId, amount)
                    showEditQuantityDialog = false
                }) {
                    Text(text = stringResource(id = R.string.accept))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEditQuantityDialog = false
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun TwoLineText(primary: String, secondary: String) {
    Column {
        Text(text = primary, fontSize = 12.sp, color = Color.Gray)
        Text(text = secondary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Tags(elements: List<String>) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        elements.forEach { element ->
            SuggestionChip(onClick = {}, label = { Text(text = element) })
        }
    }
}

@Composable
fun InputMyText(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    var textValue by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
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


@Composable
fun DialogExamples() {
    val state = remember { mutableStateOf(false) }

    when {
        state.value -> ConfirmDialog()
    }
}

@Composable
fun ConfirmDialog() {
    AlertDialog(
        icon = { Icon(Icons.Default.Info, contentDescription = "") },
        title = { Text(text = "Hello") },
        text = { Text(text = "World") },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Dismiss")
            }
        })
}