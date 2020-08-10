package com.example.training

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestViews {

    @Test
    fun checkIfViewsHaveK(){

        val a = MyAdapter(emptyList(), ApplicationProvider.getApplicationContext())

        val s = a.getViews(55090)

        assertEquals(s, "55K")

    }

}