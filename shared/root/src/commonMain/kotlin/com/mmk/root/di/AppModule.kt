package com.mmk.root.di

import com.mmk.common.ui.di.uiModule
import com.mmk.core.di.coreModule
import com.mmk.profile.profileFeatureModule
import com.mmk.quotes.quotesFeatureModule
import org.koin.core.module.Module
import org.koin.dsl.module

private val featureModules = quotesFeatureModule + profileFeatureModule
private val commonModule = module { includes(coreModule, uiModule) }
val appModules: List<Module> = listOf(commonModule) + featureModules
