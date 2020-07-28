package com.tootiyesolutions.footrdc.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.tootiyesolutions.footrdc.Injection
import com.tootiyesolutions.footrdc.R
import com.tootiyesolutions.footrdc.adapter.NewsLoadStateAdapter
import com.tootiyesolutions.footrdc.adapter.ResultsAdapter
import com.tootiyesolutions.footrdc.databinding.FragmentFixturesBinding
import com.tootiyesolutions.footrdc.ui.AppViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FixturesFragment : Fragment(){

    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppViewModel
    private val adapter = ResultsAdapter()

    private var searchJob: Job? = null

    private fun search() {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            context?.let {
                viewModel.fetchFixtures(it).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        val view = binding.root

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(AppViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvFixtures.addItemDecoration(decoration)

        initAdapter()
        search()
        binding.btRetryFixtures.setOnClickListener { adapter.retry() }
        return view
    }

    private fun initAdapter() {
        binding.rvFixtures.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvFixtures.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.pbFixtures.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.btRetryFixtures.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}
