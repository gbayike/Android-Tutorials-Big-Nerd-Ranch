package com.olugbayike.android.codapizza.ui

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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


val TAG = "pizzaBuilderScreen"

@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier,
){
    var pizza by rememberSaveable {
        mutableStateOf(Pizza())
    }
    Column(
        modifier = modifier
    ) {
        ToppingList(
            pizza = pizza,
            onEditPizza = { pizza = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )

        OrderButton(
            pizza = pizza,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
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
){
    Button(
        modifier = modifier,
        onClick = { /*TODO*/ }
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