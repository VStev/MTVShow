package com.submission.movieandtvshow.ui.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.utilities.EspressoIdlingResource
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest{

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadShows(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadMovies(){
        onView(withId(R.id.nav_movie)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadShowDetail(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(6))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(R.id.poster_image)).check(matches(isDisplayed()))
        onView(withId(R.id.title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.release_year)).check(matches(isDisplayed()))
        onView(withId(R.id.director_or_ongoing)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.not_found)).check(matches(not(isDisplayed())))
        onView(withId(R.id.data_found)).check(matches(isDisplayed()))
        onView(withId(R.id.nested_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.season_and_episode_count)).check(matches(isDisplayed()))
    }

    @Test
    fun loadShowDetailAndFav(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(6))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(R.id.poster_image)).check(matches(isDisplayed()))
        onView(withId(R.id.title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.release_year)).check(matches(isDisplayed()))
        onView(withId(R.id.director_or_ongoing)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.not_found)).check(matches(not(isDisplayed())))
        onView(withId(R.id.data_found)).check(matches(isDisplayed()))
        onView(withId(R.id.nested_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.season_and_episode_count)).check(matches(isDisplayed()))
        //adds to fav
        onView(withId(R.id.action_bookmark)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.nav_star_show)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(ViewActions.swipeUp())
        //remove fav
        onView(withId(R.id.nav_tv)).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(6))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(R.id.action_bookmark)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.nav_star_show)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.not_found)).check(matches(isDisplayed()))
    }

    @Test
    fun loadMovieDetail(){
        onView(withId(R.id.nav_movie)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(6))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(R.id.poster_image)).check(matches(isDisplayed()))
        onView(withId(R.id.title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.release_year)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.not_found)).check(matches(not(isDisplayed())))
        onView(withId(R.id.data_found)).check(matches(isDisplayed()))
        onView(withId(R.id.nested_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.season_and_episode_count)).check(matches(not(isDisplayed())))
    }

    @Test
    fun loadMovieDetailAndFav(){
        onView(withId(R.id.nav_movie)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(6))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(R.id.poster_image)).check(matches(isDisplayed()))
        onView(withId(R.id.title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.release_year)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.not_found)).check(matches(not(isDisplayed())))
        onView(withId(R.id.data_found)).check(matches(isDisplayed()))
        onView(withId(R.id.nested_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.season_and_episode_count)).check(matches(not(isDisplayed())))
        //adds to fav
        onView(withId(R.id.action_bookmark)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.nav_star_movie)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(ViewActions.swipeUp())
        //remove fav
        onView(withId(R.id.nav_movie)).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(6))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))
        onView(withId(R.id.action_bookmark)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.nav_star_movie)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.not_found)).check(matches(isDisplayed()))
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

}