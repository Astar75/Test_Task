package ru.astar.task.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM item")
    fun observeAll(): Flow<List<ProductDb>>

    @Query("SELECT * FROM item WHERE id = :productId")
    suspend fun fetchBy(productId: Long): ProductDb?

    @Update
    suspend fun update(product: ProductDb)

    @Query("DELETE FROM item WHERE id = :productId")
    suspend fun delete(productId: Long)
}
