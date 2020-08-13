package com.mmk.quotesapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.utils.QUOTES_COLLECTION
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class QuoteRepository @Inject constructor(private val db: FirebaseFirestore) {
    private val quotesCollection = db.collection(QUOTES_COLLECTION)

    suspend fun addNewQuote(quote: Quote): NetworkResource<String> {
        return try {

            val referenceId = quotesCollection.document().id
            quote.id = referenceId
            quotesCollection.document(referenceId).set(quote).await()
            NetworkResource.Success(referenceId)

        } catch (e: Exception) {
            NetworkResource.Error(e.message)
        }
    }

//    suspend fun getQuotes():NetworkResource<List<Quote>>{
//        return try {
//            val resultTask=quotesCollection.get().await()
//            val quoteList=resultTask.toObjects(Quote::class.java)
//            NetworkResource.Success(quoteList)
//        }catch (e:Exception){
//            NetworkResource.Error(e.message)
//        }
//    }

    fun getQuotes(): LiveData<PagingData<Quote>> {
        val config = PagingConfig(pageSize = 10,initialLoadSize = 15, enablePlaceholders = false)
        return Pager(config = config) { QuotesPagingSource(db) }.liveData
    }
}