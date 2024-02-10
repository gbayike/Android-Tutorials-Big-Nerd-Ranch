package com.olugbayike.android.criminalintent

import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

class CrimeListViewModel: ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        for(i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "crime #$i",
                date = Date(),
                isSolved = i % 2 == 0,
                requiresPolice = i % 3 == 0
            )
            crimes += crime
        }
    }
}