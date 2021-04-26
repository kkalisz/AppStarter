package pl.kalisz.kamil.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect val Dispatchers.UI: CoroutineDispatcher
expect val Dispatchers.WORKER: CoroutineDispatcher

