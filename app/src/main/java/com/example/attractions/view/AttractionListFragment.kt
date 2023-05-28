package com.example.attractions.view

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.MainActivity
import com.example.attractions.R
import com.example.attractions.databinding.FragmentAttractionListBinding
import com.example.attractions.network.model.Data
import com.example.attractions.viewmodel.AttractionViewModel
import kotlinx.coroutines.launch

class AttractionListFragment: Fragment(), LanguageCheckboxDialog.OnItemSelectListener {
    var mView: View? = null
    var progressBar: ProgressBar? = null
    var mAttractionListAdapter: AttractionListAdapter? = null
    val mAttractionViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AttractionViewModel::class.java)
    }
    var selectedLangIdx = 0
    lateinit var binding: FragmentAttractionListBinding

    companion object {
        val langKey = arrayListOf("zh-tw", "zh-cn", "en", "ja", "ko", "es", "id", "th", "vi")
        val langValue = arrayListOf("正體中文", "簡體中文", "英文", "日文", "韓文", "西班牙文", "印尼文", "泰文", "越南文")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentAttractionListBinding.inflate(
                inflater, container, false
            ).also {
                it.vm = mAttractionViewModel
                it.lifecycleOwner = viewLifecycleOwner
            }

        mView = binding.root
        initUI()
        setHasOptionsMenu(true)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserver()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mAttractionViewModel.attractionListPagingFlow.collect {
                    setLoadingVisibility(false)
                    mAttractionListAdapter?.submitData(it)
                }
            }
        }

        mAttractionViewModel.progressVisible.observe(viewLifecycleOwner) { visible ->
            binding.progressVisible = visible
        }
    }

    private fun initUI() {
        (requireActivity() as MainActivity).mToolbar?.title = requireContext().getString(R.string.app_name)
        val recyclerView = binding.rvAttractions
        progressBar = binding.progressBar
        mAttractionViewModel.progressVisible.postValue(View.VISIBLE)
        mAttractionListAdapter = AttractionListAdapter(requireContext())
        recyclerView.adapter = mAttractionListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAttractionListAdapter?.addLoadStateListener {
            when (it.append) {
                is LoadState.Loading -> mAttractionViewModel.progressVisible.postValue(View.VISIBLE)
                is LoadState.Error,
                is LoadState.NotLoading -> mAttractionViewModel.progressVisible.postValue(View.GONE)
            }
        }

        mAttractionListAdapter?.setItemClicklistener(object :
            AttractionListAdapter.OnItemClicklistener {
            override fun onItemClick(data: Data?) {
                val bundle = bundleOf(
                    "data" to data
                )
                (requireActivity() as? MainActivity)?.navController?.navigate(
                    R.id.detail_fragment,
                    bundle
                )
            }
        })
    }

    private fun setLoadingVisibility(visible: Boolean) {
        progressBar?.visibility =
            if (visible) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_change_lang -> {
                showLanguageDialog()
            }
        }
        return false
    }

    private fun showLanguageDialog() {
        val checkboxDialog = LanguageCheckboxDialog(requireContext(), langValue, mAttractionViewModel.mLanguageIdx)
        checkboxDialog.setLangCallback(this)
        checkboxDialog.setSelectedItem(mAttractionViewModel.mLanguageIdx)
        checkboxDialog.showDialog()
    }

    override fun selectLanguage(position: Int) {
        mAttractionViewModel.mLanguageIdx = position
        mAttractionViewModel.mLanguage = langKey[position]
        mAttractionListAdapter?.refresh()
    }
}