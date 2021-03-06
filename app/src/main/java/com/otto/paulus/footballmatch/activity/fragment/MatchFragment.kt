package com.otto.paulus.footballmatch.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.otto.paulus.footballmatch.R
import org.jetbrains.anko.AnkoLogger

class MatchFragment : Fragment(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_matches, container, false)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }
}