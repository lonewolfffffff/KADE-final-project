package com.otto.paulus.footballmatch.presenter

import android.provider.Settings
import com.google.gson.Gson
import com.otto.paulus.footballmatch.api.ApiRepository
import com.otto.paulus.footballmatch.api.TheSportDBApi
import com.otto.paulus.footballmatch.model.EventDetailResponse
import com.otto.paulus.footballmatch.model.TeamResponse
import com.otto.paulus.footballmatch.view.MatchDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson):AnkoLogger {
    fun getEventDetail(eventId: String) {
        view.showLoading()
        GlobalScope.launch {
            val data = async {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEventDetail(eventId)),
                        EventDetailResponse::class.java
                )
            }
            view.showDetailEvent(data.await().events.get(0))
            view.hideLoading()
        }
    }

    fun getTeamDetail(teamId: String, isHomeTeam: Boolean=true) {
        view.showLoading()
        GlobalScope.launch {
            val data = async {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java
                )
            }
            view.showDetailTeam(data.await().teams.get(0), isHomeTeam)
            view.hideLoading()
        }
    }
}