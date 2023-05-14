package com.example.attractions.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.R

class AttractionListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val ivAttraction = itemView.findViewById<ImageView>(R.id.iv_thumb)
    val tvAttrTitle = itemView.findViewById<TextView>(R.id.tv_title)
    val tvAttrDescription = itemView.findViewById<TextView>(R.id.tv_description)
}
