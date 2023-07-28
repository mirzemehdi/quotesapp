package com.mmk.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.mmk.core.model.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import java.util.UUID

actual fun randomUUIDString(): String = UUID.randomUUID().toString()

@Composable
actual fun <T> StateFlow<T>.asState(): State<T> = collectAsState()

actual inline fun <reified T : ViewModel> Module.multiPlatformViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> = single(qualifier = qualifier, definition = definition)

actual object ViewModelProvider : KoinComponent {
    @Composable
    actual inline fun <reified T : ViewModel> get(): T = getKoin().get()
}
