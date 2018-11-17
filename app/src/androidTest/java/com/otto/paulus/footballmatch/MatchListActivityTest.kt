package com.otto.paulus.footballmatch

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.otto.paulus.footballmatch.activity.DetailActivity
import com.otto.paulus.footballmatch.activity.MainActivity
import com.otto.paulus.footballmatch.api.ApiRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MatchListActivityTest {
    @Rule
    @JvmField val activityTestRule = ActivityTestRule(MainActivity::class.java,false, false)

    @Rule
    @JvmField val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    lateinit var mockServer : MockWebServer

    @Before
    @Throws fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        ApiRepository.IS_MOCKED = true
        ApiRepository.MOCKED_SERVER_URL = mockServer.url("/").toString()

    }

    @Test
    fun shouldShowMatchList() {

            val mockResponse = MockResponse()
                    .setResponseCode(200)
                    .setBody(loadJSON(this.javaClass , "json/eventresponse_last15events.json"))

            mockServer.enqueue(mockResponse)

            val intent = Intent()
            activityTestRule.launchActivity(intent)

            onView(withId(R.id.rvMatchList)).check(matches(ViewMatchers.isDisplayed()))

            val request = mockServer.takeRequest()
            assertEquals("/",request.path)

    }

    @Test
    fun validateIntentOnListItemClick() {
        val mockMatchListResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass , "json/eventresponse_last15events.json"))

        mockServer.enqueue(mockMatchListResponse)

        val mockMatchDetailResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass , "json/eventdetailresponse_past_576537.json"))

        mockServer.enqueue(mockMatchDetailResponse)

        val intent = Intent()
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.rvMatchList)).perform(scrollToPosition<RecyclerView.ViewHolder>(14))
        onView(withId(R.id.rvMatchList)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))

        intended(allOf(
                hasExtra("MATCH_ID", "576537" ),
                hasExtra("HOME_TEAM_ID", "134777"),
                hasExtra("AWAY_TEAM_ID", "133626"),
                hasComponent(DetailActivity::class.java.name)
        ))
    }

    @Test
    fun shouldShowCorrectDetailActivityOnClick() {
        val mockMatchListResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass , "json/eventresponse_last15events.json"))

        mockServer.enqueue(mockMatchListResponse)

        val mockMatchDetailResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass , "json/eventdetailresponse_past_576537.json"))

        mockServer.enqueue(mockMatchDetailResponse)

        val intent = Intent()
        activityTestRule.launchActivity(intent)
        //val request1 = mockServer.takeRequest()

        onView(withId(R.id.rvMatchList)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))
        //val request2 = mockServer.takeRequest()

        onView(withId(R.id.tvHomeTeamName)).check(matches(withText("Newcastle")))
        onView(withId(R.id.tvAwayTeamName)).check(matches(withText("Leicester")))

    }

    @After
    @Throws fun tearDown() {
        mockServer.shutdown()
    }
}