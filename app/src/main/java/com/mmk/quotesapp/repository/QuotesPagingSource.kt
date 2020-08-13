package com.mmk.quotesapp.repository

import androidx.paging.PagingSource
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.utils.QUOTES_COLLECTION
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by mirzemehdi on 8/14/20
 */
class QuotesPagingSource(private val db: FirebaseFirestore) : PagingSource<String, Quote>() {
    private val quotesCollection = db.collection(QUOTES_COLLECTION).orderBy(FieldPath.documentId())

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Quote> {

        return try {
            val query =
                if (params.key == null) quotesCollection else quotesCollection.startAfter(params.key)

            val response = query
                .limit(params.loadSize.toLong())
                .get().await()
            val quotesList = response.toObjects(Quote::class.java)
            LoadResult.Page(
                data = quotesList,
                prevKey = null,
                nextKey = if (quotesList.isEmpty()) null else quotesList.last().id

            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}