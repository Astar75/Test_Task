package ru.astar.task.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.astar.task.data.mappers.toProductList
import ru.astar.task.data.room.ProductsDao
import javax.inject.Inject

interface ProductsRepository {

    fun observeAll(): Flow<List<Product>>
    suspend fun setNumberProducts(productId: Long, quantity: Int)
    suspend fun deleteProduct(productId: Long)

    class Base @Inject constructor(
        private val dao: ProductsDao,
    ) : ProductsRepository {
        override fun observeAll(): Flow<List<Product>> =
            dao.observeAll().map { it.toProductList() }

        override suspend fun setNumberProducts(productId: Long, quantity: Int) =
            withContext(Dispatchers.IO) {
                val product = dao.fetchBy(productId)
                    ?: error("Product #$productId not found in database")
                product.amount = quantity
                dao.update(product)
            }

        override suspend fun deleteProduct(productId: Long) = withContext(Dispatchers.IO) {
            dao.delete(productId)
        }

    }
}
