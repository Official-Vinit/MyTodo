package com.example.mytodo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.ui.auth.LogInScreen
import com.example.mytodo.ui.auth.SignupScreen
import com.example.mytodo.ui.task.TodoScreen
import com.example.mytodo.ui.theme.MyTodoTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTodoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //LogInScreen(modifier = Modifier.padding(innerPadding))

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login", modifier = Modifier.padding(innerPadding)) {
                        composable("login") {
                            LogInScreen(navController)
                        }
                        composable("signup") {
                            SignupScreen(navController)
                        }
                        composable("home") {
                            TodoScreen()
                        }
                    }
                }
            }
        }
    }
}

class AuthenticaltionManager @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val auth = Firebase.auth
    fun createAcoountWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow{
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    trySend(AuthResponse.Success)
                }else{
                    trySend(AuthResponse.Error(it.exception?.message?:""))
                }
            }
        awaitClose()
    }
    fun LogInWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow{
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    trySend(AuthResponse.Success)
                }else{
                    trySend(AuthResponse.Error(it.exception?.message?:""))
                }
            }
        awaitClose()
    }
//    private fun createNonce(): String{
//        val rawNonce = UUID.randomUUID().toString()
//        val bytes = rawNonce.toByteArray()
//        val md = MessageDigest.getInstance("SHA-256")
//        val digest = md.digest(bytes)
//
//        return digest.fold("") { str, it ->
//            str + "%02x".format(it)
//        }
//    }
//    fun signInWithGoogle(): Flow<AuthResponse> = callbackFlow{
//        val googleIdOption = GetGoogleIdOption.Builder()
//            .setFilterByAuthorizedAccounts(false)
//            .setServerClientId(context.getString(R.string.google_app_id))
//            .setAutoSelectEnabled(false)
//            .setNonce(createNonce())
//            .build()
//
//        val request = GetCredentialRequest.Builder()
//            .addCredentialOption(googleIdOption)
//            .build()
//
//        try{
//            val credentialManager = CredentialManager.create(context)
//            val result = credentialManager.getCredential(
//                context = context,
//                request = request
//            )
//
//            val credential = result.credential
//            if(credential is CustomCredential){
//                if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
//                    try{
//                        val googleIdTokenCredential = GoogleIdTokenCredential
//                            .createFrom(credential.data)
//                        val firebaseCredential = GoogleAuthProvider
//                            .getCredential(
//                                googleIdTokenCredential.idToken,
//                                null
//                            )
//                        auth.signInWithCredential(firebaseCredential)
//                            .addOnCompleteListener{
//                                if(it.isSuccessful){
//                                    trySend(AuthResponse.Success)
//                                }else{
//                                    trySend(AuthResponse.Error(it.exception?.message?:""))
//                                }
//                            }
//                    }catch(e: GoogleIdTokenParsingException){
//                        trySend(AuthResponse.Error(e.localizedMessage?:""))
//                    }
//                }
//            }
//        } catch (e: Exception){
//            trySend(AuthResponse.Error(e.localizedMessage?:""))
//        }
//
//        awaitClose()
//    }
}

interface AuthResponse{
    data object Success : AuthResponse
    data class Error(val message: String): AuthResponse
}
