package com.otto.paulus.footballmatch.activity.fragment

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.*
import com.otto.paulus.footballmatch.R

class FavoriteFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_favorites, container, false)
        return rootView
    }
}