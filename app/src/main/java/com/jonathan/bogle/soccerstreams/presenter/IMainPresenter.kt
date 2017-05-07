package com.jonathan.bogle.soccerstreams.presenter

import com.jonathan.bogle.soccerstreams.model.Match
import com.jonathan.bogle.soccerstreams.model.NetworkUtils
import com.jonathan.bogle.soccerstreams.model.Stream
import com.jonathan.bogle.soccerstreams.toNormalCase
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import io.reactivex.Observable
import org.jsoup.Jsoup
import java.io.IOException


/**
 * Created by bogle on 4/11/17.
 */
class IMainPresenter(val activity: MainActivity): MainPresenter{

    override fun getMatches(): Observable<ArrayList<Match>> {
    //    GetMatchesAsync().execute(activity)
        return Observable.create{ subscriber ->
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
                subscriber.onNext(ArrayList(elements.map { elem ->
                    val time24          = Match.formattedDateFromString("yyyy-MM-dd hh:mm:ss","kk:mm", elem.select("span").first().attr("data-eventtime"))
                    val time12          = Match.formattedDateFromString("yyyy-MM-dd hh:mm:ss","hh:mm a", elem.select("span").first().attr("data-eventtime"))
                    val comp            = elem.select("p").first().text()
                    val compSrc         = NetworkUtils.HTTP + elem.select("img").first().attr("src").replace("/small","")
                    val team1           = elem.select("img").get(1).attr("alt")
                    val team1Src        = NetworkUtils.HTTP + elem.select("img").get(1).attr("src").replace("/small","")
                    val team2           = elem.select("img").get(2).attr("alt")
                    val team2Src        = NetworkUtils.HTTP + elem.select("img").get(2).attr("src").replace("/small","")

                    val link = elem.select("a").first().attr("href")

                    Match(time24,time12,comp, compSrc, team1, team1Src, team2, team2Src, link)
                }))
            } catch (e: IOException) {
                subscriber.onError(e)
            } finally {
                subscriber.onComplete()
            }
        }
    }


    override fun getStreams(matchLink: String): Observable<ArrayList<Stream>> {
        //GetStreamsAsync().execute(Pair(matchLink,streamFrag))
        return Observable.create { subscriber ->
            try {
                //Load site html
                val response = Jsoup.connect(matchLink)
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

                subscriber.onNext(ArrayList(elements.map { elem ->
                    val title           = elem.select("td").get(5).ownText()
                    val streamLink      = elem.attr("data-href")
                    val type            = elem.attr("data-type")
                    val lang            = elem.attr("data-language").toNormalCase()
                    val qual            = elem.attr("data-quality")
                    val mobile          = (elem.attr("data-mobile") == "Yes")
                    val rating          = elem.select("span").first().text().toInt()

                    Stream(title,streamLink,type,lang,qual,mobile,rating)
                }))
            } catch (e: IOException) {
                subscriber.onError(e)
            } finally {
                subscriber.onComplete()
            }
        }
    }
}