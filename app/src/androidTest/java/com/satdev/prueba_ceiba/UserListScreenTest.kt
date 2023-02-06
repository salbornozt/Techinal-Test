package com.satdev.prueba_ceiba

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


class UserListScreenTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    companion object {
        val SEARCH_TEXT = "Ervin Howell"
    }

    @Test
    fun search_input_shows_item(){


        assertFirstScreen()

        writeNameInEditText(SEARCH_TEXT)

        assertSearchedItemIsShowed()



    }

    @Test
    fun search_input_shows_empty_text(){

        assertFirstScreen()

        writeNameInEditText("pruebas vacias")

        assertEmptyText()



    }

    private fun assertEmptyText() {
        onView(ViewMatchers.withId(R.id.empty_list_message)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    private fun assertSearchedItemIsShowed() {
        onView(withId(R.id.user_list))
            .check(matches(hasItemAtPosition(0, hasDescendant(withText(SEARCH_TEXT)))));

    }

    private fun writeNameInEditText(text:String) {
        onView(withId(R.id.user_list_search))
        .perform(clearText(), typeText(text));
    }

    private fun assertFirstScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.user_list)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    private fun assertLoadingDialog() {
        Espresso.onView(ViewMatchers.withText(R.string.progress_dialog_title)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    private fun hasItemAtPosition(position: Int, matcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has item at position $position : ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    ?: return false
                return matcher.matches(viewHolder.itemView)
            }
        }
    }
}