package com.jonathan.bogle.soccerstreams.view.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
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
import com.jonathan.bogle.soccerstreams.model.Stream
import com.jonathan.bogle.soccerstreams.presenter.IMainPresenter
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import kotlinx.android.synthetic.main.adapter_match.view.*
import kotlinx.android.synthetic.main.stream_dialog_fragment.*
import kotlinx.android.synthetic.main.stream_dialog_fragment.view.*


/**
 * Created by bogle on 4/17/17.
 */

class StreamDialogFragment(val match: Match, val is24h: Boolean, val activity: MainActivity): DialogFragment(), IMainPresenter.OnGetStreamsListener {

    var onlyMobile: Boolean = false //TODO make this a checkbox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.stream_dialog_fragment,container,false)
        with(match) {
            view.team1.text = team1
            Glide.with(view.context).load(team1Src).fitCenter().into(view.team1_src)

            view.team2.text = team2
            Glide.with(view.context).load(team2Src).fitCenter().into(view.team2_src)

            if (is24h) view.time.text = time24 else view.time.text = time12
        }

        view.stream_recycler.layoutManager = GridLayoutManager(activity,1)

        return view
    }

    override fun onGetStreams(streams: ArrayList<Stream>) {
        stream_progressBar.visibility = GONE

        if(streams.size < 1) {                                                                      //No Streams
            stream_no_streams_text.visibility = VISIBLE
        }else {                                                                                     //Display Streams
            stream_recycler.setLayoutAnimation(activity.getSlideUpAnimation())
            if (onlyMobile)                                                                         //weed out any non-mobile Streams
                stream_recycler.adapter =
                        StreamAdapter(ArrayList(streams.filter { stream -> stream.mobile }),activity)
            else                                                                                    //leave non-mobile links
                stream_recycler.adapter = StreamAdapter(streams,activity)
        }
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window.setLayout(width, height)
    }

}