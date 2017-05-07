package com.jonathan.bogle.soccerstreams.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.adapter.StreamAdapter
import com.jonathan.bogle.soccerstreams.getSlideUpAnimation
import com.jonathan.bogle.soccerstreams.model.Match
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adapter_match.view.*
import kotlinx.android.synthetic.main.stream_fragment.*
import kotlinx.android.synthetic.main.stream_fragment.view.*


/**
 * Created by bogle on 4/17/17.
 */

class StreamFragment(val match: Match, val is24h: Boolean, val activity: MainActivity): Fragment() {

    var onlyMobile: Boolean = false //TODO make this a checkbox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.stream_fragment,container,false)
        with(match) {
            view.team1.text = team1
            Glide.with(view.context).load(team1Src).fitCenter().into(view.team1_src)

            view.team2.text = team2
            Glide.with(view.context).load(team2Src).fitCenter().into(view.team2_src)

            if (is24h) view.time.text = time24 else view.time.text = time12
        }

        view.stream_recycler.layoutManager = GridLayoutManager(activity,1)

        activity.mPresenter.getStreams(match.link).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        { streams ->
                            stream_progressBar.visibility = GONE

                            if(streams.size < 1) {                                                                      //No Streams
                                stream_no_streams_text.visibility = VISIBLE
                            }else {                                                                                     //Display Streams
                                stream_recycler.layoutAnimation = activity.getSlideUpAnimation()
                                if (onlyMobile)                                                                         //weed out any non-mobile Streams
                                    stream_recycler.adapter =
                                            StreamAdapter(ArrayList(streams.filter { stream -> stream.mobile }),activity)
                                else                                                                                    //leave non-mobile links
                                    stream_recycler.adapter = StreamAdapter(streams,activity)
                            }
                        },
                        //onError
                        { error ->
                            error.printStackTrace()
                        }
                )
        return view
    }

}