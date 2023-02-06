package com.satdev.prueba_ceiba

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule

import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun user_item_to_post_navigation(){
        //assertLoadingDialog()

        assertFirstScreen()

        navigateToPostView()


        assertSecondScreen()

    }

    private fun assertSecondScreen() {
        Espresso.onView(withId(R.id.post_title)).check(matches(isDisplayed()))
    }

    private fun navigateToPostView() {

        onView(withId(R.id.user_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickOnViewChild(R.id.btn_go_to_post)))



    }

    private fun assertFirstScreen() {
        Espresso.onView(withId(R.id.user_list)).check(matches(isDisplayed()))
    }


    fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) = click().perform(uiController, view.findViewById<View>(viewId))
    }
}