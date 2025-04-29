package com.example.feature_tvbox.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_tvbox.R
import com.example.lib_database.entity.VideoEntity

class TvBoxVideoAdapter(
    private val onClick: (VideoEntity) -> Unit
) : ListAdapter<VideoEntity, TvBoxVideoAdapter.VH>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tvbox_video, parent, false)
        return VH(view)
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val cover: ImageView = itemView.findViewById(R.id.ivCover)
        fun bind(item: VideoEntity) {
            title.text = item.title
            // 你可以用 Glide/Picasso 加载封面
            // Glide.with(cover).load(item.cover).into(cover)
            itemView.setOnClickListener { onClick(item) }
        }
    }
    companion object Diff : DiffUtil.ItemCallback<VideoEntity>() {
        override fun areItemsTheSame(oldItem: VideoEntity, newItem: VideoEntity) = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: VideoEntity, newItem: VideoEntity) = oldItem == newItem
    }
} 