package com.example.attractions.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.attractions.R
import com.example.attractions.databinding.AttractionListBinding
import com.example.attractions.network.model.Data
import com.example.attractions.util.dataBind.EventClickHandler


class AttractionListAdapter(private val mContext: Context):
    PagingDataAdapter<Data, AttractionListViewHolder2>(itemDiffCallback) {
    var mItemClicklistener: OnItemClicklistener? = null

    interface OnItemClicklistener {
        fun onItemClick(data: Data?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionListViewHolder2 {
        val view = DataBindingUtil.inflate<AttractionListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.attraction_list,
            parent,
            false
        )
        return AttractionListViewHolder2(view)
    }


    override fun onBindViewHolder(holder: AttractionListViewHolder2, position: Int) {
        val item = getItem(position)
        holder.bindData(mContext, item, mItemClicklistener)
    }

    fun setItemClicklistener(itemClicklistener: OnItemClicklistener) {
        mItemClicklistener = itemClicklistener
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
