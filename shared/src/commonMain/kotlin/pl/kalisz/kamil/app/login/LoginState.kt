package pl.kalisz.kamil.app.login

class LoginState(val isProgress: Boolean, val isError: Boolean, val errorMessage: String?){
    companion object{
        fun initialState() = LoginState(isProgress = false, isError = false, errorMessage = null)
        fun loading() = LoginState(isProgress = true, isError = false, errorMessage = null)
        fun success() = LoginState(isProgress = false, isError = false, errorMessage = null)
        fun error(message: String) = LoginState(isProgress = false, isError = true, errorMessage = message)


    }
}