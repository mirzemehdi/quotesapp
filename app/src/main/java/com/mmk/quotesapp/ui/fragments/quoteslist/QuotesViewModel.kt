package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mmk.data.repository.quotes.QuotesPagingSource
import com.mmk.domain.interaction.quote.allquotes.GetQuotesByPaginationUseCase
import com.mmk.domain.model.Quote
import com.mmk.quotesapp.ui.base.BaseViewModel
import com.mmk.quotesapp.ui.base.UiState

/**
 * Created by mirzemehdi on 8/12/20
 */
class QuotesViewModel constructor(
    private val quotesByPaginationUseCase: GetQuotesByPaginationUseCase

) : BaseViewModel() {

    private var _quotesListPagedData: MutableLiveData<PagingData<Quote>> = getQuotesPagingData()
    var quotesListPagedData: LiveData<PagingData<Quote>> = _quotesListPagedData


    fun setUiState(uiState: UiState) {
        _uiState.value = uiState
    }


    private fun getQuotesPagingData(): MutableLiveData<PagingData<Quote>> {
        val config =
            PagingConfig(pageSize = 10, initialLoadSize = 15, enablePlaceholders = false)
        return Pager(
            config = config,
            pagingSourceFactory = { QuotesPagingSource(quotesByPaginationUseCase) })
            .flow.asLiveData().cachedIn(viewModelScope)
            .let { it as MutableLiveData<PagingData<Quote>> }
    }


    fun deleteQuote(quote: Quote) {
        _quotesListPagedData.value?.filter { it.id != quote.id }
            .let { _quotesListPagedData.value = it }
    }

    fun editQuote(quote: Quote) {
        _quotesListPagedData.value?.map {
            if (it.id == quote.id) return@map it.copy(author = "Deyisilmis", text = "Yehuuuuuuu")
            else it

        }
            .let { _quotesListPagedData.value = it }
    }

}