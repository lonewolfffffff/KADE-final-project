package com.otto.paulus.footballmatch.presenter

import com.google.gson.Gson
import com.otto.paulus.footballmatch.api.ApiRepository
import com.otto.paulus.footballmatch.api.TheSportDBApi
import com.otto.paulus.footballmatch.model.EventResponse
import com.otto.paulus.footballmatch.view.MatchListView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.AnkoLogger

class MatchListPresenter(private val view: MatchListView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson) : AnkoLogger {

    fun getLast15EventsList(leagueId: Int?) {
        view.showLoading()
        GlobalScope.launch {
            val data = async {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getLast15Events(leagueId)),
                        EventResponse::class.java
                )
            }
            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    fun getNext15EventsList(leagueId: Int?) {
        view.showLoading()
        GlobalScope.launch {
            val data = async {

                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNext15Events(leagueId)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }
}