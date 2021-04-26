package pl.kalisz.kamil.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val Dispatchers.UI: CoroutineDispatcher
    get() = Dispatchers.Main

actual val Dispatchers.WORKER: CoroutineDispatcher
    get() = Dispatchers.Default