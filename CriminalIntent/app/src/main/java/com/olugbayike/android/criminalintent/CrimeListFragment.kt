package com.olugbayike.android.criminalintent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.olugbayike.android.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "CrimeListFragment"
class CrimeListFragment : Fragment() {
    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private var _binding: FragmentCrimeListBinding? = null
    private var job: Job? = null

    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is the view visible?"
        }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)

        binding.crimeRecyclerView.layoutManager = LinearLayoutManager (context)

//        val crimes = crimeListViewModel.crimes
//        val adapter = CrimeListAdapter(crimes)
//        binding.crimeRecyclerView.adapter = adapter

        return binding.root
    }

    // Using repeatOnLifeCycle()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
//                val crimes = crimeListViewModel.loadCrimes()
                crimeListViewModel.crimes.collect { crimes ->
                    binding.crimeRecyclerView.adapter =
                        CrimeListAdapter(crimes) { crimeId ->
                            findNavController().navigate(
//                                Without safe args
//                                R.id.show_crime_detail
                                CrimeListFragmentDirections.showCrimeDetail(crimeId)
                            )
                        }
                }
            }

        }
    }

//    Using coroutines on life cycle methods onStart() and on Stop().

//    override fun onStart() {
//        super.onStart()
//        job = viewLifecycleOwner.lifecycleScope.launch {
//            val crimes = crimeListViewModel.loadCrimes()
//            binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        job?.cancel()
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}