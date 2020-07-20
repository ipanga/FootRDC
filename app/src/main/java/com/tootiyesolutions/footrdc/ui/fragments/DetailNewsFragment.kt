package com.tootiyesolutions.footrdc.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tootiyesolutions.footrdc.Injection
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.ui.SearchNewsViewModel
import kotlinx.android.synthetic.main.fragment_detail_news.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailNewsFragment : Fragment(R.layout.fragment_detail_news) {

    lateinit var viewModel: SearchNewsViewModel
    val args: DetailNewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(requireContext()))
            .get(SearchNewsViewModel::class.java)
        //viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.link.toString())
        }

        // When clicked on the fab button, save article and show SnackBar
        fab.setOnClickListener {
            viewModel.saveNews(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}