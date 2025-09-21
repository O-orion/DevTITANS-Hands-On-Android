package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: String,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Botão de voltar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFA0C539),
            titleContentColor = Color.White
        )
    )
}

fun isPasswordEmpty(password: PasswordInfo): Boolean {
    return password.name.isEmpty() && password.login.isEmpty() && password.password.isEmpty()
}

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo) -> Unit
) {
    val title: String = if (isPasswordEmpty(args.password)) {
        "Adicionar nova senha"
    } else {
        "Editar senha"
    }

    val nomeState = rememberSaveable { mutableStateOf(args.password.name ?: "") }
    val usuarioState = rememberSaveable { mutableStateOf(args.password.login ?: "") }
    val senhaState = rememberSaveable { mutableStateOf(args.password.password ?: "") }
    val notasState = rememberSaveable { mutableStateOf(args.password.notes ?: "") }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = title,
                navigateBack = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EditInput(
                textInputLabel = "Nome",
                textInputState = nomeState
            )
            EditInput(
                textInputLabel = "Usuário",
                textInputState = usuarioState
            )
            EditInput(
                textInputLabel = "Senha",
                textInputState = senhaState
            )
            EditInput(
                textInputLabel = "Notas",
                textInputState = notasState,
                textInputHeight = 120
            )

            Button(onClick = {
                val newPassword = PasswordInfo(
                    args.password.id,
                    nomeState.value,
                    usuarioState.value,
                    senhaState.value,
                    notasState.value
                )
                savePassword(newPassword)
                navigateBack()
            }) {
                Text(text = "Salvar")
            }
        }
    }
}

@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String>,
    textInputHeight: Int = 60
) {
    val padding: Int = 30
    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(1, "Nome", "Usuário", "Senha", "Notas")),
        navigateBack = {},
        savePassword = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome", mutableStateOf(""))
}