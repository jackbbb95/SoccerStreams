package com.jonathan.bogle.soccerstreams.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by bogle on 4/11/17.
 */
abstract class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}