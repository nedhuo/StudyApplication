package com.example.feature_tvbox.di

import com.example.feature_tvbox.data.TvBoxRepository
import com.example.feature_tvbox.domain.FetchAndParseMeowTvUseCase
import com.example.lib_database.DatabaseManager
import com.example.lib_spider.meowtv.spider.MeowTvSpider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvBoxModule {
    @Provides
    @Singleton
    fun provideTvBoxRepository(
        databaseManager: DatabaseManager,
        spider: MeowTvSpider
    ) = TvBoxRepository(databaseManager, spider)

    @Provides
    @Singleton
    fun provideFetchAndParseMeowTvUseCase(
        repository: TvBoxRepository
    ) = FetchAndParseMeowTvUseCase(repository)
} 