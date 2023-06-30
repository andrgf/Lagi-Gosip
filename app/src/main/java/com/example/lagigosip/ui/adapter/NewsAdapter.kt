package com.example.lagigosip.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lagigosip.R
import com.example.lagigosip.data.local.entity.NewsEntity
import com.example.lagigosip.databinding.NewsItemBinding
import com.example.lagigosip.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) : ListAdapter <NewsEntity, NewsAdapter.MyViewHolder>(DiFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val bookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            bookmark.setImageDrawable(ContextCompat.getDrawable(bookmark.context, R.drawable.bookmark))
        } else {
            bookmark.setImageDrawable(ContextCompat.getDrawable(bookmark.context, R.drawable.bookmark_white))
        }
        bookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

    class MyViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            binding.tvTitle.text = news.title
            binding.tvDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(binding.root)
                .load(news.urlToImage)
                .into(binding.ivNews)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }

    }

    companion object {
        val DiFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

            }
    }
}