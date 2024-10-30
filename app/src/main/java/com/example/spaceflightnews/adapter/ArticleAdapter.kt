package com.example.spaceflightnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spaceflightnews.R
import com.example.spaceflightnews.model.Article
import java.text.SimpleDateFormat
import java.util.Locale

class ArticleAdapter(
    private val articles: MutableList<Article>,
    private val itemClickListener: OnArticleClickListener
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    interface OnArticleClickListener {
        fun onArticleClick(article: Article)
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.itemTitle)
        val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        val summaryTextView: TextView = itemView.findViewById(R.id.itemSummary)
        val publishedAtTextView: TextView = itemView.findViewById(R.id.itemPublish)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title

        // Load the article image using Glide
        Glide.with(holder.itemView.context)
            .load(article.image_url)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageView)

        holder.summaryTextView.text = article.summary

        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        try {
            val date = originalFormat.parse(article.published_at)
            holder.publishedAtTextView.text = date?.let { targetFormat.format(it) }
        } catch (e: Exception) {
            holder.publishedAtTextView.text = article.published_at
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onArticleClick(article)
        }
    }

    override fun getItemCount(): Int = articles.size

    fun addArticles(newArticles: List<Article>) {
        val startPosition = articles.size
        val filteredArticles = newArticles.filterNot { newArticle ->
            articles.any { it.id == newArticle.id }
        }
        articles.addAll(filteredArticles)
        notifyItemRangeInserted(startPosition, filteredArticles.size)
    }
}
