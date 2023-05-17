package com.example.attractions.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object PhotoDisplayUtil {

    fun showPhoto(context: Context, url: String?, imageView: ImageView) {
        Glide
            .with(context)
            .load(url)
            .placeholder(android.R.drawable.star_on)
            .into(imageView)
    }
}