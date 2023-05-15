package com.example.attractions.view

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.R
import com.example.attractions.viewmodel.AttractionViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment: Fragment() {
    var mView: View? = null
    var progressBar: ProgressBar? = null
    var mAttractionListAdapter: AttractionListAdapter? = null
    val mAttractionViewModel by lazy {
        ViewModelProvider(this).get(AttractionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView =
            inflater.inflate(R.layout.fragment_attraction_list, container, false)
        initUI()
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
    }

    private fun initUI() {
        val recyclerView = mView?.findViewById<RecyclerView>(R.id.rv_attractions)
        progressBar = mView?.findViewById(R.id.progress_bar)
        mAttractionListAdapter = AttractionListAdapter(requireContext())
        recyclerView?.adapter = mAttractionListAdapter
        mAttractionListAdapter?.addLoadStateListener {
            when (it.append) {
                is LoadState.Loading -> setLoadingVisibility(true)
                is LoadState.Error,
                is LoadState.NotLoading -> setLoadingVisibility(false)
            }
        }
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

            }
        }
        return false
    }
}