package com.mmk.core.mapper

/**
 *
 * This is for presentation layer(domain),
 * can be used by both local and remote objects
 * @param <T> domain model
 */

interface DomainMapper<T> {
    fun mapToDomainModel(): T
}

/**
 * This is for saving data to localSource (Database)
 * @param <T> domain model
 * @param <D> database entity model
 */

interface EntityMapper<T, D> {
    fun mapToEntity(domainObj: T): D
}

/**
 * This is for sending data to the remoteServer
 * @param <T> domain model
 * @param <N> network Request model
 */

interface NetworkMapper<T, N> {
    fun mapToNetworkRequest(domainObj: T): N
}