package com.sullivan.example.view

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sullivan.example.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserFragmentTest {

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        val scenario = launchFragmentInContainer<UserFragment>()
        scenario.onFragment { fragment ->
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun enterNameButton_ActionResult() {
        val name = "Denzel"

        onView(withId(R.id.nameEditText)).perform(typeText(name))
        closeSoftKeyboard()
        onView(withId(R.id.enterNameButton)).perform(click())

        onView(withId(R.id.nameTextView)).check(matches(withText(containsString(name))))
        onView(withId(R.id.nameEditText)).check(matches(not(isDisplayed())))
        onView(withId(R.id.enterNameButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.startButton)).check(matches(isDisplayed()))
    }

    @Test
    fun startButton_ActionResult() {
        enterNameButton_ActionResult()

        onView(withId(R.id.startButton)).perform(forceClick())
        assertEquals(R.id.homeFragment, navController.currentDestination?.id)
    }

    private fun forceClick(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(isClickable(), isEnabled(), isDisplayed())
            }

            override fun getDescription(): String {
                return "force click"
            }

            override fun perform(uiController: UiController, view: View) {
                view.performClick()
                uiController.loopMainThreadUntilIdle()
            }
        }
    }
}