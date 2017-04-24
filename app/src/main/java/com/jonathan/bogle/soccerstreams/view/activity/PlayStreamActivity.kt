package com.jonathan.bogle.soccerstreams.view.activity

import android.annotation.TargetApi
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jonathan.bogle.soccerstreams.R
import com.jonathan.bogle.soccerstreams.model.Stream
import kotlinx.android.synthetic.main.activity_play_stream.*

/**
 * Created by bogle on 4/20/17.
 */

class PlayStreamActivity : AppCompatActivity() {

    lateinit var streamURL: String

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_stream)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()

        streamURL = intent.getStringExtra(Stream.STREAM_KEY)

        //Hide Status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //Play the video
        initVideoView()
    }

    private fun initVideoView() {
        webview.settings.javaScriptEnabled = true
        webview.setPadding(0, 0, 0, 0)
        webview.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webview.isScrollbarFadingEnabled = false
        val newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
        webview.settings.userAgentString = newUA
        webview.setWebViewClient(InsideWebViewClient())
        webview.loadUrl(streamURL)
        //TODO Try and find and use embed codes
    }

    private inner class InsideWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return true
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (webview.canGoBack()) {
            webview.goBack()
        }
    }

    public override fun onPause() {
        super.onPause()
        webview.loadUrl("about:blank")
        //finish();
    }

    public override fun onResume() {
        super.onResume()
        if (webview.url != null && webview.url == "about:blank")
            webview.loadUrl(streamURL)
    }

    public override fun onStop() {
        super.onStop()
        //Show statusbar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

    }

}
