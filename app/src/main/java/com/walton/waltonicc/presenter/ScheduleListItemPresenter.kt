package com.walton.waltonicc.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.walton.waltonicc.R
import com.walton.waltonicc.models.example.Detail

class ScheduleListItemPresenter : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_news, parent, false)

        val params = view.layoutParams
        params.width = getWidthPercentage(parent!!.context, 6)
        params.height = getHeightPercentage(parent!!.context, 16)

        return ViewHolder(view)

    }

    fun getWidthPercentage(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.widthPixels ?: 0
        return (width * percent) / 100
    }

    fun getHeightPercentage(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.heightPixels ?: 0
        return (width * percent) / 100
    }


    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val content = item as? Detail

        val imageView = viewHolder?.view?.findViewById<ImageView>(R.id.imgPoster)

        val url = "https://www.themoviedb.org/t/p/w500" + content?.poster_path
        Glide.with(viewHolder?.view?.context!!).load(url).into(imageView!!)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}