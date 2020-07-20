package com.tootiyesolutions.footrdc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.databinding.ItemNewsPreviewBinding
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.ui.fragments.BreakingNewsFragmentDirections
import com.tootiyesolutions.footrdc.ui.fragments.SavedNewsFragmentDirections
import com.tootiyesolutions.footrdc.ui.fragments.SearchNewsFragmentDirections

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.NewsViewHolder>(), NewsClickListener {

    inner class NewsViewHolder(var view: ItemNewsPreviewBinding): RecyclerView.ViewHolder(view.root)

    /*The below function DiffUtil is used for the recyclerview to update only items that have been changed
    in the list for better performance instead of reloading the entire list*/
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id.toString() == newItem.id.toString()
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    // This is the tool that will compare lists and calculate differences
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemNewsPreviewBinding>(inflater, R.layout.item_news_preview, parent, false)
        return NewsViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.view.article = differ.currentList[position]
        holder.view.listener = this
    }

    override fun onArticleClicked(v: View, article: Article) {
        try {
            val action =
                BreakingNewsFragmentDirections.actionNavigationBreakingNewsToDetailNewsFragment(article)
            Navigation.findNavController(v).navigate(action)
        }catch (t: Throwable){}

        try {
            val action =
                SavedNewsFragmentDirections.actionNavigationSavedNewsToDetailNewsFragment(article)
            Navigation.findNavController(v).navigate(action)
        }catch (t: Throwable){}

        try {
            val action =
                SearchNewsFragmentDirections.actionNavigationSearchNewsToDetailNewsFragment(article)
            Navigation.findNavController(v).navigate(action)
        }catch (t: Throwable){}
    }
}