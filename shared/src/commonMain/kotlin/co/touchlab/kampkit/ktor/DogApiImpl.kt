package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.response.BreedResult
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

class DogApiImpl(log: Kermit) : KtorApi {

    // If this is a constructor property, then it gets captured
    // inside HttpClient config and freezes this whole class.
    @Suppress("CanBePrimaryConstructorProperty")
    private val log = log

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    log.v("Network") { message }
                }
            }

            level = LogLevel.INFO
        }
    }

    init {
        ensureNeverFrozen()
    }

    override suspend fun getJsonFromApi(): BreedResult {
        log.d { "Fetching Breeds from network" }
        return client.get<BreedResult> {
            dogs("api/breeds/list/all")
        }
    }

    private fun HttpRequestBuilder.dogs(path: String) {
        url {
            takeFrom("https://dog.ceo/")
            encodedPath = path
        }
    }
}
