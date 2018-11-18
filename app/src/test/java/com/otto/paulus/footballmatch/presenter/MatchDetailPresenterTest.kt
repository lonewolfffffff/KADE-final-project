package com.otto.paulus.footballmatch.presenter

import com.google.gson.Gson
import com.otto.paulus.footballmatch.api.ApiRepository
import com.otto.paulus.footballmatch.api.TheSportDBApi
import com.otto.paulus.footballmatch.model.EventDetail
import com.otto.paulus.footballmatch.model.EventDetailResponse
import com.otto.paulus.footballmatch.model.Team
import com.otto.paulus.footballmatch.model.TeamResponse
import com.otto.paulus.footballmatch.view.MatchDetailView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter:MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson)
    }

    @Test
    fun getEventDetail() {
        val eventId = "441613"
        val events:List<EventDetail> = listOf(EventDetail("1","27/10/2018","1","Liverpool","null","null","null","null","null","null","null","null","null","2","Burnley","null","null","null","null","null","null","null","null","null"))
        val response = EventDetailResponse(events)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(eventId)),
                EventDetailResponse::class.java
        )).thenReturn(response)

        presenter.getEventDetail(eventId)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showDetailEvent(response.events.get(0))
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getTeamDetail() {
        val teamId = "1"
        val isHomeTeam = false
        val teams:List<Team> = listOf(Team())
        val response = TeamResponse(teams)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamDetail(teamId,isHomeTeam)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showDetailTeam(response.teams.get(0), isHomeTeam)
        Mockito.verify(view).hideLoading()
    }
}