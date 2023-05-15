package com.example.attractions.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.attractions.R
import com.example.attractions.network.model.AttractionList
import com.example.attractions.network.model.Data
import com.example.attractions.util.PhotoDisplayUtil
import org.w3c.dom.Attr

class AttractionListAdapter(val mContext: Context): PagingDataAdapter<Data, AttractionListViewHolder>(itemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.attraction_list, parent, false)
        return AttractionListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttractionListViewHolder, position: Int) {
        val item = getItem(position)
        if (item?.images?.size!! > 0)
            PhotoDisplayUtil.showPhoto(
                mContext,
                item.images[0].src.toString(),
                holder.ivAttraction
            )
        holder.tvAttrTitle.text = item.name
        holder.tvAttrDescription.text = item.introduction
    }


    companion object {
        val itemDiffCallback: DiffUtil.ItemCallback<Data> =
            object : DiffUtil.ItemCallback<Data>() {
                override fun areItemsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean {
                    return oldItem.name == newItem.name
                }

            }
    }
}
