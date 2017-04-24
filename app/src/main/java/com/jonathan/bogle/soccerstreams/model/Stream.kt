package com.jonathan.bogle.soccerstreams.model

/**
 * Created by bogle on 4/11/17.
 */
data class Stream (val title: String, val link: String, val type: String,
                   val language: String, val quality: String,
                   val mobile: Boolean, val rating: Int) {

    companion object {
        val SD = "SD"
        val HD = "HD"

        val CSS_QUERY = "tbody"

        val STREAM_KEY = "streamkey"
    }

}

