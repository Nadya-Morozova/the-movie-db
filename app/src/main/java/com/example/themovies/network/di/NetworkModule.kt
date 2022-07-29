package com.example.themovies.network.di

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MovieApi
import com.example.themovies.api.PeopleApi
import com.example.themovies.api.TvApi
import com.example.themovies.network.interceptor.ApiKeyInterceptor
import com.example.themovies.network.repositories.ConfigurationRepository
import com.example.themovies.utils.ConnectivityTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "d906252ac4f180bbf851fd1bf9e97f9b"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConfigurationApi(retrofit: Retrofit): ConfigurationApi {
        return retrofit.create(ConfigurationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTvApi(retrofit: Retrofit): TvApi {
        return retrofit.create(TvApi::class.java)
    }

    @Provides
    @Singleton
    fun providePeopleApi(retrofit: Retrofit): PeopleApi {
        return retrofit.create(PeopleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConfigurationRepository(configurationApi: ConfigurationApi): ConfigurationRepository {
        return ConfigurationRepository(configurationApi)
    }

    @Provides
    fun provideConnectivityTracker(): ConnectivityTracker {
        return ConnectivityTracker()
    }

}



