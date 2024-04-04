package com.olugbayike.android.criminalintent

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.doOnLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.olugbayike.android.criminalintent.databinding.FragmentCrimeDetailBinding
import com.olugbayike.android.criminalintent.databinding.FragmentImageDisplayBinding
import java.io.File

private const val TAG = "ImageDisplayFragment"

class ImageDisplayFragment: DialogFragment() {

    private val args: ImageDisplayFragmentArgs by navArgs()
    var _binding: FragmentImageDisplayBinding? = null

    val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is the view visible?"
        }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_image_display, container, false)
        _binding = FragmentImageDisplayBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }



    private fun setupView() {
        binding.apply {
            updatePhoto(args.imageName)
        }
    }

    private fun updatePhoto(photoFileName: String?) {
        if (binding.crimeImage.tag != photoFileName){
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }

            if (photoFile?.exists() == true){
                binding.crimeImage.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )

                    binding.crimeImage.setImageBitmap(scaledBitmap)
                    binding.crimeImage.tag = photoFileName
                }
            }else{
                binding.crimeImage.setImageBitmap(null)
                binding.crimeImage.tag = null
            }
        }
    }
}