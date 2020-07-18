package com.mmk.quotesapp.ui.fragments.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mmk.quotesapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewModel:MainViewHolder by viewModels()
    @Inject lateinit var adapter:PicturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentMainBinding.inflate(inflater,container,false)
        context?: return binding.root
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeValues()
    }

    private fun initView() {
        quotesList.adapter=adapter
    }



    private fun observeValues() {
        viewModel.getPictureList()
        viewModel.pictureListResponse?.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            Timber.e("${it.size}")
            adapter.submitList(it)
        })
    }
}