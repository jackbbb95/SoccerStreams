package com.jonathan.bogle.soccerstreams.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.model.Stream
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import com.jonathan.bogle.soccerstreams.view.activity.PlayStreamActivity
import kotlinx.android.synthetic.main.adapter_stream.view.*
import android.support.v4.content.ContextCompat.startActivity



/**
 * Created by bogle on 4/18/17.
 */
class StreamAdapter(val streams: ArrayList<Stream>, val activity: MainActivity): RecyclerView.Adapter<StreamAdapter.StreamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_stream, parent, false)
        return StreamViewHolder(view)
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        holder.bindStream(streams.get(position))
    }

    override fun getItemCount(): Int {
        return streams.size
    }

    inner class StreamViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindStream(stream: Stream) {
            itemView.rating.text = stream.rating.toString()
            itemView.title.text = stream.title
            itemView.type.text = stream.type
            itemView.quality.text = stream.quality
            itemView.language.text = stream.language
            if(stream.mobile) itemView.mobile.visibility = View.VISIBLE
            else itemView.mobile.visibility = View.INVISIBLE

            itemView.setOnClickListener {view ->
                val intent = Intent(activity, PlayStreamActivity::class.java)
                intent.putExtra(Stream.STREAM_KEY, stream.link)
                activity.startActivity(intent)
            }
        }



    }
}