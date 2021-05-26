package com.mmk.quotesapp.ui.fragments.addquote


import androidx.fragment.app.Fragment

import com.mmk.quotesapp.R



class AddNewQuoteFragment : Fragment(R.layout.fragment_add_quote) {
//    private val viewModel:AddNewQuoteViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding=FragmentAddQuoteBinding.inflate(inflater,container,false)
//        binding.viewModel=viewModel
//        binding.lifecycleOwner=this
//       return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        observeValues()
//    }
//
//    private fun observeValues() {
//        viewModel.uiState.observe(viewLifecycleOwner, Observer {
//
//            when(it){
//                UiState.Loading->{
//                    addNewQuoteButton.visibility=View.INVISIBLE
//                    newQuoteAuthorInputLayout.isEnabled=false
//                    newQuoteInputLayout.isEnabled=false
//                }
//                else->{
//                    addNewQuoteButton.visibility=View.VISIBLE
//                    newQuoteAuthorInputLayout.isEnabled=true
//                    newQuoteInputLayout.isEnabled=true
//                }
//            }
//        })
//
//        viewModel.onQuoteAdded.observe(viewLifecycleOwner, Observer {
//            if (it==true)
//                findNavController().popBackStack()
//        })
//    }
}