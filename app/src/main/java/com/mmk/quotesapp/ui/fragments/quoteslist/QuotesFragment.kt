package com.mmk.quotesapp.ui.fragments.quoteslist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mmk.quotesapp.databinding.FragementQuotesBinding
import com.mmk.quotesapp.ui.base.BaseFragment
import com.mmk.quotesapp.utils.binding.viewbinding.viewBinding
import com.mmk.quotesapp.utils.onLoadingStateChanged
import com.mmk.quotesapp.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuotesFragment : BaseFragment() {

    private val viewModel: QuotesViewModel by viewModel()
    private val quotesAdapter by lazy { QuotesAdapter() }
//    private lateinit var binding: FragementQuotesBinding

    override val binding by viewBinding(FragementQuotesBinding::inflate)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        setClicks()
        observeValues()
    }


    private fun initView() {
        binding.quotesRecyclerView.adapter = quotesAdapter
            .withLoadStateFooter(
                footer = ItemLoadStateAdapter { quotesAdapter.retry() }
            )


        quotesAdapter.onLoadingStateChanged { viewModel.setUiState(it) }


    }

    private fun setClicks() {
        quotesAdapter.onClickItem = {
            Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
            viewModel.editQuote(it)
        }
        quotesAdapter.onLikeButtonClicked = {
            context?.toast("On Like Button Clicked")
            viewModel.deleteQuote(it)
        }
    }

    private fun observeValues() {
        viewModel.quotesListPagedData.observe(viewLifecycleOwner) {
            quotesAdapter.submitData(lifecycle, it)

        }
    }

}