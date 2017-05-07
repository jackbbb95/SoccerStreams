package com.jonathan.bogle.soccerstreams

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.animation.*
import android.widget.Toast


/**
 * Created by bogle on 4/13/17.
 */

//TODO put this in the base application class
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Activity.getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
    val height = displayMetrics.heightPixels
    return height
}

fun Activity.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    return width
}

fun Activity.getSlideUpAnimation(): LayoutAnimationController {
    val set = AnimationSet(true)

    // Fade in animation
    val fadeIn = AlphaAnimation(0.0f, 1.0f)
    fadeIn.duration = 400
    fadeIn.fillAfter = true
    set.addAnimation(fadeIn)

    // Slide up animation from bottom of screen
    val slideUp = TranslateAnimation(0f, 0f, getScreenHeight().toFloat(), 0f)
    slideUp.interpolator = DecelerateInterpolator(4f)
    slideUp.duration = 400
    set.addAnimation(slideUp)

    // Set up the animation controller              (second parameter is the delay)
    return LayoutAnimationController(set, 0.2f)
}

fun Activity.getSlideInAnimation(): LayoutAnimationController {
    val set = AnimationSet(true)

    // Fade in animation
    val fadeIn = AlphaAnimation(0.0f, 1.0f)
    fadeIn.duration = 400
    fadeIn.fillAfter = true
    set.addAnimation(fadeIn)

    // Slide up animation from bottom of screen
    val slideUp = TranslateAnimation(getScreenWidth().toFloat(), 0f, 0f, 0f)
    slideUp.interpolator = DecelerateInterpolator(4f)
    slideUp.duration = 400
    set.addAnimation(slideUp)

    // Set up the animation controller              (second parameter is the delay)
    return LayoutAnimationController(set, 0.2f)
}


fun Activity.getSlideOutAnimation(): LayoutAnimationController {
    val set = AnimationSet(true)

    // Fade in animation
    val fadeIn = AlphaAnimation(0.0f, 1.0f)
    fadeIn.duration = 400
    fadeIn.fillAfter = true
    set.addAnimation(fadeIn)

    // Slide up animation from bottom of screen
    val slideUp = TranslateAnimation(0f,getScreenWidth().toFloat(), 0f, 0f)
    slideUp.interpolator = DecelerateInterpolator(4f)
    slideUp.duration = 400
    set.addAnimation(slideUp)

    // Set up the animation controller              (second parameter is the delay)
    return LayoutAnimationController(set, 0.2f)
}



fun String.toNormalCase(): String {
    var result = ""
    for (i in 0..this.lastIndex)
        if (i == 0)
            result += this[0].toTitleCase()
        else
            result += this[i].toLowerCase()
    return result
}