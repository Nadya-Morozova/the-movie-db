package com.example.themovies.screens.people

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.PeopleApi
import com.example.themovies.data.People
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PeopleRepository {

    private val peopleApi = NetworkUtils.createRetrofit().create(PeopleApi::class.java)
    private val configurationApi =
        NetworkUtils.createRetrofit().create(ConfigurationApi::class.java)

    suspend fun getPopularPeople(page: Int = 1): List<People> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        peopleApi.getPopularPeople(page)?.results ?: mutableListOf()
    }

    suspend fun getPeople(peopleId: Int?): People? = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getPeople(peopleId)
    }

    private suspend fun checkConfiguration() {
        if (!ConfigurationRepository.isConfigurationDownloaded()) {
            ConfigurationRepository.downloadConfiguration(configurationApi)
        }
    }

}