package com.example.memeow.feature_keyboard

import android.view.View
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import dagger.hilt.android.AndroidEntryPoint

/*
* We need this class because:
* Compose need 3 "Owner" classes from android life cycle package to work:
* 1. LifecycleOwner
* 2. ViewModelStoreOwner
* 3. SavedStateRegistryOwner
*
* Above are implement in appcompatActivity and fragment but not input method serivce
* Source: https://stackoverflow.com/questions/65755763/inputmethodservice-with-jetpack-compose-composeview-causes-composed-into-the
* */


class KeyboardViewLifecycleOwner :
    LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    fun onCreate() {
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun onResume() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun onPause() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        store.clear()
    }

    /**
    Compose uses the Window's decor view to locate the
    Lifecycle/ViewModel/SavedStateRegistry owners.
    Therefore, we need to set this class as the "owner" for the decor view.
     */
    fun attachToDecorView(decorView: View?) {
        if (decorView == null) return

        ViewTreeLifecycleOwner.set(decorView, this)
        ViewTreeViewModelStoreOwner.set(decorView, this)
        ViewTreeSavedStateRegistryOwner.set(decorView, this)
    }

    // LifecycleOwner methods
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    // ViewModelStore methods
    private val store = ViewModelStore()
    override fun getViewModelStore(): ViewModelStore = store

    // SavedStateRegistry methods
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    override fun getSavedStateRegistry(): SavedStateRegistry =
        savedStateRegistryController.savedStateRegistry
}