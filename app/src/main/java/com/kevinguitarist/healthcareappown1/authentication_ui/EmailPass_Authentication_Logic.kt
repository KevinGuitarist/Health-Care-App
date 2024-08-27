package com.kevinguitarist.healthcareappown1.authentication_ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    private val _authState by lazy { MutableLiveData<AuthState>(AuthState.Idle) }
    val authState: LiveData<AuthState> = _authState

    fun handleSignIn(email: String, password: String) {
        if (!isEmailValid(email)) {
            _authState.value = AuthState.AuthError("Invalid email")
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Email signup is successful")
                    _authState.value = AuthState.Success
                } else {
                    task.exception?.let {
                        Log.i(TAG, "Email signup failed with error ${it.localizedMessage}")
                        _authState.value = AuthState.AuthError(it.localizedMessage)
                    }
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}



sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class AuthError(val message: String? = null) : AuthState()
}