package com.tootiyesolutions.footrdc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.databinding.ItemNewsPreviewBinding
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.ui.fragments.BreakingNewsFragmentDirections

class NewsViewHolder(private val binding: ItemNewsPreviewBinding) :
    RecyclerView.ViewHolder(binding.root), NewsClickListener {

    override fun onArticleClicked(v: View, article: Article) {
        val action = BreakingNewsFragmentDirections.actionNavigationBreakingNewsToDetailNewsFragment(article)
        Navigation.findNavController(v).navigate(action)
    }

    fun bind(article: Article?) {
        binding.article = article
        binding.listener = this
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_preview, parent, false)
            return NewsViewHolder(ItemNewsPreviewBinding.bind(view))
        }
    }
}