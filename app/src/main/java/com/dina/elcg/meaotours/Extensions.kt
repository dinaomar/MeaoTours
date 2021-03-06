package com.dina.elcg.meaotours

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(uri: Any?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.mipmap.ic_launcher_round)
        .override(600,400)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}
