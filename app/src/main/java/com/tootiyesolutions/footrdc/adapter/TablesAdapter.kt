package com.tootiyesolutions.footrdc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.databinding.ItemTablePreviewBinding
import com.tootiyesolutions.footrdc.model.Tables

class TablesAdapter : PagingDataAdapter<Tables, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemTablePreviewBinding>(inflater, R.layout.item_table_preview, parent, false)
        return TablesViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tableItem = getItem(position)
        if (tableItem != null) {
            (holder as TablesViewHolder).view.table = tableItem
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Tables>() {
            override fun areItemsTheSame(oldItem: Tables, newItem: Tables): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Tables, newItem: Tables): Boolean =
                oldItem == newItem
        }
    }

    class TablesViewHolder(var view: ItemTablePreviewBinding): RecyclerView.ViewHolder(view.root)
}