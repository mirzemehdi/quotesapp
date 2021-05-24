package com.mmk.data.mapper

//This is for presentation
interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}

//This is for saving to Database
interface EntityMapper<out T : Any> {
    fun mapToEntity(): T
}

//This is for sending data to the server
interface NetworkMapper<T : Any> {
    fun mapToNetworkRequest(): T
}