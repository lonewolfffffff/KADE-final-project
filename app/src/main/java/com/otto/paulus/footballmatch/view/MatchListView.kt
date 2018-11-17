package com.otto.paulus.footballmatch.view

import com.otto.paulus.footballmatch.model.Event

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}