package com.mmk.quotes

import com.mmk.quotes.data.di.remoteSourceModule
import com.mmk.quotes.data.di.repositoryModule
import com.mmk.quotes.domain.di.useCaseModule
import com.mmk.quotes.presentation.di.viewModelModule

private val domainModules = listOf(useCaseModule)
private val dataModules = listOf(remoteSourceModule, repositoryModule)
private val presentationModules = listOf(viewModelModule)

val quotesFeatureModule = domainModules + dataModules + presentationModules
