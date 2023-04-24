package com.mmk.quotes.presentation.allquotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mmk.common.ui.UiState
import com.mmk.common.ui.fragmentdelegations.viewBinding
import com.mmk.common.ui.observeEvent
import com.mmk.quotes.databinding.FragmentQuotesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuotesFragment : Fragment() {
    private val binding by viewBinding(FragmentQuotesBinding::inflate)
    private val viewModel: QuotesViewModel by viewModel()
    private val quotesAdapter by lazy { QuotesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent { QuotesScreen() }
    }

    fun initView() {
        binding.quotesRecyclerView.adapter = quotesAdapter.withLoadStateFooter(
            footer = ItemLoadStateAdapter { quotesAdapter.retry() }
        )
        quotesAdapter.addLoadStateListener {
            viewModel.onPageAdapterLoadingStateChanged(
                it.refresh,
                quotesAdapter.itemCount
            )
        }
        quotesAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.quotesRecyclerView.smoothScrollToPosition(positionStart)
            }
        })
    }

    fun setClicks() {
//        super.setClicks()
        quotesAdapter.onClickQuoteItem = {
            Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
        }
        quotesAdapter.onClickLikeButton = {
            Toast.makeText(context, "On like button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    fun observeValues() {
//        super.observeValues()
        viewModel.quotesList.observe(viewLifecycleOwner) {
            quotesAdapter.submitData(lifecycle, it)
        }

        viewModel.noNetworkConnectionEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }

        viewModel.getQuotesUiState.observe(viewLifecycleOwner) {
            binding.quotesRecyclerView.isVisible = it == UiState.HasData
            binding.progressBarQuotes.isVisible = it == UiState.Loading
            binding.emptyView.isVisible = it == UiState.NoData
        }
    }
}
