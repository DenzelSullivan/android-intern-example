package com.sullivan.example.view

import android.os.IBinder
import android.view.WindowManager
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sullivan.example.R
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserFragmentTest {

    companion object {
        private const val inputName = "Denzel"
        private const val toastMessage = "Hello $inputName"
    }

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        val scenario = launchFragmentInContainer<UserFragment>()
        scenario.onFragment { fragment ->
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.userFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun enterNameButton_ActionResult() {
        onView(withId(R.id.nameEditText)).perform(typeText(inputName))
        closeSoftKeyboard()
        onView(withId(R.id.toastButton)).perform(click())

        onView(withId(R.id.enterNameTextView)).check(matches(withText(containsString(inputName))))
        onView(withId(R.id.nameEditText)).check(matches(not(isDisplayed())))
        onView(withId(R.id.toastButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.nextButton)).check(matches(isDisplayed()))
    }

    @Test
    fun startButton_ActionResult() {
        enterNameButton_ActionResult()

        onView(withId(R.id.nextButton)).perform(forceClick())
        onView(withText(toastMessage)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        assertEquals(R.id.homeFragment, navController.currentDestination?.id)
    }
}

class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description?) {
        description?.appendText("is toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        val type: Int? = item?.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder? = item.decorView?.windowToken
            val appToken: IBinder? = item.decorView?.applicationWindowToken
            return windowToken === appToken
        }
        return false
    }
}