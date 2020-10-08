package com.sullivan.example.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sullivan.example.R
import com.sullivan.example.view.adapter.DataViewHolder
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        val scenario = launchFragmentInContainer<HomeFragment>()
        scenario.onFragment { fragment ->
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.homeFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        runBlocking {
            delay(500)
        }
    }

    @Test
    fun scrollToItem_checkItsText() {
        onView(withId(R.id.dataRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DataViewHolder>(0, forceClick())
        )
        onView(withText("Beef")).check(matches(isDisplayed()))
    }

    //Todo: Add tests that use the other recyclerViewActions ie
    // scrollTo(), scrollToPosition(), actionOnItem(), actionOnItemAtPosition()
}