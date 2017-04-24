package com.jonathan.bogle.soccerstreams.presenter

import com.jonathan.bogle.soccerstreams.view.fragment.StreamDialogFragment

/**
* Created by bogle on 4/11/17.
*/

interface MainPresenter {

    fun getMatches()

    fun getStreams(matchLink: String, streamFrag: StreamDialogFragment)

}