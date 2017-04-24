package com.jonathan.bogle.soccerstreams.presenter

import android.net.Network
import android.os.AsyncTask
import com.jonathan.bogle.soccerstreams.model.Comp
import com.jonathan.bogle.soccerstreams.model.Match
import com.jonathan.bogle.soccerstreams.model.NetworkUtils
import com.jonathan.bogle.soccerstreams.model.Stream
import com.jonathan.bogle.soccerstreams.toNormalCase
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import com.jonathan.bogle.soccerstreams.view.fragment.MatchFragment
import com.jonathan.bogle.soccerstreams.view.fragment.StreamDialogFragment
import org.jsoup.Jsoup
import java.io.IOException

/**
 * Created by bogle on 4/11/17.
 */
class IMainPresenter(val activity: MainActivity): MainPresenter{



    override fun getMatches() { GetMatchesAsync().execute(activity) }

    interface OnGetMatchesListener {
        fun onGetMatches(comps: ArrayList<Comp>)
    }

    private class GetMatchesAsync: AsyncTask<MainActivity?, Void, ArrayList<Match>>() {

        var activity: MainActivity? = null

        override fun doInBackground(vararg params: MainActivity?): ArrayList<Match>? {
            activity = params[0]
            var matches: ArrayList<Match>? = null
            try {
                //Load site html
                val response = Jsoup.connect(Match.URL)
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .execute();
                //Get relevant data
                val elements = response.parse()
                        .select(Match.CSS_QUERY)
                        .first()
                        .children()
                        .filter {elem -> elem.hasText() && elem.select("span").hasAttr("data-eventtime")}

                //Create Match objects
                matches = ArrayList(elements.map { elem ->
                    val time24 = Match.formattedDateFromString("yyyy-MM-dd hh:mm:ss","kk:mm", elem.select("span").first().attr("data-eventtime"))
                    val time12 = Match.formattedDateFromString("yyyy-MM-dd hh:mm:ss","hh:mm a", elem.select("span").first().attr("data-eventtime"))

                    val comp = elem.select("p").first().text()
                    val compSrc = NetworkUtils.HTTP + elem.select("img").first().attr("src").replace("/small","")

                    val team1 = elem.select("img").get(1).attr("alt")
                    val team1Src = NetworkUtils.HTTP + elem.select("img").get(1).attr("src").replace("/small","")
                    val team2 = elem.select("img").get(2).attr("alt")
                    val team2Src = NetworkUtils.HTTP + elem.select("img").get(2).attr("src").replace("/small","")

                    val link = elem.select("a").first().attr("href")

                    Match(time24,time12,comp, compSrc, team1, team1Src, team2, team2Src, link)
                })
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return matches
        }

        override fun onPostExecute(matches: ArrayList<Match>) {
            val comps = ArrayList<Comp>()
            val compStrings = ArrayList<String>()
            matches.forEach { match ->
                if(!compStrings.contains(match.comp)) {
                    compStrings.add(match.comp)
                    val curComp = ArrayList<Match>()
                    curComp.add(match)
                    comps.add(Comp(match.comp,match.compSrc, curComp))
                } else {
                    val curComp = comps.first { comp -> comp.compName == match.comp }
                    curComp.matches.add(match)
                }
            }
            (activity?.mFragment as MatchFragment).onGetMatches(comps)
        }

    }

    override fun getStreams(matchLink: String, streamFrag: StreamDialogFragment) {
        GetStreamsAsync().execute(Pair(matchLink,streamFrag))
    }

    interface OnGetStreamsListener {
        fun onGetStreams(streams: ArrayList<Stream>)
    }

    private class GetStreamsAsync: AsyncTask<Pair<String,StreamDialogFragment>, Void, ArrayList<Stream>>() {
            var frag: StreamDialogFragment? = null
        override fun doInBackground(vararg params: Pair<String, StreamDialogFragment>): ArrayList<Stream> {
            val link = params[0].first
            frag = params[0].second
            var streams = ArrayList<Stream>()

            try {
                //Load site html
                val response = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .execute();

                //Get relevant data
                val elements = response.parse()
                        .select(Stream.CSS_QUERY)
                        .first()
                        .children()
                            .filter {elem -> elem.hasText() && elem.select("tr").hasAttr("data-href")}

                streams = ArrayList(elements.map { elem ->
                    //TODO configure the stream
                    val title = elem.select("td").get(5).ownText()
                    val streamLink = elem.attr("data-href")
                    val type = elem.attr("data-type")
                    val lang = elem.attr("data-language").toNormalCase()
                    val qual = elem.attr("data-quality")
                    val mobile = (elem.attr("data-mobile") == "Yes")
                    val rating = elem.select("span").first().text().toInt()

                    Stream(title,streamLink,type,lang,qual,mobile,rating)
                })

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return streams
        }

        override fun onPostExecute(streams: ArrayList<Stream>) {
            (frag as StreamDialogFragment).onGetStreams(streams)
        }
    }

}