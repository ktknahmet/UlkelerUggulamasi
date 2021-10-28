package com.ahmetaktekin.ulkeleruggulamasi.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahmetaktekin.ulkeleruggulamasi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//EXTENSİON EKLENTİ OLUŞTURMA

fun ImageView.downloadFromUrl(url:String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions().placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    //veriler gelene kadar ne gösterilecek
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}
fun placeholderProgressBar(context:Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        //özelliklerini yazarız
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}