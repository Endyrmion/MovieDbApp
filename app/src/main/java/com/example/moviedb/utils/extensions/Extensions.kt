package com.example.moviedb.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.contentValuesOf

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    var value: Int = 2

    if (value >= 10) {
        while (value < 5){
            value++
        }
    }

    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}