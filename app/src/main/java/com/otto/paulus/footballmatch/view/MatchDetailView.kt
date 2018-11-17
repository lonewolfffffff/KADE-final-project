package com.otto.paulus.footballmatch.view

import com.otto.paulus.footballmatch.model.EventDetail
import com.otto.paulus.footballmatch.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailEvent(data: EventDetail)
    fun showDetailTeam(data: Team, isHomeTeam: Boolean)
}