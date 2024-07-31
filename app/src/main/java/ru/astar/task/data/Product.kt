package ru.astar.task.data

data class Product(
    val id: Long,
    val name: String,
    val time: String,
    val tags: List<String>,
    val amount: Int,
)