package com.mmk.quotesapp.ui.fragments.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mmk.quotesapp.R
import com.mmk.quotesapp.data.QuoteData
import com.mmk.quotesapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewModel:MainViewHolder by viewModels()
    private val adapter=QuotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentMainBinding.inflate(inflater,container,false)
        context?: return binding.root
        binding.lifecycleOwner=this
        binding.input="Salam"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
       // observeValues()
    }

    private fun initView() {
        adapter.submitList(getFakeList())
        quotesList.adapter=adapter
    }

    private fun getFakeList(): MutableList<QuoteData>? {
        val list= mutableListOf<QuoteData>()
        for (i in 1 .. 10){
            list.add(QuoteData(i,"Random Quoteee"))
        }
        return list
    }

    private fun observeValues() {
        viewModel.getRandomQuote()
        viewModel.randomQuoteResponse?.observe(viewLifecycleOwner, Observer {
            textView.text=it?.contents?.quotes?.getOrNull(0)?.quote
        })
    }
}