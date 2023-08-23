package com.walton.waltonicc.view

import android.os.Bundle
import android.util.Log
import com.walton.waltonicc.R
import androidx.fragment.app.FragmentActivity
import com.walton.waltonicc.models.example.Detail
import com.walton.waltonicc.models.newsmodel.NewsModelItem
import com.walton.waltonicc.repository.DetailDataRepository
import com.walton.waltonicc.repository.NewsDataRepository
import com.walton.waltonicc.utils.Constants.TAG

class DetailsActivity : FragmentActivity() {

    lateinit var dataClass : Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val className = intent.getStringExtra("class_name")

        if (className.equals("Detail")){
            dataClass = DetailDataRepository.detailItem!!
            Log.d(TAG, "onCreate: ${(dataClass as Detail).title}")
        }else if(className.equals("NewsItem")){
            dataClass = NewsDataRepository.newsItem!! as NewsModelItem
            Log.d(TAG, "onCreate: ${(dataClass as NewsModelItem).title}")
        }
    }
}