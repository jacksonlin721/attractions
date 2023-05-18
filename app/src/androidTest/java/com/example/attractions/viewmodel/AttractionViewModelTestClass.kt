package com.example.attractions.viewmodel

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class AttractionViewModelTestClass {

    private lateinit var context: Context
    private lateinit var attractionViewModel: AttractionViewModel

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        attractionViewModel = AttractionViewModel()
    }

    @Test
    fun testGetStringByLocale() {
        // Set the desired language for the test
        val desiredLanguage = "zh-tw"

        // Create a new configuration with the desired locale
        val desiredLocale = Locale(desiredLanguage)
        val configuration = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(desiredLocale)
        } else {
            configuration.locale = desiredLocale
        }

        // Create a new resources object with the updated configuration
        val resources: Resources = context.resources
        val updatedResources = Resources(resources.assets, resources.displayMetrics, configuration)

        // Call the getStringByLocale function with the desired string resource
        val strRes = com.example.attractions.R.string.str_address
        val result = attractionViewModel.getStringByLocale(strRes, context)

        // Get the expected string from the updated resources
        val expected = updatedResources.getString(strRes)

        // Assert that the result matches the expected string
        assertEquals(expected, result)
    }
}
