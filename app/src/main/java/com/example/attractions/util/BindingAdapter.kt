package com.example.attractions.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState

@BindingAdapter("app:showProgress")
fun showProgress(view: View, loadState: androidx.paging.LoadState) {
    view.visibility =
        when (loadState) {
            is LoadState.Loading -> View.VISIBLE
            is LoadState.Error,
            is LoadState.NotLoading -> View.GONE
        }
}