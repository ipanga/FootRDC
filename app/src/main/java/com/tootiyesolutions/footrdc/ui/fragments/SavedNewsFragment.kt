package com.tootiyesolutions.footrdc.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tootiyesolutions.footrdc.Injection
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.adapter.SavedNewsAdapter
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.ui.SearchNewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news){

    lateinit var viewModel: SearchNewsViewModel
    lateinit var newsAdapter: SavedNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(requireContext()))
            .get(SearchNewsViewModel::class.java)
        setupRecyclerView()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position] as Article
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveNews(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = SavedNewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}