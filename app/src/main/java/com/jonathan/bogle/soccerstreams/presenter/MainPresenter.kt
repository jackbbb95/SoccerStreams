package com.jonathan.bogle.soccerstreams.presenter

import com.jonathan.bogle.soccerstreams.model.Match
import com.jonathan.bogle.soccerstreams.model.Stream
import io.reactivex.Observable

/**
* Created by bogle on 4/11/17.
*/

interface MainPresenter {

    fun getMatches(): Observable<ArrayList<Match>>

    fun getStreams(matchLink: String): Observable<ArrayList<Stream>>

}