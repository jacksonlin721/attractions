package com.example.attractions.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.attractions.MainActivity
import com.example.attractions.R
import com.example.attractions.network.model.Data
import com.example.attractions.network.model.Images
import com.example.attractions.network.model.Links
import com.example.attractions.util.PhotoDisplayUtil
import com.example.attractions.view.AttractionDetailFragment.ViewPagerAdapter.ItemViewHolder
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class AttractionDetailFragment: Fragment() {
    var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_attraction_detail, container, false)
        initUI()
        return mView
    }

    private fun initUI() {
        val data: Data = arguments?.getParcelable("data")!!
        (requireActivity() as MainActivity).mToolbar?.title = data.name
        mView?.findViewById<TextView>(R.id.tv_title)?.text = data.name
        mView?.findViewById<TextView>(R.id.tv_description)?.text = data.introduction
        mView?.findViewById<TextView>(R.id.tv_address)?.text = data.address
        mView?.findViewById<TextView>(R.id.tv_update_time)?.text = data.modified
        if (data.links.size > 0)
            initTextLink(data.links[0])

        initViewPagerUI(data)

    }

    private fun initTextLink(link: Links) {
        val linkTextView = mView?.findViewById<TextView>(R.id.tv_link)
        val linkText = link.subject
        val url = link.src
        val spannableString = SpannableString(linkText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        spannableString.setSpan(clickableSpan, 0, linkText?.length!!, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        linkTextView?.text = spannableString
        linkTextView?.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initViewPagerUI(data: Data) {
        val viewPager: ViewPager2 = mView?.findViewById(R.id.view_pager)!!
        val dotsIndicator: WormDotsIndicator = mView?.findViewById(R.id.dotsIndicator)!!
        val adapter = ViewPagerAdapter()
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 10
        dotsIndicator.attachTo(viewPager)
        adapter.setData(data.images)
    }

    inner class ViewPagerAdapter: RecyclerView.Adapter<ItemViewHolder>() {
        var mImgList: ArrayList<Images> = arrayListOf()

        inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val imageView = itemView.findViewById<ImageView>(R.id.iv_photo)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.viewpager_photo, parent, false
            )
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            PhotoDisplayUtil.showPhoto(
                requireContext(),
                mImgList[position].src!!,
                holder.imageView
            )
        }

        fun setData(imgSrc: ArrayList<Images>) {
            mImgList = imgSrc
            notifyDataSetChanged()
        }


        override fun getItemCount(): Int {
            return mImgList.size
        }
    }

}