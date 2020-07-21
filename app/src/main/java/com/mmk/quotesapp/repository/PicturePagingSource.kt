package com.mmk.quotesapp.repository

import androidx.paging.PagingSource
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.network.PhotoService
import com.mmk.quotesapp.utils.toPictureDataList
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class PicturePagingSource @Inject constructor(private val apiService: PhotoService) :
    PagingSource<Int, PictureData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData> {
        val pageIndex = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = apiService.loadPhotos(pageIndex.toString())
            val pictureResultList = response.body() ?: listOf()
            LoadResult.Page(
                data = pictureResultList.toPictureDataList(),
                prevKey = if (pageIndex == UNSPLASH_STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (pictureResultList.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}