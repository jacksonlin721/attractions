package com.example.attractions.view

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.attractions.MainActivity
import com.example.attractions.R
import com.example.attractions.databinding.FragmentAttractionDetailBinding
import com.example.attractions.network.model.Data
import com.example.attractions.network.model.Images
import com.example.attractions.network.model.Links
import com.example.attractions.util.PhotoDisplayUtil
import com.example.attractions.view.AttractionDetailFragment.ViewPagerAdapter.ItemViewHolder
import com.example.attractions.viewmodel.AttractionViewModel
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import java.util.*


class AttractionDetailFragment: Fragment() {
    var mView: View? = null
    lateinit var binding: FragmentAttractionDetailBinding
    val mAttractionViewModel: AttractionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttractionDetailBinding.inflate(
            inflater, container, false
        )
        mView = binding.root
        initUI()
        return mView
    }

    private fun initUI() {
        val data: Data = arguments?.getParcelable("data")!!
        (requireActivity() as MainActivity).mToolbar?.title = data.name
        binding.tvTitle.text = data.name
        binding.tvDescription.text = data.introduction
        binding.tvAddress.text = data.address
        binding.tvAddressTitle.text = getStringByLocale(R.string.str_address)
        binding.tvUpdateTimeTitle.text = getStringByLocale(R.string.str_last_update_time)
        binding.tvUpdateTime.text = data.modified

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
                mAttractionViewModel.mLanguage == "zh-cn"
            )
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
        val spannableString = SpannableString(linkText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        spannableString.setSpan(clickableSpan, 0, linkText?.length!!, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvLink.text = spannableString
        binding.tvLink.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initViewPagerUI(data: Data) {
        val viewPager: ViewPager2 = binding.viewPager
        val dotsIndicator: WormDotsIndicator = binding.dotsIndicator
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