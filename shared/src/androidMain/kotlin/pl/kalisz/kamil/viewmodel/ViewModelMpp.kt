package pl.kalisz.kamil.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pl.kalisz.kamil.core.WORKER

actual open class ViewModelMpp {
    protected actual val viewModelScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.WORKER)

    actual open fun onCleared() {
    }
}