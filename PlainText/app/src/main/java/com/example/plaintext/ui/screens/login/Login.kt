package com.example.plaintext.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.plaintext.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// --- ViewModel e State ---
data class LoginViewState(
    val login: String = "",
    val password: String = "",
    val checked: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(LoginViewState())
        private set

    fun onLoginChanged(newLogin: String) {
        uiState = uiState.copy(login = newLogin)
    }

    fun onPasswordChanged(newPassword: String) {
        uiState = uiState.copy(password = newPassword)
    }

    fun onCheckedChanged(newChecked: Boolean) {
        uiState = uiState.copy(checked = newChecked)
    }

    fun clearFields() {
        uiState = LoginViewState()
    }

    fun checkCredentials(login: String, password: String): Boolean {
        // Lógica de validação, altere conforme necessário
        return login == "admin" && password == "1234"
    }
}

// --- Tela de Login ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = { navigateToSettings() },
                navigateToSensores = {}
            )
        },
        containerColor = Color(0xFF3B1E0E)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(1.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabeçalho verde
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFA0C539)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "\"The most\nsecure password\nmanager\"\nBob and Alice",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Digite suas credenciais para continuar",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Login
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login:",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = uiState.login,
                    onValueChange = { viewModel.onLoginChanged(it) },
                    modifier = Modifier.width(270.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFD2B48C),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Senha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Senha:",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.width(270.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFD2B48C),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // CheckBox
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.checked,
                    onCheckedChange = { viewModel.onCheckedChanged(it) },
                )
                Text(
                    text = "Salvar as informações de login",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Botão
            Button(
                onClick = {
                    if (viewModel.checkCredentials(uiState.login, uiState.password)) {
                        navigateToList()
                    } else {
                        Toast.makeText(
                            context,
                            "Login/Senha inválidos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color(0xFF8B4513)),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFD2B48C),
                    containerColor = Color.White
                ),
                enabled = true
            ) {
                Text("Enviar")
            }
        }
    }
}

// --- AlertDialog ---
@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog.value = false },
            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(onClick = { shouldShowDialog.value = false }) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

// --- TopBar ---
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText", color = Color.White) },
        actions = {
            if (navigateToSettings != null && navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings()
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = { Text("Sobre") },
                        onClick = {
                            shouldShowDialog.value = true
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF3B1E0E),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    Login_screen(
        navigateToSettings = {},
        navigateToList = {},
    )
}
