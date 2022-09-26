package com.mmk.quotes.presentation.allquotes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mmk.common.ui.UiState
import com.mmk.common.ui.fragmentdelegations.FragmentMainMethods
import com.mmk.common.ui.fragmentdelegations.IFragmentMainMethods
import com.mmk.common.ui.fragmentdelegations.viewBinding
import com.mmk.quotes.databinding.FragmentQuotesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuotesFragment : Fragment(), IFragmentMainMethods by FragmentMainMethods() {
    private val binding by viewBinding(FragmentQuotesBinding::inflate)
    private val viewModel: QuotesViewModel by viewModel()
    private val quotesAdapter by lazy { QuotesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callMainMethods()
    }

    override fun initView() {
        super.initView()
        binding.quotesRecyclerView.adapter = quotesAdapter.withLoadStateFooter(
            footer = ItemLoadStateAdapter { quotesAdapter.retry() }
        )
        quotesAdapter.addLoadStateListener {
            viewModel.onPageAdapterLoadingStateChanged(
                it.refresh,
                quotesAdapter.itemCount
            )
        }
    }

    override fun setClicks() {
        super.setClicks()
        quotesAdapter.onClickQuoteItem = {
            Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
        }
        quotesAdapter.onClickLikeButton = {
            Toast.makeText(context, "On like button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun observeValues() {
        super.observeValues()
        viewModel.quotesList.observe(viewLifecycleOwner) {
            quotesAdapter.submitData(lifecycle, it)
        }

        viewModel.getQuotesUiState.observe(viewLifecycleOwner) {
            binding.quotesRecyclerView.isVisible = it == UiState.HasData
            binding.progressBarQuotes.isVisible = it == UiState.Loading
            binding.emptyView.isVisible = it == UiState.NoData
        }
    }
}