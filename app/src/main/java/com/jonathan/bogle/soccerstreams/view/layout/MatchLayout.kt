package com.jonathan.bogle.soccerstreams.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.jonathan.bogle.soccerstreams.adapter.MatchAdapter


/**
 * Created by bogle on 4/17/17.
 */
class MatchLayout : RelativeLayout {

    private var matchAdapter: MatchAdapter? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    fun setMatches(matchAdapter: MatchAdapter) {
        this.matchAdapter = matchAdapter

        //Popolute MatchAdapter
        if (this.matchAdapter != null) {
            for (i in 0..this.matchAdapter!!.getCount() - 1) {
                val item = matchAdapter.getView(i, null, null)
                item!!.id = i + 1 //starting at 0 doesn't work
                if(item.id > 1) {
                    val params = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    params.addRule(RelativeLayout.BELOW, i) //the id of the item above is i
                    this.addView(item,params)
                } else
                    this.addView(item)
            }
        }

    }
}