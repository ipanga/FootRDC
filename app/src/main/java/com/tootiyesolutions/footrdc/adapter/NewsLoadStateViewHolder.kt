package com.tootiyesolutions.footrdc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.databinding.ItemNewsLoadStateFooterViewBinding

class NewsLoadStateViewHolder(
    private val binding: ItemNewsLoadStateFooterViewBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btLoadStateRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvLoadStateErrorMessage.text = loadState.error.localizedMessage
        }
        binding.pbLoadState.isVisible = loadState is LoadState.Loading
        binding.btLoadStateRetry.isVisible = loadState !is LoadState.Loading
        binding.tvLoadStateErrorMessage.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): NewsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_load_state_footer_view, parent, false)
            val binding = ItemNewsLoadStateFooterViewBinding.bind(view)
            return NewsLoadStateViewHolder(binding, retry)
        }
    }
}