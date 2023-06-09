package com.example.themovies.views.adapters.binding

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.Person
import com.example.themovies.network.data.RecordType
import com.example.themovies.network.data.TV

@BindingAdapter("nullOrEmpty")
fun TextView.nullOrEmpty(text: String?) {
    if (text.isNullOrBlank()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("equalsZero")
fun TextView.equalsZero(value: String?) {
    if (value == "0" || value == "0.0") {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("textOrHideIfEmpty")
fun TextView.setTextOrHideIfEmpty(value: String?) {
    this.isVisible = value != "0"
}

@BindingAdapter("hideText")
fun TextView.hideText(value: String?) {
    this.isVisible = value?.isNotBlank() == true
}

@BindingAdapter("dotAfterThreeNumbers")
fun TextView.setDotAfterThreeNumbers(value: String?) {
    val length = value?.length ?: 0
    if (length > 3) {
        val builder = StringBuilder(value.toString())
        var dots = length / 3
        if (length % 3 == 0) dots -= 1
        for (i in 1..dots) {
            builder.insert(length - (i * 3), ".")
        }
        this.text = "$builder$"
    } else {
        this.text = "$value$"
    }
}

@BindingAdapter("url")
fun TextView.setUrl(url: String?) {
    if (url?.isNotBlank() == true) {
        this.visibility = View.VISIBLE
        this.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            this.context.startActivity(intent)
        }
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("textMinutes")
fun TextView.setTextForMinutes(value: String?) {
    this.text = "$value minutes"
}

@BindingAdapter("setTextForGallery")
fun TextView.setTextForGallery(value: String?) {
    this.text = "Photo gallery of $value"
}