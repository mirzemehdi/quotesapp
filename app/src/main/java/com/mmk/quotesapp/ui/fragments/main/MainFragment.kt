package com.mmk.quotesapp.ui.fragments.main


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mmk.quotesapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    val viewModel:MainViewHolder by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValues()
    }

    private fun observeValues() {
        viewModel.getRandomQuote()
        viewModel.randomQuoteResponse?.observe(viewLifecycleOwner, Observer {
            textView.text=it?.contents?.quotes?.getOrNull(0)?.quote
        })
    }
}