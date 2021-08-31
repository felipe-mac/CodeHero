package com.app.codehero.utils

import android.widget.ImageView
import com.app.codehero.domain.model.Thumbnail
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

import java.security.MessageDigest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadFromUrl(thumbnail: Thumbnail) {
    val url = thumbnail.path + "/standard_medium." + thumbnail.extension
    Glide.with(this)
        .load(url)
        .circleCrop()
        .transition(withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun String.toMD5(): String {
    val md5 = "MD5"
    val digest: MessageDigest = MessageDigest.getInstance(md5)
    digest.update(this.toByteArray())
    val messageDigest: ByteArray = digest.digest()

    val hexString = StringBuilder()
    for (aMessageDigest in messageDigest) {
        var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
        while (h.length < 2) h = "0$h"
        hexString.append(h)
    }
    return hexString.toString()
}
