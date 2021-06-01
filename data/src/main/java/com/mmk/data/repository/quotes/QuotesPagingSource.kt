package com.mmk.data.repository.quotes

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.mmk.data.network.model.response.QuoteResponse
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.IOException

class QuotesPagingSource(private val quotesCollection: CollectionReference) :
    PagingSource<String, QuoteResponse>() {

    override fun getRefreshKey(state: PagingState<String, QuoteResponse>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, QuoteResponse> {
        val orderedCollection = quotesCollection.orderBy(FieldPath.documentId())
        return try {
            val query =
                if (params.key == null) orderedCollection else orderedCollection.startAfter(params.key)
            val response = query
                .limit(params.loadSize.toLong())
                .get().await()
            val quotesList = response.toObjects(QuoteResponse::class.java)
            LoadResult.Page(
                data = quotesList,
                prevKey = null,
                nextKey = if (quotesList.isNullOrEmpty()) null else quotesList.last().id
            )

        } catch (exception: IOException) {
            Timber.e(exception)
            LoadResult.Error(exception)

        } catch (exception: Exception) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }
}