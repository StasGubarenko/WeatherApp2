package com.bignerdranch.android.weatherapp.presentation.fragments.search

import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.presentation.MainActivity
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchFragmentTest{

    private lateinit var activity: MainActivity

    @Before
    fun setup(){
        activity = Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
    }

    @Test
    fun test(){
        assertNotNull(activity)
    }

    @Test
    fun check_display_activity(){
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        val idSearchFragment = fragmentManager.findFragmentById(R.id.fragmentContainerView)
        assertNotNull(idSearchFragment)
    }

}