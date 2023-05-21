package com.example.tfg4.presentation.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg4.Database.Controller
import com.example.tfg4.Database.Users
import com.example.tfg4.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    val state: MutableState<LoginState> = mutableStateOf(LoginState())
    val c = Controller()


    fun login(email: String, password: String) {

        val user: MutableList<String> = c.getUser(email)

        val errorMessage = if(email.isBlank() || password.isBlank()) {
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else if(email != c.getUser(email).get(0) || password !=  c.getUser(email).get(1) ){
            R.string.error_invalid_credentials
        } else null

        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnFailureListener { e ->
                    Log.w("Login", "Error al iniciar sesión", e)
                    when (e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            Log.w("Login", " Contraseña incorrecta")
                        } //"Datos incorrectos"
                        is FirebaseAuthInvalidUserException -> {
                            Log.w("Login", " Usuario inexistente")
                        } //"Usuario no existe"
                        else -> {
                            Log.e("Register", " Error no gestionado de Firebase", e)
                        }
                    }
                }
        }catch (error: Exception) {
                Log.e("Login", "-2: Error no controlado", error)
        }

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(email = email, password = password)
            state.value = state.value.copy(displayProgressBar = false)
            state.value = state.value.copy(successLogin = true)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}