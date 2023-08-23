package com.walton.waltonicc.presenter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.walton.waltonicc.R
import com.walton.waltonicc.models.example.Detail
import com.walton.waltonicc.models.newsmodel.NewsModelItem
import com.walton.waltonicc.utils.Constants.TAG

class NewsListItemPresenter: Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_schedule, parent, false)

        val params = view.layoutParams
        params.width = getWidthPercentage(parent!!.context, 12)
        params.height = getHeightPercentage(parent.context, 32)

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
        val content = item as? NewsModelItem

        val txtTitleView = viewHolder?.view?.findViewById<TextView>(R.id.txtTitle)
        txtTitleView?.text = content?.context

        val txtCaption = viewHolder?.view?.findViewById<TextView>(R.id.txtCaption)
        txtCaption?.text = content?.cover_image_caption

        val imageView = viewHolder?.view?.findViewById<ImageView>(R.id.imgPosterSchedule)

        val url = "https://icc-worldcup.waltontvrni.com/public/storage/${content?.image_id}.jpg"


        imageView?.let {
            Glide.with(viewHolder?.view?.context!!)
                .load(url)
                .transform(CenterCrop(), RoundedCorners(2))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(it)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}