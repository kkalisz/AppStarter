package pl.kalisz.kamil.app.login

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.kalisz.kamil.viewmodel.ViewModelMpp
import kotlin.random.Random

class LoginViewModel: ViewModelMpp() {
    val state = MutableStateFlow<LoginState> (LoginState.initialState())

    val newState: List<String> = emptyList()

    fun login(username: String, password: String){
        viewModelScope.launch {
            state.emit(LoginState.loading())
            delay(4000)
            if(Random.nextBoolean()) {
                state.emit(LoginState.error("error"))
            }else{
                state.emit(LoginState.success())
            }
            val httpClient = HttpClient();
            val content = httpClient.request<String>{
                url("https://en.wikipedia.org/wiki/Main_Page")
                method = HttpMethod.Get
            }
            state.emit(LoginState.error(content.substring(20)))
        }
    }
}