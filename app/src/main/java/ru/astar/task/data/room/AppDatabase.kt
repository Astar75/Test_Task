package ru.astar.task.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = [ProductDb::class], version = 1)
@TypeConverters(TagsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao
}