package com.mmk.quotesapp.ui.fragments.quoteslist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.FragementQuotesBinding
import com.mmk.quotesapp.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuotesFragment : Fragment(R.layout.fragement_quotes) {

    private val viewModel: QuotesViewModel by viewModel()
    private val quotesAdapter by lazy { QuotesAdapter() }
    private lateinit var binding: FragementQuotesBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragementQuotesBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        setClicks()
        observeValues()
    }


    private fun initView() {
        binding.quotesRecyclerView.adapter = quotesAdapter
            .withLoadStateHeaderAndFooter(
                header = ItemLoadStateAdapter { quotesAdapter.retry() },
                footer = ItemLoadStateAdapter { quotesAdapter.retry() }
            )
        quotesAdapter.addLoadStateListener { loadState ->
            with(loadState.source.refresh) {
                binding.quotesRecyclerView.isVisible = this is LoadState.NotLoading
                binding.progressBarQuotes.isVisible = this is LoadState.Loading
            }
        }

    }

    private fun setClicks() {
        quotesAdapter.onClickItem = {
            Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
        }
        quotesAdapter.onLikeButtonClicked = {
            context?.toast("On Like Button Clicked")
        }
    }

    private fun observeValues() {
        viewModel.quotesList.observe(viewLifecycleOwner, Observer {
            quotesAdapter.submitData(lifecycle, it)
        })
    }

}