package com.walton.waltonicc.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.walton.waltonicc.models.example.DataModel
import com.walton.waltonicc.models.example.Detail
import com.walton.waltonicc.models.newsmodel.NewsModel
import com.walton.waltonicc.models.newsmodel.NewsModelItem
import com.walton.waltonicc.presenter.NewsListItemPresenter
import com.walton.waltonicc.presenter.ScheduleListItemPresenter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RowsListFragment : RowsSupportFragment() {

    private var itemClickListener: ((Any) -> Unit)? = null

    private val listRowPresenter = object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM) {
        override fun isUsingDefaultListSelectEffect(): Boolean {
            return false
        }
    }.apply {
        shadowEnabled = false
    }

    private var rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(listRowPresenter)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter

        onItemViewClickedListener = ItemViewClickListener()

    }

    fun bindData(dataList: DataModel) {
        dataList.result.forEachIndexed { index, result ->
            val arrayObjectAdapter = ArrayObjectAdapter(ScheduleListItemPresenter())
            result.details.forEach {
                arrayObjectAdapter.add(it)
            }

            val headerItem = HeaderItem("Upcoming Matches")
            val listRow = ListRow(headerItem, arrayObjectAdapter)
            rootAdapter.add(listRow)
        }
    }

    fun bindNewsData(dataList: NewsModel?) {
        val arrayObjectAdapter = ArrayObjectAdapter(NewsListItemPresenter())
        dataList?.forEachIndexed { index, result ->
            arrayObjectAdapter.add(result)
        }
        val headerItem = HeaderItem("News")
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)
    }

    fun setOnItemClickListener(listener: (Any) -> Unit) {
        this.itemClickListener = listener
    }


    inner class ItemViewClickListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is NewsModelItem) {
                Log.d("ICC_WORLD_CUP_2023", "onItemClicked: News")
                itemClickListener?.invoke(item)
            } else if (item is Detail) {
                Log.d("ICC_WORLD_CUP_2023", "onItemClicked: Detail")
                itemClickListener?.invoke(item)
            }
        }
    }

    fun requestFocus(): View {
        val view = view
        view?.requestFocus()
        return view!!
    }

}