package ru.astar.task.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.astar.task.data.ProductsRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindRepository(impl: ProductsRepository.Base): ProductsRepository
}