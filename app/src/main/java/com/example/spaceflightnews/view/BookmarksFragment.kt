package com.example.spaceflightnews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceflightnews.R
import com.example.spaceflightnews.model.Article
import com.example.spaceflightnews.adapter.ArticleAdapter
import com.example.spaceflightnews.viewmodel.BookmarksViewModel

class BookmarksFragment : Fragment(), ArticleAdapter.OnArticleClickListener {

    private lateinit var viewModel: BookmarksViewModel
    private lateinit var adapter: ArticleAdapter
    private var bookmarkedArticles: List<Article> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[BookmarksViewModel::class.java]
        adapter = ArticleAdapter(mutableListOf(), this)

        val emptyMessage: TextView = view.findViewById(R.id.bookmarkEmptyMessage)
        val recyclerView: RecyclerView = view.findViewById(R.id.bookmarkArticles)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        bookmarkedArticles = viewModel.getBookmarkedArticles()
        if (bookmarkedArticles.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            adapter.addArticles(bookmarkedArticles)
        }
    }

    override fun onArticleClick(article: Article) {
        val bundle = Bundle().apply {
            putParcelable("article", article)
        }
        findNavController().navigate(R.id.navigation_article, bundle) // Navigate to Dashboard
    }
}
