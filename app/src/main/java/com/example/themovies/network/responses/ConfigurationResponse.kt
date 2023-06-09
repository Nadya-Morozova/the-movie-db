package com.example.themovies.network.responses

import com.example.themovies.network.data.PosterSize
import com.google.gson.annotations.SerializedName

class ConfigurationResponse(
    @SerializedName("images")
    var imagesSize: PosterSize? = null
)
