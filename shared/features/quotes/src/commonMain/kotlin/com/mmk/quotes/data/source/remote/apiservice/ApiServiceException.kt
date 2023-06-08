package com.mmk.quotes.data.source.remote.apiservice

data class ApiServiceException(val e: Exception) : Exception(e)
