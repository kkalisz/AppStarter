package pl.kalisz.kamil.app

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.lifecycleScope
import co.touchlab.kampkit.android.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import pl.kalisz.kamil.app.login.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val loginViewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button)
        val login = findViewById<TextInputEditText>(R.id.login_login)
        val pasword = findViewById<TextInputEditText>(R.id.login_password)
        val progres = findViewById<ContentLoadingProgressBar>(R.id.login_progress)


        loginButton.setOnClickListener {
            loginViewModel.login(login.text.toString(),pasword.text.toString())
        }

        lifecycleScope.launchWhenResumed {
            loginViewModel.state.collect {
                if(it.isProgress){
                    progres.show()
                }else{
                    progres.hide()
                }
                if(it.isError){
                    Toast.makeText(applicationContext,it.errorMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}