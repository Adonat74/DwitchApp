package com.example.dwitchapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dwitchapp.R
import com.example.dwitchapp.model.orders.Order
import com.example.dwitchapp.service.ApiClient
import com.example.dwitchapp.ui.theme.OpenColors
import kotlinx.coroutines.launch
import timber.log.Timber

class OrdersViewModel : ViewModel() {
    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: State<List<Order>> = _orders

    init {
        fetchOrders()
    }

    fun fetchOrders() {
        viewModelScope.launch {
            try {
                val token = "Bearer 49b70f996ffbb654be996f8604d118bfca7624ced27749df6f4fdcac30b7009da1ba63ef7d6b91c8ca814baf88955daba2804396ab3b8cd2c03b50a1f96ff330032d2fbc2238338b4f7e25bff9e852b002c26ecca02fbf1e8e261cf6e0cdb00c042e35b33f64dda3522c3178ba1edb22b9daba42b51c1c8355309fd475b5d92b" // Normally you get this token from your auth process

                val response = ApiClient.dwitchService.getOrders(token)
                val orderList = response.data // This is List<Order>
                Timber.d("${response}")

                _orders.value = orderList

            } catch (e: Exception) {
                Timber.d("Error fetching orders: ${e.message}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(viewModel: OrdersViewModel = viewModel()) {
//    var refreshTrigger by remember { mutableIntStateOf(0) } // A state variable to trigger refetch
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
//                            refreshTrigger++
                            viewModel.fetchOrders()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_refresh_24),
                                contentDescription = "Add"
                            )
                        }
                        Text(" Mes Commandes")
                    }
                }
            )
        }
    ) { innerPadding ->
        OrderList(
//            refreshTrigger = refreshTrigger,
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}





//suspend fun fetchData(): List<Order> {
//    val token = "Bearer 49b70f996ffbb654be996f8604d118bfca7624ced27749df6f4fdcac30b7009da1ba63ef7d6b91c8ca814baf88955daba2804396ab3b8cd2c03b50a1f96ff330032d2fbc2238338b4f7e25bff9e852b002c26ecca02fbf1e8e261cf6e0cdb00c042e35b33f64dda3522c3178ba1edb22b9daba42b51c1c8355309fd475b5d92b"
//    val response = ApiClient.dwitchService.getOrders(token)
//    Timber.d("$response")
//    return response.data
//}

//view model way
@Composable
fun OrderList(viewModel: OrdersViewModel = viewModel(), modifier: Modifier = Modifier) {
    val orders by viewModel.orders


    LazyColumn (modifier = modifier){
        items(orders) { order ->
            OrderItem(order, modifier = Modifier
                .padding(vertical = 10.dp)
            )
        }
    }
}

// sans view model
//@Composable
//fun OrderList(refreshTrigger: Int, modifier: Modifier = Modifier) {
//    var orders by remember { mutableStateOf(emptyList<Order>()) }
//
//
//    LaunchedEffect(refreshTrigger) {
//        orders = fetchData()
//    }
//
//    LazyColumn (modifier = modifier){
//        items(orders) { order ->
//            OrderItem(order, modifier = Modifier
//                .padding(vertical = 10.dp)
//            )
//        }
//    }
//}

@Composable
fun OrderItem(order: Order, modifier: Modifier) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(15.dp))
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(8 .dp)) {
            OrderHeader(order)
            Text(
                "${order.getMainCount()} Garniture(s) principale(s)  ${order.getToppingCount()} sauce(s)  ${order.ingredients.size} ingrédient(s)",
                color = OpenColors.gray8,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
            IngredientsList(order)
            ProgressBar(order)
            HorizontalDivider(thickness = 1.dp, color = OpenColors.gray5)
            OrderFooter(order)
        }
    }
}




@Composable
fun IngredientsList(order: Order) {
    LazyRow{
        items(order.ingredients) { ingredient ->
            Surface(
                color = Color(android.graphics.Color.parseColor(ingredient.getColor())),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 3.dp)
            ) {
                Row (modifier = Modifier
                    .padding(vertical = 3.dp, horizontal = 10.dp)
                ) {
                    Text(ingredient.getEmoji())
                    Text(ingredient.name)
                }
            }
        }
    }
}


@Composable
fun ProgressBar(order: Order) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxSize()
    ) {
        Text("Progression", fontSize = 13.sp, letterSpacing = 0.4.sp)
        LinearProgressIndicator(
            progress = order.progress/100f,
            color = OpenColors.gray9,
            modifier = Modifier
                .height(8.dp)
                .width(220.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text("${order.progress}%", fontSize = 13.sp, letterSpacing = 0.4.sp)
    }
}

@Composable
fun OrderHeader(order: Order) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row{
            Icon(
                painter = painterResource(id = R.drawable.baseline_shopping_bag_24),
                contentDescription = "Add"
            )
            Text("Le ${order.getFormatedPlacedAtDate()}  ", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
        Surface(
            shape = MaterialTheme.shapes.small,
            color = OpenColors.red3
        ) {
            Text(
                "${order.price}€",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(6.dp)
            )
        }
    }
}

@Composable
fun OrderFooter(order: Order) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_store_24),
            contentDescription = "Add",
            modifier = Modifier
                .padding(end = 10.dp)
        )
        Text("${order.store?.name} - ${order.store?.city} ${order.store?.zipCode}", fontWeight = FontWeight.SemiBold)
    }
}