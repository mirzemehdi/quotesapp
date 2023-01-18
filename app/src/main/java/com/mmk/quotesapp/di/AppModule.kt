package com.mmk.quotesapp.di

import com.mmk.core.di.coreModule
import com.mmk.profile.profileFeatureModule
import com.mmk.quotes.quotesFeatureModule

private val featureModules = quotesFeatureModule + profileFeatureModule
val appModules = listOf(coreModule) + featureModules
