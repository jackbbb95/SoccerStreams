package com.jonathan.bogle.soccerstreams.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.adapter.CompAdapter.CompViewHolder
import com.jonathan.bogle.soccerstreams.model.Comp
import com.jonathan.bogle.soccerstreams.view.fragment.MatchFragment
import kotlinx.android.synthetic.main.adapter_comp.view.*


/**
 * Created by bogle on 4/11/17.
 */
class CompAdapter(val comps: ArrayList<Comp>, val is24h: Boolean, val fragment: MatchFragment): RecyclerView.Adapter<CompViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_comp, parent, false)
        return CompViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompViewHolder, position: Int) {
        holder.bindComp(comps, position)

    }

    override fun getItemCount(): Int {
       return comps.size
    }

    inner class CompViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindComp(comps: ArrayList<Comp>, pos: Int) {
            val comp = comps.get(pos)
            with(comp) {
                itemView.comp.text = compName
                Glide.with(itemView.context).load(compSrc).fitCenter().into(itemView.comp_src)

                itemView.comp_match_layout.setMatches(MatchAdapter(matches,is24h, fragment))

            }

        }



    }
}