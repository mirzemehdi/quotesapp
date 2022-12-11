package com.mmk.common.ui.fragmentdelegations

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> viewBinding(
    bindingInitializer: (LayoutInflater) -> T
) = FragmentViewBindingProperty(bindingInitializer)

class FragmentViewBindingProperty<T : ViewBinding>(private val bindingInitializer: (LayoutInflater) -> T) :
    ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (isLifecycleInDestroyedState(lifecycle.currentState))
            throw IllegalStateException(
                "Binding property is null.\n" +
                    "Binding property can be referenced only between " +
                    "Lifecycle.State.CREATED and Lifecycle.State.DESTROYED"
            )

        return binding ?: run {
            lifecycle.addObserver(this)
            bindingInitializer(thisRef.layoutInflater).also { binding = it }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
        binding = null
    }

    private fun isLifecycleInDestroyedState(state: Lifecycle.State): Boolean {
        return state == Lifecycle.State.DESTROYED
    }
}
