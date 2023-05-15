package com.example.attractions.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.attractions.network.model.AttractionList
import com.example.attractions.network.model.Data
import kotlinx.coroutines.flow.collectLatest

class AttractionPagingSource(private val mAttractionViewModel: AttractionViewModel) :
    PagingSource<Int, Data>() {
    val init_page = 1

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val page = params.key ?: init_page
        return try {
            var attractionList: MutableList<Data> = mutableListOf()
            mAttractionViewModel.loadAttractionList(page.toString()).collect {
                attractionList = it
            }

            LoadResult.Page(
                data = attractionList,
                prevKey = if (page == init_page) null else page - 1,
                nextKey = if (attractionList.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}