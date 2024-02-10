package com.olugbayike.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.olugbayike.android.criminalintent.databinding.ListItemCrimeBinding
import com.olugbayike.android.criminalintent.databinding.ListItemSeriousCrimeBinding
import java.text.DateFormat

class CrimeHolder(
    private val binding: ListItemCrimeBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(crime: Crime){
//        if(binding is ListItemCrimeBinding){
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = DateFormat.getDateInstance(DateFormat.FULL).format(crime.date).toString()

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked: : Normal Crime",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.crimeSolved.visibility = if (crime.requiresPolice){
                View.VISIBLE
            }else{
                View.GONE
            }
//        }else if(binding is ListItemSeriousCrimeBinding){
//            binding.crimeTitle.text = crime.title
//            binding.crimeDate.text = crime.date.toString()
//
//            binding.root.setOnClickListener {
//                Toast.makeText(
//                    binding.root.context,
//                    "${crime.title} clicked: Serious Crime",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            binding.crimeSolved.visibility = if (crime.requiresPolice){
//                View.VISIBLE
//            }else{
//                View.GONE
//            }
//        }
    }
}

class CrimeListAdapter(private val crime: List<Crime>): RecyclerView.Adapter<CrimeHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = // if (viewType == VIEW_TYPE_NORMAL_CRIME) {
            ListItemCrimeBinding.inflate(inflater, parent, false)
//        }else{
//            ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
//        }
        return CrimeHolder(binding)
    }

    override fun getItemCount() = crime.size

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crime[position]
        holder.bind(crime)
    }

//    override fun getItemViewType(position: Int): Int {
//        val crime = crime[position]
////        return if (crime.requiresPolice){
////            VIEW_TYPE_SERIOUS_CRIME
////        }else{
////            VIEW_TYPE_NORMAL_CRIME
////        }
//    }

    companion object{
        private const val VIEW_TYPE_NORMAL_CRIME = 0
        private const val VIEW_TYPE_SERIOUS_CRIME = 1
    }
}