package com.jonathan.bogle.soccerstreams.view.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.adapter.CompAdapter
import com.jonathan.bogle.soccerstreams.getSlideInAnimation
import com.jonathan.bogle.soccerstreams.getSlideUpAnimation
import com.jonathan.bogle.soccerstreams.model.Comp
import com.jonathan.bogle.soccerstreams.model.Match
import com.jonathan.bogle.soccerstreams.presenter.MainPresenter
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_match.*


/**
 * Created by bogle on 4/11/17.
 */
class MatchFragment: BaseFragment() {

    lateinit var mPresenter: MainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = (activity as MainActivity).mPresenter
        mPresenter.getMatches()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        { matches ->
                            progressBar.visibility = View.GONE
                            recycler.layoutAnimation = activity.getSlideUpAnimation()
                            recycler.adapter = CompAdapter(getComps(matches),false,this) //24hr = true
                        },
                        //onError
                        { error ->
                            error.printStackTrace()
                        }
                )

        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        recycler.layoutManager = GridLayoutManager(activity,1)
    }

    private fun getComps(matches: ArrayList<Match>): ArrayList<Comp> {
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
        return comps
    }


    companion object {

        val TAG = MatchFragment::class.java.name

        fun newInstance(): MatchFragment {
            return MatchFragment()
        }

    }

}