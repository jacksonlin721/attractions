package com.example.attractions.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:showProgress")
fun showProgress(view: View, visible: Int) {
    view.visibility = visible
}