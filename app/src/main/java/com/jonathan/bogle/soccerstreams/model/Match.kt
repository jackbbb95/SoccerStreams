package com.jonathan.bogle.soccerstreams.model

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bogle on 4/11/17.
 */

data class Match (val time24: String, val time12: String,
                  val comp: String, val compSrc: String,
                  val team1: String, val team1Src: String,
                  val team2: String, val team2Src: String,
                  val link: String) {

    companion object {
        val URL: String = "https://soccerstreams.net/"
        val CSS_QUERY = "tbody"

        val FAKE1 = Match("TIME","TIME","COMP","https://cdn.soccerstreams.net/images/generic.png",
                "TEAM1","https://cdn.soccerstreams.net/images/generic.png",
                "TEAM2","https://cdn.soccerstreams.net/images/generic.png","LINK")

        fun formattedDateFromString(inputFormat: String, outputFormat: String, inputDate: String): String {
            var inFormat = inputFormat
            var outFormat = outputFormat

            if (inFormat == "") inFormat = "yyyy-MM-dd hh:mm:ss"
            if (outFormat == "") outFormat = "EEEE d 'de' MMMM 'del' yyyy"

            val df_input = SimpleDateFormat(inFormat,Locale.getDefault())
            df_input.timeZone = TimeZone.getTimeZone("UTC") //source provides UTC times
            val df_output = SimpleDateFormat(outFormat, java.util.Locale.getDefault())

            var outputDate = ""
            try {
                val parsed = df_input.parse(inputDate)
                outputDate = df_output.format(parsed)
            } catch (e: Exception) {
                Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.message)
            }

            return outputDate

        }
    }


}