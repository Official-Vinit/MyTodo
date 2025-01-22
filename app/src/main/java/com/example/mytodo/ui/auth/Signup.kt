package com.example.mytodo.ui.auth

import android.R.attr.value
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mytodo.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.mytodo.AuthenticaltionManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.compose.runtime.getValue

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun SignupScreen(navController: NavController) {

    //val email = viewModel.email.collectAsStateWithLifecycle()
    //val password = viewModel.password.collectAsStateWithLifecycle()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoadingByEmail by rememberSaveable { mutableStateOf(false) }
    var isLoadingByGoogle by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val authenticationManager = remember{
        AuthenticaltionManager(
            context
        )
    }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//       BackButton(
//           onClick = { /*TODO*/ }
//       )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Signup",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 40.dp)
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Email",
            modifier = Modifier.padding(start = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            placeholder = {
                Text(text = "Enter your email")
            },
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Password",
            modifier = Modifier.padding(start = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = {
                Text(text = "Enter your password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Button(
            onClick = {
                isLoadingByEmail = true
                authenticationManager.createAcoountWithEmail(email, password)
                    .onEach {
                        when (it) {
                            is AuthResponse.Success -> {
                                navController.navigate("home")
                                isLoadingByEmail = false
                            }

                            is AuthResponse.Error -> {
                                Toast.makeText(context, "fuck off", Toast.LENGTH_SHORT).show()
                                isLoadingByEmail = false
                            }
                        }
                    }
                    .launchIn(coroutineScope)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .padding(end = 8.dp)
                .height(50.dp)
        ) {
            if (isLoadingByEmail) {
                CircularProgressIndicator(color = Color.White)
            }else{
                Text(
                    text = "Signup",
                    fontSize = 20.sp,
                )
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                thickness = 1.dp,
            )
            Text(
                text = "or",
                modifier = Modifier.padding(8.dp)

            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                thickness = 1.dp,
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        OutlinedButton(
            onClick = {
//                isLoadingByGoogle = true
//                authenticationManager.signInWithGoogle()
//                    .onEach {
//                        when (it) {
//
//                            is AuthResponse.Success -> {
//                                navController.navigate("home")
//                                isLoadingByGoogle = false
//                            }
//
//                            is AuthResponse.Error -> {
//                                Toast.makeText(context, "fuck off", Toast.LENGTH_SHORT).show()
//                                isLoadingByGoogle = false
//                            }
//                        }
//                    }
//                    .launchIn(coroutineScope)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ){
            if (isLoadingByGoogle) {
                CircularProgressIndicator() // Show loading indicator
            } else {
                Row {
                    Image(
                        painter = painterResource(R.drawable.google__1_),
                        contentDescription = "Google",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Signup with Google",
                        fontSize = 20.sp,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        TextButton(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Already have an account? Login",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(rememberNavController())
}