package com.jonathan.bogle.soccerstreams.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.model.Match
import com.jonathan.bogle.soccerstreams.toast
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import com.jonathan.bogle.soccerstreams.view.fragment.MatchFragment
import com.jonathan.bogle.soccerstreams.view.fragment.StreamDialogFragment
import kotlinx.android.synthetic.main.adapter_comp.view.*
import kotlinx.android.synthetic.main.adapter_match.view.*

/**
 * Created by bogle on 4/17/17.
 */
class MatchAdapter(val matches: ArrayList<Match>, val is24h: Boolean, val fragment: MatchFragment): BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val vh: MatchViewHolder?

        if(convertView == null) {
            view = LayoutInflater.from(fragment.context).inflate(R.layout.adapter_match, parent, false)
            vh = MatchViewHolder(view)
            view.setTag(vh)
        } else
            vh = convertView.tag as MatchViewHolder

        vh.bindMatch(matches.get(position),position)

        return view

    }

    override fun getItem(position: Int): Match {
        return matches.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return matches.size
    }










    inner class MatchViewHolder(val itemView: View) {

        fun bindMatch(match: Match, pos: Int) {
            with(match) {

                itemView.team1.text = team1
                Glide.with(itemView.context).load(team1Src).fitCenter().into(itemView.team1_src)

                itemView.team2.text = team2
                Glide.with(itemView.context).load(team2Src).fitCenter().into(itemView.team2_src)

                if (is24h) itemView.time.text = time24 else itemView.time.text = time12

                itemView.setOnClickListener {
                    val streamDialogFragment = StreamDialogFragment(match,is24h,(fragment.activity as MainActivity))
                    streamDialogFragment.show(fragment.fragmentManager,"Stream Fragment")
                    fragment.mPresenter.getStreams(match.link,streamDialogFragment)

                }
            }
        }
    }

}