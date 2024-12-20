package com.example.dwitchapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dwitchapp.screens.AccountScreen
import com.example.dwitchapp.screens.NewsScreen
import com.example.dwitchapp.screens.OrdersScreen
import com.example.dwitchapp.ui.theme.DwitchAppTheme


//class OrdersViewModel : ViewModel() {
//    private val _orders = mutableStateOf<List<Order>>(emptyList())
//    val orders: State<List<Order>> = _orders
//
//    init {
//        fetchOrders()
//    }
//
//    private fun fetchOrders() {
//        viewModelScope.launch {
//            try {
//                val token = "Bearer 49b70f996ffbb654be996f8604d118bfca7624ced27749df6f4fdcac30b7009da1ba63ef7d6b91c8ca814baf88955daba2804396ab3b8cd2c03b50a1f96ff330032d2fbc2238338b4f7e25bff9e852b002c26ecca02fbf1e8e261cf6e0cdb00c042e35b33f64dda3522c3178ba1edb22b9daba42b51c1c8355309fd475b5d92b" // Normally you get this token from your auth process
//
//                val response = ApiClient.dwitchService.getOrders(token)
//                val orderList = response.data // This is List<Order>
//                _orders.value = orderList
//
//            } catch (e: Exception) {
//                Timber.d("Error fetching orders: ${e.message}")
//            }
//        }
//    }
//}


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DwitchAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomAppBar {
                            var selectedItem by remember { mutableIntStateOf(1) }
                            val items = listOf("Account", "Orders", "News")
                            val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Star)
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                selectedIcons[index],
                                                contentDescription = item
                                            )
                                        },
                                        label = { Text(item) },
                                        selected = selectedItem == index,
                                        onClick = {
                                            selectedItem = index
                                            navController.navigate(item)
                                        }
                                    )
                                }
                            }
                        }
                    }
                ){
                    NavHost(
                        navController = navController,
                        startDestination = "Orders"
                    ) {
                        composable(route = "Account") {
                            AccountScreen()
                        }
                        composable(route = "Orders") {
                            OrdersScreen()
                        }
                        composable(route = "News") {
                            NewsScreen()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderListPreview() {
    DwitchAppTheme {
        OrdersScreen()
    }
}