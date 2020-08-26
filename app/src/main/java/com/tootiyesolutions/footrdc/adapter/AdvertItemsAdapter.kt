package com.tootiyesolutions.footrdc.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.model.CategoryItem
import kotlinx.android.synthetic.main.advert_row_items.view.*

class AdvertItemsAdapter: RecyclerView.Adapter<AdvertItemsAdapter.AdvertViewHolder>() {

    inner class AdvertViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        return AdvertViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.advert_row_items,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) {
        val categoryItem = differ.currentList[position]
        holder.itemView.apply {
            item_advert_image.setImageResource(categoryItem.imageUrl)
        }
    }
}