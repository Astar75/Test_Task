package ru.astar.task.data.mappers

import android.annotation.SuppressLint
import ru.astar.task.data.Product
import ru.astar.task.data.room.ProductDb
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
private val dateTimeFormatter = SimpleDateFormat("dd.MM.yyyy")

fun List<ProductDb>.toProductList(): List<Product> =
    this.map { it.toProduct() }

fun ProductDb.toProduct() = Product(
    id = this.id,
    name = this.name,
    time = dateTimeFormatter.format(this.time),
    tags = this.tags,
    amount = amount
)