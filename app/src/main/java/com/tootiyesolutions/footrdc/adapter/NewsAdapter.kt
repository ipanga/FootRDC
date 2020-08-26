package com.tootiyesolutions.footrdc.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.ui.UiModel
import com.tootiyesolutions.footrdc.ui.fragments.BreakingNewsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Adapter for the list of news.
 */
class NewsAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_news_preview -> { NewsViewHolder.create(parent) }
            R.layout.item_view_separator -> { SeparatorViewHolder.create(parent) }
            else -> { ScoreViewHolder.create(parent) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.NewsItem -> R.layout.item_news_preview
            is UiModel.SeparatorItem -> R.layout.item_view_separator
            is UiModel.ScoreItem -> R.layout.score_row_items
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.NewsItem -> (holder as NewsViewHolder).bind(uiModel.news)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.description)
                is UiModel.ScoreItem -> (holder as ScoreViewHolder).bind(BreakingNewsFragment.getAllScroe(), BreakingNewsFragment.getAllAdvert())
            }
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.NewsItem && newItem is UiModel.NewsItem &&
                        oldItem.news.id == newItem.news.id) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }
}
