package com.example.feature_tvbox.ui.site

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_database.entity.TvBoxSiteEntity

class TvBoxSiteAdapter : ListAdapter<TvBoxSiteEntity, TvBoxSiteAdapter.VH>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_2, parent, false)
        return VH(view)
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text1)
        private val subtitle: TextView = itemView.findViewById(R.id.text2)
        fun bind(item: TvBoxSiteEntity) {
            title.text = item.name
            subtitle.text = item.api
        }
    }
    companion object Diff : DiffUtil.ItemCallback<TvBoxSiteEntity>() {
        override fun areItemsTheSame(oldItem: TvBoxSiteEntity, newItem: TvBoxSiteEntity) = oldItem.key == newItem.key
        override fun areContentsTheSame(oldItem: TvBoxSiteEntity, newItem: TvBoxSiteEntity) = oldItem == newItem
    }
}