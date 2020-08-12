package com.mmk.quotesapp.ui.fragments.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.mmk.quotesapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewModel: MainViewHolder by viewModels()

    @Inject
    lateinit var adapter: PicturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, wsavedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeValues()
    }

    private fun initView() {
        quotesRecyclerView.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = PictureLoadStateAdapter { adapter.retry() },
                footer = PictureLoadStateAdapter { adapter.retry() }
            )
        adapter.addLoadStateListener { loadState ->
            with(loadState.source.refresh) {
                quotesRecyclerView.isVisible = this is LoadState.NotLoading
                progressBarMain.isVisible = this is LoadState.Loading
            }


        }
    }


    private fun observeValues() {
        viewModel.getPictureList().observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)

        })

    }
}