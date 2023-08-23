package com.walton.waltonicc.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.walton.waltonicc.R
import com.walton.waltonicc.models.example.DataModel
import com.walton.waltonicc.models.example.Detail
import com.walton.waltonicc.models.newsmodel.NewsModelItem
import com.walton.waltonicc.repository.DetailDataRepository
import com.walton.waltonicc.repository.NewsDataRepository
import com.walton.waltonicc.utils.Constants.TAG
import com.walton.waltonicc.utils.NetworkResult
import com.walton.waltonicc.viewmodel.NewsViewModel
import com.walton.waltonicc.viewmodel.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var txtBannerTitle: TextView
    private lateinit var txtBannerSubtitle: TextView
    private lateinit var txtOverviewBanner: TextView
    private lateinit var imgBanner: ImageView

    lateinit var rowsListFragment: RowsListFragment

    private val scheduleModel by viewModels<ScheduleViewModel>()
    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
    }

    fun init(view: View) {
//        setContentView(R.layout.activity_main)

        initUI(view)

        transactionNewsListFragment()

        bindData()

        scheduleModel.scheduleLiveData.observe(requireActivity()) {
            when (it) {
                is NetworkResult.Error -> {
                    Log.d(TAG, "onCreate: Schedule: ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "onCreate: Schedule: ${it.message}")
                }
                is NetworkResult.Success -> {
                    Log.d(TAG, "onCreate_Schedule_Data: ${listOf(it.data)}")
                    txtBannerTitle.text = it.data?.get(0)!!.series_name
                    txtBannerSubtitle.text =
                        it.data[0].match_desc + "\n" + it.data[0].team1_name + " VS " + it.data[0].team2_name
                    txtOverviewBanner.text = "Venue: " + it.data[0].venue_ground + "\n"
                }
            }
        }

        newsViewModel.newsLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Error -> {
                    Log.d(TAG, "init: News ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "init: News Loading...")
                }
                is NetworkResult.Success -> {
                    Log.d(TAG, "init:_News ${it.data?.javaClass}")
                    rowsListFragment.bindNewsData(it.data)
                }
            }
        }
    }

    private fun initUI(view: View) {
        imgBanner = view.findViewById(R.id.imgBanner)
        txtBannerSubtitle = view.findViewById(R.id.txtSubTitle)
        txtBannerTitle = view.findViewById(R.id.txtHomeBannerTitle)
        txtOverviewBanner = view.findViewById(R.id.txtOverviewBanner)
    }

    private fun bindData() {

        scheduleModel.getScheduleData()
        newsViewModel.getNewsData()

        val gson = Gson()
        val i: InputStream = requireContext().assets.open("movies.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: DataModel = gson.fromJson(br, DataModel::class.java)
        Log.d(TAG, "bindData: $dataList")

        rowsListFragment.bindData(dataList)

        rowsListFragment.setOnItemClickListener {
            Log.d(TAG, "bindData: sdsd is Detail:${it is Detail} or is News:${it is NewsModelItem}")
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            if (it is Detail){
                val detailItem = it
                intent.putExtra("class_name", "Detail")
                DetailDataRepository.detailItem = detailItem
                Log.d(TAG, "bindData: ${detailItem.title}")
            }else if (it is NewsModelItem){
                val detailItem = it
                NewsDataRepository.newsItem = detailItem
                intent.putExtra("class_name", "NewsItem")
            }

            requireActivity().startActivity(intent)
        }
        updateBanner(dataList.result[0].details[0])
    }

    private fun updateBanner(it: Detail) {
        txtBannerTitle.text = it.title
        txtBannerSubtitle.text = it.release_date
        txtOverviewBanner.text = it.overview

//        val url = "https://www.themoviedb.org/t/p/w780" + it.backdrop_path
        val url = ""

//        Glide.with(this).load(url).into(imgBanner)
    }

    private fun transactionNewsListFragment() {
        rowsListFragment = RowsListFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.news_list_fragment, rowsListFragment)
        transaction.commit()
    }

}