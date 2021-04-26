package com.kalisz.kamil.sample

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.papsign.ktor.openapigen.model.Described
import com.papsign.ktor.openapigen.model.security.HttpSecurityScheme
import com.papsign.ktor.openapigen.model.security.SecuritySchemeModel
import com.papsign.ktor.openapigen.model.security.SecuritySchemeType
import com.papsign.ktor.openapigen.modules.providers.AuthProvider
import com.papsign.ktor.openapigen.route.path.auth.OpenAPIAuthenticatedRoute
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.util.pipeline.*
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.reflect.typeOf


data class UserPrincipal(val userId: String, val name: String?) : Principal

val jwtProviderName = "jwt"

val authProvider = JwtProvider()

fun Application.installAuth(){
    val jwtIssuer = this.environment.config.property("jwt.issuer").getString().toString()//"acme.com"
    val jwtAudience = this.environment.config.property("jwt.audience").getString()//"af600a62-cf3d-4445-a78f-8f453b36cf84"
    val jwtRealm = "none"
    //val jwtEndpoint: String = "$jwtIssuer/protocol/openid-connect/certs"

    val algorithm = Algorithm.HMAC256(this.environment.config.property("jwt.secret").getString())

    fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun installJwt (provider: Authentication.Configuration) {
        provider.apply {
            jwt(jwtProviderName) {
                realm = jwtRealm
                verifier(makeJwtVerifier( jwtIssuer, jwtAudience))
                validate { credentials ->
                    UserPrincipal(
                        credentials.payload.subject,
                        credentials.payload.claims["name"]?.asString())
                }
            }
        }
    }

    install(Authentication) {
        installJwt(this)
    }

    fun getJwkProvider(jwkEndpoint: String): JwkProvider {
        return JwkProviderBuilder(URL(jwkEndpoint))
            .cached(10, 24, TimeUnit.HOURS)
            .rateLimited(10, 1, TimeUnit.MINUTES)
            .build()
    }
}

class JwtProvider : AuthProvider<UserPrincipal> {
    override val security: Iterable<Iterable<AuthProvider.Security<*>>> =
        listOf(listOf(
            AuthProvider.Security(
                SecuritySchemeModel(
                    SecuritySchemeType.http,
                    scheme = HttpSecurityScheme.bearer,
                    bearerFormat = "JWT",
                    name = jwtProviderName
                ), emptyList<JwtScopes>()
            )
        ))

    override suspend fun getAuth(pipeline: PipelineContext<Unit, ApplicationCall>): UserPrincipal {
        return pipeline.context.authentication.principal() ?: throw RuntimeException("No JWTPrincipal")
    }

    @ExperimentalStdlibApi
    override fun apply(route: NormalOpenAPIRoute): OpenAPIAuthenticatedRoute<UserPrincipal> {
        val authenticatedKtorRoute = route.ktorRoute.authenticate("jwt")  { }
        return OpenAPIAuthenticatedRoute(authenticatedKtorRoute, route.provider.child().also { it.registerModule(this, typeOf<AuthProvider<*>>()) }, this)
    }
}

enum class JwtScopes(override val description: String) : Described {
    Dummy("dummy jwt scope")
}

@ExperimentalStdlibApi
inline fun NormalOpenAPIRoute.jwtAuth(crossinline route: OpenAPIAuthenticatedRoute<UserPrincipal>.()->Unit = {}): OpenAPIAuthenticatedRoute<UserPrincipal> {
    return authProvider.apply(this).apply {
        route()
    }
}

inline fun<T> NormalOpenAPIRoute.auth(provider: AuthProvider<T>, crossinline route: OpenAPIAuthenticatedRoute<T>.()->Unit = {}): OpenAPIAuthenticatedRoute<T> {
    return provider.apply(this).apply {
        route()
    }
}