package pl.kalisz.kamil

import org.koin.core.module.Module
import org.koin.dsl.module
import pl.kalisz.kamil.app.login.LoginViewModel

fun baseViewModelModule(): Module {
    return module {
        single { LoginViewModel() }
    }
}