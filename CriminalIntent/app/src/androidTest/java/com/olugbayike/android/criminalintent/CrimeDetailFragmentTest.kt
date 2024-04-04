package com.olugbayike.android.criminalintent

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.Companion.launch
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.w3c.dom.Text
import java.util.Date
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class CrimeDetailFragmentTest {

//    private lateinit var scenario: FragmentScenario<CrimeDetailFragment>
//    private var fragment: CrimeDetailFragment = CrimeDetailFragment()
//    private lateinit var crime: Crime
//    @Before
//    fun setUp() {
//        scenario = launchFragmentInContainer(themeResId = R.style.Theme_CriminalIntent)
//        scenario.moveToState(Lifecycle.State.STARTED)
//        crime = Crime(
//            id = UUID.randomUUID(),
//            title = "",
//            date = Date(),
//            isSolved = false
//        )
//    }
//
//    @Test
//    fun testCheckbox(){
//        scenario.onFragment {fragment ->
////            var isSolved: String = it.crime.isSolved as String
//            fragment.crime = crime
//
//            val crimeSolved = fragment.binding.crimeSolved
//            crimeSolved.isChecked = true
//
//            assertTrue(fragment.crime.isSolved)
//        }
//    }
//
//    @Test
//    fun testEditText(){
//
//        scenario.onFragment{
//            it.crime = crime
//
//            val titleEditText = it.binding.crimeTitle
//            titleEditText.setText("Test Crime Title")
//
//            assertEquals("Test Crime Title", it.crime.title)
//        }
//    }
//
//
//    @After
//    fun tearDown() {
//        scenario.close()
//    }
}