package com.example.plaintext


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.plaintext.ui.components.ListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // se não estiver usando Hilt, pode remover esta anotação
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // Abre diretamente a tela da lista (ListScreen dos seus components)
                ListScreen(
                    onAddClicked = {
                        // ação do botão "+"
                        // exemplo: navegar para uma tela de criação quando você tiver a rota
                    }
                )
            }
        }
    }
}
