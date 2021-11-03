package com.shehata.pokedex.screens.login

import android.text.Layout
import android.util.Log
import android.content.res.Resources
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.pokedex.R
import com.pokedex.components.GoogleButton
import com.pokedex.components.OutlinedEmailTextField
import com.pokedex.components.OutlinedPasswordTextField

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.loadingState.collectAsState()

    val token = stringResource(R.string.default_web_client_id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedEmailTextField(userEmail) { userEmail = it }

        OutlinedPasswordTextField(userPassword) { userPassword = it }

        LoginButton(userEmail, userPassword) {
            viewModel.signInWithEmailAndPassword(userEmail.trim(), userPassword.trim())
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption,
            text = "Login with"
        )

        Spacer(modifier = Modifier.height(18.dp))

        GoogleSignIn(
            token = token,
            signWithCredential = { credential ->
                viewModel.signWithCredential(credential)
            }
        )

        when (state.status) {
            LoadingState.Status.SUCCESS -> {
                Text(text = "Success")
            }
            LoadingState.Status.FAILED -> {
                Text(text = state.msg ?: "Error")
            }
            else -> {
            }
        }
    }
}


@Composable
fun LoginButton(
    email: String,
    password: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = email.isNotEmpty() && password.isNotEmpty(),
        onClick = onClick
    ) {
        Text(text = "Login")
    }

}

@Composable
fun GoogleSignIn(
    token: String,
    signWithCredential: (AuthCredential) -> Unit
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                signWithCredential(credential)
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }

    OutlinedButton(
        border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        }
    ) {
        GoogleButton()
    }
}

