package com.jonathan.bogle.soccerstreams.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.presenter.IMainPresenter
import com.jonathan.bogle.soccerstreams.presenter.MainPresenter
import com.jonathan.bogle.soccerstreams.view.fragment.MatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mPresenter: MainPresenter by lazy {
        IMainPresenter(this)
    }

    lateinit var mFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        mFragment = MatchFragment.newInstance()

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, mFragment, MatchFragment.TAG)
                .commit()

    }

}
