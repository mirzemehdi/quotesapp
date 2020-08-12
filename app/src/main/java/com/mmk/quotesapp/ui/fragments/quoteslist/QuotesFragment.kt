package com.mmk.quotesapp.ui.fragments.quoteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.FragementQuotesBinding
import com.mmk.quotesapp.databinding.FragmentAddQuoteBinding
import com.mmk.quotesapp.ui.fragments.addquote.AddNewQuoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

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
    }

    private fun observeValues() {
        viewModel.quotesList?.observe(viewLifecycleOwner, Observer {
            quotesAdapter.submitList(it)
        })
    }

}