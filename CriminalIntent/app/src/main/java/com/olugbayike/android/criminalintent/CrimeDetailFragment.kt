package com.olugbayike.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.olugbayike.android.criminalintent.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeDetailFragment"
class CrimeDetailFragment: Fragment() {
//    private lateinit var binding: FragmentCrimeDetailBinding
    var _binding: FragmentCrimeDetailBinding? = null

    private val args: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }
    val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback is only called when MyFragment is at least started


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back is pressed... Finish the activity
                // Handle the back button event
                Log.d(TAG, "Value of crime title is ${binding.crimeTitle.text.toString()}")
                if (binding.crimeTitle.text.toString().isEmpty()){
                    binding.crimeTitle.setHint(R.string.crime_title_needed)
                }
                else{
                    Log.d(TAG, "Activated Finish on the activity")
                    val navController = Navigation.findNavController(requireView())
                    navController.popBackStack(R.id.crimeDetailFragment2,true)
                    Log.d(TAG, "Finished the activity")
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

//        requireActivity().onBackPressedDispatcher.addCallback(this /* lifecycle owner */, callback)




        Log.d(TAG, "The crime id id: ${args.crimeId}")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = FragmentCrimeDetailBinding.inflate(layoutInflater,container, false)
        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(title = text.toString())
                }
            }
//            crimeDate.apply {
//                isEnabled = false
//            }
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(isSolved = isChecked)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                crimeDetailViewModel.crime.collect{ crime ->
                    crime?.let { updateUi(it) }
                }
            }
        }

        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ){ _, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            Log.d(TAG, "KEY_DATE: ${newDate}")
            crimeDetailViewModel.updateCrime { it.copy(date = newDate) }
        }

        setFragmentResultListener(
            TimePickerFragment.REQUEST_KEY_TIME
        ){ _, bundle ->
            val newTime =
                bundle.getSerializable(TimePickerFragment.BUNDLE_KEY_TIME)
            Log.d(TAG, "KEY_TIME: ${newTime}")
            binding.time.setText(newTime.toString())
//            crimeDetailViewModel.updateCrime { it.copy(date = newDate) }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
        _binding = null
    }

    private fun updateUi(crime: Crime) {
        binding.apply{
            if (crimeTitle.text.toString() != crime.title){
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()

            crimeDate.setOnClickListener {
                findNavController().navigate(
                    CrimeDetailFragmentDirections.selectDate(crime.date)
                )
            }

            time.setOnClickListener {
                findNavController().navigate(
                    CrimeDetailFragmentDirections.selectTime(binding.time.text.toString())
                )
            }
            crimeSolved.isChecked = crime.isSolved
        }
    }
}