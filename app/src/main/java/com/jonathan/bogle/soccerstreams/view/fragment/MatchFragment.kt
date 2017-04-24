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
import com.jonathan.bogle.soccerstreams.presenter.IMainPresenter.OnGetMatchesListener
import com.jonathan.bogle.soccerstreams.presenter.MainPresenter
import com.jonathan.bogle.soccerstreams.toNormalCase
import com.jonathan.bogle.soccerstreams.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_match.*


/**
 * Created by bogle on 4/11/17.
 */
class MatchFragment: BaseFragment(), OnGetMatchesListener  {


    lateinit var mPresenter: MainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = (activity as MainActivity).mPresenter
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.getMatches()
        progressBar.visibility = View.VISIBLE

        recycler.layoutManager = GridLayoutManager(activity,1)
    }

    override fun onGetMatches(comps: ArrayList<Comp>) {
        progressBar.visibility = View.GONE

        recycler.setLayoutAnimation(activity.getSlideInAnimation())

        recycler.adapter = CompAdapter(comps,false,this) //24hr = true

    }

    companion object {

        val TAG = MatchFragment::class.java.name

        fun newInstance(): MatchFragment {
            return MatchFragment()
        }

    }

}