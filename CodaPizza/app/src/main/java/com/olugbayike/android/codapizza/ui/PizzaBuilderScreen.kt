package com.olugbayike.android.codapizza.ui

import android.icu.text.NumberFormat
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olugbayike.android.codapizza.R
import com.olugbayike.android.codapizza.ToppingCell
import com.olugbayike.android.codapizza.model.Pizza
import com.olugbayike.android.codapizza.model.Topping
import com.olugbayike.android.codapizza.model.ToppingPlacement
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val TAG = "pizzaBuilderScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier,
){
    var pizza by rememberSaveable {
        mutableStateOf(Pizza())
    }

//    var isRotated by rememberSaveable {
//        mutableStateOf(false)
//    }
    var targetValue by rememberSaveable {
        mutableStateOf(0f)
    }
    val rotateValue by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 1000)
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold (
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.height(IntrinsicSize.Min),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                        Text(text = stringResource(id = R.string.app_name))
                },

                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.systemBars
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            ToppingList(
                pizza = pizza,
                onEditPizza = { pizza = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true),
                rotateValue = rotateValue
            )

            OrderButton(
                pizza = pizza,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                rotatePizza = {
                    targetValue = it
                    Log.d(TAG, "Rotate Value: $rotateValue")}
            )
        }
    }



}

//private var pizza = Pizza(
//    toppings = mapOf(
//        Topping.Pepperoni to ToppingPlacement.All,
//        Topping.Pineapple to ToppingPlacement.All,
//    )
//)
//    set(value) {
//        Log.d(TAG, "Reassigned pizza to $value")
//        field = value
//    }

// Delegation using the by keyword
//private var pizza by mutableStateOf(Pizza())
@Composable
private fun ToppingList(
    modifier: Modifier = Modifier,
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    rotateValue: Float

){
//    var showToppingPlacementDialog by rememberSaveable {
//        mutableStateOf(false)
//    }

    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null) }

//    if (showToppingPlacementDialog){
    toppingBeingAdded?.let { topping ->
        ToppingPlacementDialog(
            topping = topping,
            onDismissRequest = {
//                showToppingPlacementDialog = false
                toppingBeingAdded = null
            },
            onSetToppingPlacement = { placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            }
        )
    }
//    }

    LazyColumn (
        modifier = modifier,
    ){
        item {
            PizzaHeroImage(
                pizza = pizza,
                modifier = Modifier
                    .padding(16.dp)
                    .graphicsLayer(rotationZ = rotateValue)
            )
        }
        items(Topping.values()){ topping ->
            ToppingCell(
                topping = topping,
//                placement = ToppingPlacement.Left,
                placement = pizza.toppings[topping],
                onClickTopping =  {
                    // TODO
//                    val isOnPizza = pizza.toppings[topping] != null
//                    onEditPizza(pizza.withTopping(
//                        topping = topping,
//                        placement = if (isOnPizza){
//                            null
//                        } else {
//                            ToppingPlacement.All
//                        }
//                    ))

//                    showToppingPlacementDialog = true
                    toppingBeingAdded = topping
                },
            )
        }
    }
}

@Composable
private fun OrderButton(
    pizza: Pizza,
    modifier: Modifier = Modifier,
    rotatePizza: (Float) -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
//    val alpha: Float by animateFloatAsState(360f)

    Button(
        modifier = modifier,
        onClick = {
            coroutineScope.launch {
                Toast.makeText(context, R.string.order_placed_toast, Toast.LENGTH_LONG).show()

                rotatePizza(180f)
                delay(1000)
                rotatePizza(0f)
            }

        }
    ) {
        val currencyFormatter = remember {
            NumberFormat.getCurrencyInstance()
        }
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(id = R.string.place_order_button, price)
                .toUpperCase(Locale.current)
        )
    }


}
