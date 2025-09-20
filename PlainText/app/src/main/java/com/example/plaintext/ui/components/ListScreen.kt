package com.example.plaintext.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.plaintext.ui.components.AddButton
import com.example.plaintext.ui.components.ListItem

data class PasswordItem(val title: String, val username: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onAddClicked: () -> Unit = {},
    passwords: List<PasswordItem> = listOf(
        PasswordItem("Twitter", "dev"),
        PasswordItem("Facebook", "devtitans"),
        PasswordItem("Moodle", "dev.com")
    )
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("PlainText", color = Color.White) }) },
        floatingActionButton = { AddButton(onClick = onAddClicked) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(passwords) { p ->
                ListItem(title = p.title, username = p.username)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListScreen() {
    ListScreen()
}