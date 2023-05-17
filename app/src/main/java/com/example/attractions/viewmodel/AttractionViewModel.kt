package com.example.attractions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.attractions.network.RequestFuture
import com.example.attractions.network.api.GetAttractionList
import com.example.attractions.network.model.AttractionList
import com.example.attractions.network.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AttractionViewModel: ViewModel() {
    val LIST_COUNT = 30
    var mLanguage = "zh-tw"
    var mLanguageIdx = 0

    val attractionListPagingFlow: Flow<PagingData<Data>> = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = LIST_COUNT
        ),
        pagingSourceFactory = { AttractionPagingSource(this, mLanguage) }
    ).flow.cachedIn(viewModelScope)

    fun loadAttractionList(page: String, language: String = "zh-tw"): Flow<MutableList<Data>> {
        return flow {
            val future = RequestFuture.newFuture<AttractionList>()!!
            GetAttractionList.getAttractionList(
                language,
                page,
                future
            )
            emit(future.get().data)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loadAttractionListSuspend(page: String): List<Data> {
        return withContext(Dispatchers.IO) {
            val attractionList = GetAttractionList.getAttractionListSuspend("zh-tw", page)
            attractionList.data
        }
    }
}