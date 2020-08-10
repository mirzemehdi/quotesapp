package com.mmk.quotesapp.ui.fragments.addquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.FragmentAddQuoteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewQuoteFragment:Fragment() {
    private val viewModel:AddNewQuoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentAddQuoteBinding.inflate(inflater,container,false)
        binding.viewHolder=viewModel
        binding.lifecycleOwner=this
       return binding.root
    }
}