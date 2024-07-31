package ru.astar.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.astar.task.data.Product
import ru.astar.task.data.ProductsRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    private val searchFlow = MutableStateFlow("")

    private val products: StateFlow<List<Product>> = productsRepository
        .observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val filteredProducts = products.combine(searchFlow) { products, query ->
        products.filter { product ->
            product.name.contains(query, ignoreCase = true)
        }
    }

    fun setSearchString(query: String) {
        searchFlow.value = query
    }

    fun setNumberProducts(productId: Long, quantity: Int) {
        viewModelScope.launch {
            productsRepository.setNumberProducts(productId, quantity)
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            productsRepository.deleteProduct(productId)
        }
    }

}