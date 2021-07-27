package com.innovaocean.adoptmeapp.util

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisibleOrGone(evaluation: Boolean) {
    if (evaluation) {
        this.visible()
    } else {
        this.gone()
    }
}