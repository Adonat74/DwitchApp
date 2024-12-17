package com.example.dwitchapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color as Colors
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dwitchapp.ui.theme.DwitchAppTheme
import com.example.dwitchapp.data.orders
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.ui.theme.OpenColors

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DwitchAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_refresh_24),
                                        contentDescription = "Add"
                                    )
                                    Text(" Mes Commandes")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    OrderList(
                        orders = orders,
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(10.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun OrderList(orders: List<Order>, modifier: Modifier = Modifier) {
    LazyColumn (modifier = modifier){
        items(orders) { order ->
            Order(order, modifier = Modifier
                .padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
fun Order(order: Order, modifier: Modifier) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(15.dp))
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
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
                color = Colors(Color.parseColor(ingredient.getColor())),
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
        Text("${order.store.name} - ${order.store.city} ${order.store.zipCode}", fontWeight = FontWeight.SemiBold)
    }
}







@Preview(showBackground = true)
@Composable
fun OrderListPreview() {
    DwitchAppTheme {
        OrderList(orders)
    }
}