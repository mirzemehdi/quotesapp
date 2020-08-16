package com.mmk.quotesapp.ui.fragments.quoteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.FragementQuotesBinding
import com.mmk.quotesapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragement_quotes.*
import kotlinx.android.synthetic.main.fragment_main.quotesRecyclerView

@AndroidEntryPoint
class QuotesFragment: Fragment(R.layout.fragement_quotes) {

    private val viewModel: QuotesViewModel by viewModels()
    private val quotesAdapter=QuotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragementQuotesBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeValues()
    }

    private fun initView() {
        quotesRecyclerView.adapter=quotesAdapter
            .withLoadStateHeaderAndFooter(
                header = ItemLoadStateAdapter { quotesAdapter.retry() },
                footer = ItemLoadStateAdapter { quotesAdapter.retry() }
            )
        quotesAdapter.addLoadStateListener { loadState ->
            with(loadState.source.refresh) {
                quotesRecyclerView.isVisible = this is LoadState.NotLoading
                progressBarQuotes.isVisible = this is LoadState.Loading
            }
        }
        quotesAdapter.onClickItem={
            Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
        }
        quotesAdapter.onLikeButtonClicked={
            context?.toast("On Like Button Clicked")
        }
    }

    private fun observeValues() {
        viewModel.quotesList?.observe(viewLifecycleOwner, Observer {
            quotesAdapter.submitData(lifecycle,it)
        })
    }

}