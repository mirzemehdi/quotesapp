package com.mmk.quotes.data.util

import com.mmk.core.model.ErrorEntity

/*
This class is created to convert core Result.Error to Paging LoadResult.Error

Whenever you want to return LoadResult.Error from PagingSource,
pass this class object as a parameter to LoadResult.Error
 */
data class PagingException(val errorEntity: ErrorEntity?) : Throwable()
