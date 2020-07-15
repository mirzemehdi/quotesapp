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
import com.mmk.quotesapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewModel:MainViewHolder by viewModels()

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

       // observeValues()
    }

    private fun observeValues() {
        viewModel.getRandomQuote()
        viewModel.randomQuoteResponse?.observe(viewLifecycleOwner, Observer {
            textView.text=it?.contents?.quotes?.getOrNull(0)?.quote
        })
    }
}