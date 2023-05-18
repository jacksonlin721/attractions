package com.example.attractions.view

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.media.Image
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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.attractions.MainActivity
import com.example.attractions.R
import com.example.attractions.network.model.Data
import com.example.attractions.network.model.Images
import com.example.attractions.network.model.Links
import com.example.attractions.util.PhotoDisplayUtil
import com.example.attractions.view.AttractionDetailFragment.ViewPagerAdapter.ItemViewHolder
import com.example.attractions.viewmodel.AttractionViewModel
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import java.util.*
import kotlin.collections.ArrayList


class AttractionDetailFragment: Fragment() {
    var mView: View? = null
    val mAttractionViewModel: AttractionViewModel by activityViewModels()

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
        mView?.findViewById<TextView>(R.id.tv_address_title)?.text =
            getStringByLocale(R.string.str_address)
        mView?.findViewById<TextView>(R.id.tv_address)?.text = data.address
        mView?.findViewById<TextView>(R.id.tv_update_time_title)?.text =
            getStringByLocale(R.string.str_last_update_time)
        mView?.findViewById<TextView>(R.id.tv_update_time)?.text = data.modified
        if (data.links.size > 0)
            initTextLink(data.links[0].subject, data.links[0].src)
        else
            initTextLink(data.url, data.url)

        if (data.images.size > 0)
            initViewPagerUI(data)
        else {
            mView?.findViewById<ViewPager2>(R.id.view_pager)?.visibility = View.GONE
            mView?.findViewById<WormDotsIndicator>(R.id.dotsIndicator)?.visibility = View.GONE
        }
    }

    private fun getStringByLocale(strRes: Int): String {
        val desiredLocale =
            if (mAttractionViewModel.mLanguage == "zh-tw" ||
                mAttractionViewModel.mLanguage == "zh-cn")
                Locale("zh", "TW")
        else
                Locale("en", "US")
        val resources: Resources = resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(desiredLocale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return resources.getString(strRes)
    }

    private fun initTextLink(linkText: String?, url: String?) {
        val linkTextView = mView?.findViewById<TextView>(R.id.tv_link)
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
        adapter.setData(
            if(data.images.size == 0) {
                val imageArray = arrayListOf<Images>()
                val images = Images()
                images.src = null
                imageArray.add(images)
                imageArray
            } else
                data.images
        )
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
                mImgList[position].src,
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