package ru.astar.task.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ProductDb(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val name: String,
    val time: Long,
    val tags: List<String>,
    var amount: Int,
)