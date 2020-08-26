package com.tootiyesolutions.footrdc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.databinding.RecyclerScoreRowItemBinding
import com.tootiyesolutions.footrdc.model.AllCategory
import com.tootiyesolutions.footrdc.model.CategoryItem


class ScoreViewHolder(private val binding: RecyclerScoreRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var scoreItemsAdapter: ScoreItemsAdapter
    lateinit var advertItemsAdapter: AdvertItemsAdapter

    fun bind(
        scoreItemList: List<AllCategory>, advertItemList: List<AllCategory>
    ) {
        binding.catTitle.text = scoreItemList[0].categoryTitle
        setScoreItemRecycler(binding.scoreRecycler, scoreItemList[0].categoryItemList)
        setAdvertItemRecycler(binding.advertRecycler, advertItemList[0].categoryItemList)
    }

    companion object {
        fun create(parent: ViewGroup): ScoreViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_score_row_item, parent, false)
            return ScoreViewHolder(RecyclerScoreRowItemBinding.bind(view))
        }
    }

    private fun setScoreItemRecycler(recyclerView: RecyclerView, scoreItemList: List<CategoryItem>) {
        scoreItemsAdapter = ScoreItemsAdapter()
        scoreItemsAdapter.differ.submitList(scoreItemList)
        recyclerView.apply {
            adapter = scoreItemsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setAdvertItemRecycler(recyclerView: RecyclerView, advertItemList: List<CategoryItem>) {
        advertItemsAdapter = AdvertItemsAdapter()
        advertItemsAdapter.differ.submitList(advertItemList)
        // Convert recyclerView to ViewPager2
        if (recyclerView.onFlingListener == null) PagerSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.apply {
            adapter = advertItemsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

    }
}