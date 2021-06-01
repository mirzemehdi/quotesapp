package com.mmk.quotesapp.ui.fragments.addquote


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.FragmentAddQuoteBinding
import com.mmk.quotesapp.utils.observeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddNewQuoteFragment : Fragment(R.layout.fragment_add_quote) {
    private val viewModel: AddNewQuoteViewModel by viewModel()
    private lateinit var binding: FragmentAddQuoteBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddQuoteBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        observeValues()
    }


    private fun observeValues() {
        viewModel.onQuoteAdded.observeEvent(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }
    }
}