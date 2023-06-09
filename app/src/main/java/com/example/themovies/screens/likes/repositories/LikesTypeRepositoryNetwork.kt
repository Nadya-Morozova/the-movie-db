package com.example.themovies.screens.likes.repositories

import com.example.themovies.api.MovieApi
import com.example.themovies.api.PeopleApi
import com.example.themovies.api.TvApi
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.Person
import com.example.themovies.network.data.TV
import com.example.themovies.network.repositories.ConfigurationRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class LikesTypeRepositoryNetwork @Inject constructor(
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val peopleApi: PeopleApi,
    private val configurationRepository: ConfigurationRepository
) {

    suspend fun getMovie(movieId: Int?): Movie? = withContext(Dispatchers.IO) {
        checkConfiguration()
        movieApi.getMovie(movieId)
    }

    suspend fun getTv(tvId: Int): TV? = withContext(Dispatchers.IO) {
        checkConfiguration()
        tvApi.getTV(tvId)
    }

    suspend fun getPeople(peopleId: Int?): Person? = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getPeople(peopleId)
    }

    private suspend fun checkConfiguration() {
        if (!configurationRepository.isConfigurationDownloaded()) {
            configurationRepository.downloadConfiguration()
        }
    }

}