package com.example.dwitchapp.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dwitchapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {
    var refreshTrigger by remember { mutableIntStateOf(0) } // A state variable to trigger refetch
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(onClick = {
                            refreshTrigger++
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_refresh_24),
                                contentDescription = "Add"
                            )
                        }
                        Text(" Mon Compte")
                    }
                }
            )
        }
    ) { innerPadding ->
        OrderList(
            refreshTrigger = refreshTrigger,
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}