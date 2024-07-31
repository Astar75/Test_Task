package ru.astar.task.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.astar.task.data.room.AppDatabase
import ru.astar.task.data.room.ProductsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .createFromAsset("data.db")
            .build()
    }

    @Provides
    fun provideProductsDao(database: AppDatabase): ProductsDao {
        return database.getProductsDao()
    }
}