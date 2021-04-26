package co.touchlab.kampkit.android

import android.app.Application
import co.touchlab.kampkit.AppInfo
import pl.kalisz.kamil.core.initKoinMvvm

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            modules(baseViewModelModule())
//        }

        initKoinMvvm {}

//        initKoin(
//            module {
//                single<Context> { this@MainApp }
//                viewModel { BreedViewModel() }
//                single<SharedPreferences> {
//                    get<Context>().getSharedPreferences("KAMPSTARTER_SETTINGS", Context.MODE_PRIVATE)
//                }
//                single<AppInfo> { AndroidAppInfo }
//                single {
//                    { Log.i("Startup", "Hello from Android/Kotlin!") }
//                }
//            }
//        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
