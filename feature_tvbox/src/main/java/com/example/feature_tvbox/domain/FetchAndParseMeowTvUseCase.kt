package com.example.feature_tvbox.domain

import com.example.feature_tvbox.data.TvBoxRepository

class FetchAndParseMeowTvUseCase(private val repository: TvBoxRepository) {
    suspend operator fun invoke() = repository.fetchAndSaveMeowTvSites()
} 