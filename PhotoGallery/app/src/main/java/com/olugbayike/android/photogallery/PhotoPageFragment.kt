package com.olugbayike.android.photogallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.olugbayike.android.photogallery.databinding.FragmentPhotoPageBinding
private const val TAG = "PhotoPageFragment"

class PhotoPageFragment : Fragment() {
    private val args: PhotoPageFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentPhotoPageBinding.inflate(inflater, container, false)

        binding.apply {
            topAppBar.setNavigationOnClickListener {
                Log.d(TAG, "Navigation clicked")
                findNavController().popBackStack()
            }
            progressBar.max = 100
            webView.apply{
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(args.photoPageUri.toString())

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                        super.onProgressChanged(view, newProgress)
                        if (newProgress == 100){
                            progressBar.visibility = View.GONE
                        }else{
                            progressBar.visibility = View.VISIBLE
                            progressBar.progress = newProgress
                        }
                    }

                    override fun onReceivedTitle(view: WebView?, title: String?) {
//                        super.onReceivedTitle(view, title)
                        val parent = requireActivity() as AppCompatActivity
                        topAppBar.subtitle = title
                    }
                }
            }
        }
        return binding.root
    }
}