package com.otto.paulus.footballmatch.presenter

import com.google.gson.Gson
import com.otto.paulus.footballmatch.api.ApiRepository
import com.otto.paulus.footballmatch.api.TheSportDBApi
import com.otto.paulus.footballmatch.model.Event
import com.otto.paulus.footballmatch.model.EventResponse
import com.otto.paulus.footballmatch.view.MatchListView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class MatchListPresenterTest {

    @Mock
    private
    lateinit var view: MatchListView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter:MatchListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchListPresenter(view, apiRepository, gson)
    }

    @Test
    fun getLast15EventsList() {

        val leagueId = 4328
        val events:MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        `when`(gson.fromJson(apiRepository
        .doRequest(TheSportDBApi.getLast15Events(leagueId)),
            EventResponse::class.java
        )).thenReturn(response)


        presenter.getLast15EventsList(leagueId)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()

    }

    @Test
    fun getNext15EventsList() {
        val leagueId = 4328
        val events:MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNext15Events(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)


        presenter.getNext15EventsList(leagueId)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()
    }
}