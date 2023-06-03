package com.mmk.quotesapp.di

import com.mmk.core.di.coreModule
import com.mmk.profile.profileFeatureModule
import com.mmk.quotes.quotesFeatureModule
import org.koin.core.module.Module

private val featureModules = quotesFeatureModule + profileFeatureModule
val appModules: List<Module> = listOf(coreModule) + featureModules
