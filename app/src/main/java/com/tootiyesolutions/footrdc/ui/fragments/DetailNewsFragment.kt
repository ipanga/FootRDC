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
import com.tootiyesolutions.footrdc.ui.AppViewModel
import kotlinx.android.synthetic.main.fragment_detail_news.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailNewsFragment : Fragment(R.layout.fragment_detail_news) {

    lateinit var viewModel: AppViewModel
    val args: DetailNewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(AppViewModel::class.java)
        //viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.link.toString())
        }
    }
}