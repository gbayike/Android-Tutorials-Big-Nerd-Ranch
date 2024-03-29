package com.olugbayike.android.geoquiz

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }

    @Test
    fun showFirstQuestionOnLaunch(){
        onView(withId(R.id.text))
            .check(matches(withText(R.string.question_text_australia)))
    }

    @Test
    fun showSecondQuestionAfterNextPress(){
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.text))
            .check(matches(withText(R.string.question_oceans)))
    }

    @Test
    fun handleActivitiesRecreation() {
        onView(withId(R.id.next_button)).perform(click())
        scenario.recreate()
        onView(withId(R.id.text))
            .check(matches(withText(R.string.question_oceans)))
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}