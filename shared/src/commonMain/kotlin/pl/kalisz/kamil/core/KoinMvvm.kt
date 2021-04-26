package pl.kalisz.kamil.core

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.kalisz.kamil.baseViewModelModule

fun initKoinMvvm(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()

    modules(
        baseViewModelModule()
    )
}