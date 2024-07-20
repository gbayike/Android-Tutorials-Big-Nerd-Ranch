package com.olugbayike.android.codapizza.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pizza (
    val size: Size,
    val toppings: Map<Topping, ToppingPlacement> = emptyMap()
): Parcelable{
    val price:Double
        get() =
            when(size){
                Size.Small -> 9.99 + toppings.asSequence()
                    .sumOf { (_, toppingPlacement) ->
                        when(toppingPlacement){
                            ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                            ToppingPlacement.All -> 1.0
                        }
                    }

                Size.Medium -> 14.99 + toppings.asSequence()
                    .sumOf { (_, toppingPlacement) ->
                        when(toppingPlacement){
                            ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                            ToppingPlacement.All -> 1.0
                        }
                    }
                Size.Large -> 19.99 + toppings.asSequence()
                    .sumOf { (_, toppingPlacement) ->
                        when(toppingPlacement){
                            ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                            ToppingPlacement.All -> 1.0
                        }
                    }
                Size.ExtraLarge -> 24.99 + toppings.asSequence()
                    .sumOf { (_, toppingPlacement) ->
                        when(toppingPlacement){
                            ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                            ToppingPlacement.All -> 1.0
                        }
                    }
            }

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza{
        return copy(
            toppings = if(placement == null){
                toppings - topping
            }else{
                toppings + (topping to placement)
            }
        )
    }

    fun changeSize(size: Size): Pizza{
        return Pizza(size)
    }
}