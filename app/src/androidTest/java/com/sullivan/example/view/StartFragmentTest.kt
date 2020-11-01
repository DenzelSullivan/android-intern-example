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
class StartFragmentTest {

    companion object {
        private const val inputName = "Denzel"
        private const val message = "Hello $inputName"
    }

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        val scenario = launchFragmentInContainer<StartFragment>()
        scenario.onFragment { fragment ->
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.startFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun changeTitleButton_ActionResult(){
        onView(withId(R.id.nameEditText)).perform(typeText(inputName))
        closeSoftKeyboard()
        onView(withId(R.id.changeTitleButton)).perform(click())

        onView(withId(R.id.enterNameTextView)).check(matches(withText(message)))
    }

    @Test
    fun showToastButton_ActionResult(){
        onView(withId(R.id.nameEditText)).perform(typeText(inputName))
        closeSoftKeyboard()
        onView(withId(R.id.toastButton)).perform(click())

        //onView(withText(message)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun showSnackBarButton_ActionResult(){
        // TODO: Complete Test
    }

    @Test
    fun sendNotificationButton_ActionResult(){
        // TODO: Complete Test
    }

    @Test
    fun nextButton_ActionResult(){
        onView(withId(R.id.nextButton)).perform(click())

        assertEquals(R.id.homeFragment, navController.currentDestination?.id)
    }
}

@Suppress("DEPRECATION")
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