package com.example.attractions.view

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.R
import com.example.attractions.databinding.AttractionListBinding
import com.example.attractions.network.model.Data
import com.example.attractions.util.PhotoDisplayUtil

class AttractionListViewHolder2(val binding: AttractionListBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(
        context: Context,
        item: Data?,
        itemClicklistener: AttractionListAdapter.OnItemClicklistener?
    ) {
        binding.tvDescription.text = item?.introduction
        binding.tvTitle.text = item?.name
        PhotoDisplayUtil.showPhoto(
            context,
            if (item?.images?.size!! > 0) {
                item.images[0].src.toString()
            } else {
                null
            },
            binding.ivThumb
        )
        binding.data = item
        binding.itemClicklistener = itemClicklistener

        binding.executePendingBindings()
    }
}