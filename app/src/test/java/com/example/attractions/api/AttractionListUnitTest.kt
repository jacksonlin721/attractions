package com.example.attractions.api

import com.example.attractions.network.RequestFuture
import com.example.attractions.network.RetrofitCallback
import com.example.attractions.network.api.GetAttractionList.Companion.getAttractionList
import com.example.attractions.network.model.AttractionList
import com.example.attractions.network.model.Data
import org.hamcrest.CoreMatchers.any
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AttractionListUnitTest {

    @Mock
    lateinit var mockApi: AttractionListAPI

    @Mock
    lateinit var mockCall: Call<AttractionList>

    @Test
    fun testGetAttractionList() {
        val language = "en"
        val page = "1"

        val attractionList = generateDummyData()
        val response = Response.success(attractionList)

        // 使用 Mockito 模拟 API 调用和响应
        `when`(mockApi.getAttractionList(language, page)).thenReturn(mockCall)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as retrofit2.Callback<AttractionList>
            callback.onResponse(mockCall, response)
        }

        val future = RequestFuture<AttractionList>()
        getAttractionList(language, page, future)

        val actualAttractionList = future.get()
        assertEquals(attractionList.data[0].id, actualAttractionList.data[0].id)
        assertEquals(attractionList.data[0].name, actualAttractionList.data[0].name)
    }

    private fun generateDummyData(): AttractionList {
        val attractionList = AttractionList()
        attractionList.total = 425
        val dataList = arrayListOf<Data>()
        val data = Data()
        data.id = 548
        data.name = "Yangmingshan Guangfu Building"
        dataList.add(data)
        attractionList.data = dataList

        return attractionList
    }
}
