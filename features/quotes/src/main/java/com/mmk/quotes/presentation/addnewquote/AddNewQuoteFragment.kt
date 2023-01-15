package com.mmk.quotes.presentation.addnewquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.mmk.common.ui.UiState
import com.mmk.common.ui.fragmentdelegations.viewBinding
import com.mmk.common.ui.observeEvent
import com.mmk.quotes.databinding.FragmentAddQuoteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewQuoteFragment : Fragment() {
    private val viewModel: AddNewQuoteVM by viewModel()
    private val binding by viewBinding(FragmentAddQuoteBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root.also {
        setClicks()
        observeValues()
    }

    private fun observeValues() {
        viewModel.onQuoteAdded.observeEvent(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it is UiState.Loading
            binding.addNewQuoteButton.isVisible = it !is UiState.Loading
        }

        binding.newQuoteAuthorEditText.setTwoWayDataBinding(viewModel.quoteAuthor)
        binding.newQuoteEditText.setTwoWayDataBinding(viewModel.quoteText)
    }

    private fun setClicks() {
        binding.addNewQuoteButton.setOnClickListener { viewModel.addQuote() }
    }

    private fun EditText.setTwoWayDataBinding(observable: MutableLiveData<String>) {
        observable.observe(viewLifecycleOwner) {
            if (this.text.toString() != it) this.setText(it)
        }
        this.doAfterTextChanged { observable.value = it.toString() }
    }
}
