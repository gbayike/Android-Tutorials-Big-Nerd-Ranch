package com.olugbayike.android.codapizza.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.olugbayike.android.codapizza.R
import com.olugbayike.android.codapizza.model.Topping
import com.olugbayike.android.codapizza.model.ToppingPlacement



@Composable
fun ToppingPlacementDialog(
    onSetToppingPlacement: (placement: ToppingPlacement?) -> Unit,
    onDismissRequest: () -> Unit,
    topping: Topping,
){
    Dialog(
        onDismissRequest = onDismissRequest
        ) {
        Card{
            Column {
                val toppingName = stringResource(id = topping.toppingName)

                Text(
                    text = stringResource(id = R.string.placement_prompt, toppingName),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp,24.dp, 24.dp,16.dp)
                        .fillMaxWidth()
                )
            }

            ToppingPlacement.values().forEach { placement ->
                ToppingPlacementOption(
                    placementName = placement.label,
                    onClick = {
                        onSetToppingPlacement(placement)
                        onDismissRequest()
                    }
                )
            }

            ToppingPlacementOption(
                placementName = R.string.placement_none,
                onClick = {
                    onSetToppingPlacement(null)
                    onDismissRequest()
                },
                modifier = Modifier.padding(16.dp,0.dp,16.dp,16.dp)
            )

        }

    }
}

@Composable
private fun ToppingPlacementOption(
    @StringRes placementName: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    TextButton(
        onClick = onClick,
        modifier = modifier.padding(8.dp, 0.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = placementName),
            modifier = Modifier.padding(8.dp)
        )
    }
}