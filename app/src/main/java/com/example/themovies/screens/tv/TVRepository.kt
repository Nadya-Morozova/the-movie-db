package com.example.themovies.screens.tv

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.TvApi
import com.example.themovies.data.TV
import com.example.themovies.network.ConfigurationRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TVRepository @Inject constructor(
    private val configurationApi: ConfigurationApi,
    private val tvApi: TvApi
) {

    suspend fun getPopularTV(page: Int = 1): List<TV> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        tvApi.getPopularTV(page)?.results ?: mutableListOf()
    }

    private suspend fun checkConfiguration() {
        if (!ConfigurationRepository.isConfigurationDownloaded()) {
            ConfigurationRepository.downloadConfiguration(configurationApi)
        }
    }
}