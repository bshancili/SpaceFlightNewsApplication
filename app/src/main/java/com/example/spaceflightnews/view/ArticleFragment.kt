package com.example.spaceflightnews.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spaceflightnews.R
import com.example.spaceflightnews.databinding.FragmentArticleBinding
import com.example.spaceflightnews.model.Article
import com.example.spaceflightnews.viewmodel.ArticleViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleUrl: String
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(requireActivity())[ArticleViewModel::class.java]

        val article = arguments?.getParcelable<Article>("article")

        article?.let { it ->
            binding.articleTitle.text = it.title
            Glide.with(this)
                .load(it.image_url)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.articleImage)

            val originalDateString = it.published_at // "2024-10-29T18:01:40Z"
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val targetFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date: Date? = originalFormat.parse(originalDateString)
            val formattedDate = date?.let { targetFormat.format(it) } ?: "Unknown date"
            
            binding.articleDetails.text = "by ${it.news_site} at $formattedDate"
            binding.articleSummary.text = it.summary.ifEmpty {
                "No summary has been provided."
            }
            articleUrl = it.url // Store the article URL
            
            val isSaved = viewModel.isArticleBookmarked(it)
            if (isSaved) {
                binding.bookmarkButton.setImageResource(R.drawable.bookmark_remove)
            } else {
                binding.bookmarkButton.setImageResource(R.drawable.bookmark_add)
            }
        }

        binding.articleURL.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireContext(), Uri.parse(articleUrl)) 
        }

        binding.bookmarkButton.setOnClickListener {
            article?.let {
                val isSaved = viewModel.toggleArticleBookmarkState(it)
                if (isSaved) {
                    binding.bookmarkButton.setImageResource(R.drawable.bookmark_remove)
                } else {
                    binding.bookmarkButton.setImageResource(R.drawable.bookmark_add)
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
