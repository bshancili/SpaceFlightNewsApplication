package com.example.spaceflightnews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceflightnews.R
import com.example.spaceflightnews.adapter.ArticleAdapter
import com.example.spaceflightnews.model.Article
import com.example.spaceflightnews.viewmodel.HomeViewModel

class HomeFragment : Fragment(), ArticleAdapter.OnArticleClickListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ArticleAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        adapter = ArticleAdapter(mutableListOf(), this)
        val recyclerView: RecyclerView = view.findViewById(R.id.homeArticles)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.fetchArticles()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    isLoading = true
                    viewModel.fetchArticles()
                }
            }
        })

        viewModel.articlesLiveData.observe(viewLifecycleOwner, { articles ->
            adapter.addArticles(articles)
            isLoading = false
        })
    }

    override fun onArticleClick(article: Article) {
        val bundle = Bundle().apply {
            putParcelable("article", article) // Pass the article object
        }
        findNavController().navigate(R.id.navigation_article, bundle)
    }
}
