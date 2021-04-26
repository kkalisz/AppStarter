package pl.kalisz.kamil.viewmodel

import kotlinx.coroutines.CoroutineScope

expect open class ViewModelMpp() {
    protected val viewModelScope: CoroutineScope

    open fun onCleared()
}