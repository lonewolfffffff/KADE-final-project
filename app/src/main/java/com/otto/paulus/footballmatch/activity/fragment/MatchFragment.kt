package com.otto.paulus.footballmatch.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otto.paulus.footballmatch.R
import org.jetbrains.anko.AnkoLogger

class MatchFragment : Fragment(), AnkoLogger {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_matches, container, false)

        return rootView
    }
}