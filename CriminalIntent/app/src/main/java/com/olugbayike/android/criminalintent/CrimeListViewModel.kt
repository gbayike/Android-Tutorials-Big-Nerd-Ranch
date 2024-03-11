package com.olugbayike.android.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import kotlin.math.log


private const val TAG = "CrimeListViewModel"
class CrimeListViewModel: ViewModel() {
//    val crimes = mutableListOf<Crime>()

    private val crimeRepository = CrimeRepository.get()

//    val crimes = crimeRepository.getCrimes()

    private val _crimes: MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimes: StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()
    init {
//        Log.d(TAG,"init starting")
        viewModelScope.launch {
//            Log.d(TAG, "coroutine launched")
//            crimes += loadCrimes()
//            Log.d(TAG, "Loading crimes finished")
            crimeRepository.getCrimes().collect {
                _crimes.value = it
            }
        }

    }

    suspend fun addCrime(crime: Crime){
        crimeRepository.addCrime(crime)
    }

//    suspend fun deleteCrime(crime: Crime){
//        crimeRepository.deleteCrime()
//    }

//    suspend fun loadCrimes(): List<Crime> {
/**
 *     Loading crimes from dummy data
 */

//        val result = mutableListOf<Crime>()
//        delay(5000)
//        for (i in 0 until 100) {
//            val crime = Crime(
//                id = UUID.randomUUID(),
//                title = "crime #$i",
//                date = Date(),
//                isSolved = i % 2 == 0,
//                requiresPolice = i % 3 == 0
//            )
//            result += crime
//        }
//        return result

//        return crimeRepository.getCrimes()
//    }
}