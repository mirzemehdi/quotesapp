package com.mmk.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.mmk.core.model.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect fun randomUUIDString(): String

@Composable
expect fun <T> StateFlow<T>.asState(): State<T>

expect inline fun <reified T : ViewModel> Module.multiPlatformViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

expect object ViewModelProvider : KoinComponent {
    @Composable
    inline fun <reified T : ViewModel> get(): T
}
