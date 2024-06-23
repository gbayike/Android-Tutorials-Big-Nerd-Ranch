package com.olugbayike.android.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.olugbayike.android.sunset.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var sunset = false

//    private var sunYStart by Delegates.notNull<Float>()

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }

    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }

    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        sunYStart = binding.sun.top.toFloat()
        binding.main.setOnClickListener {
            if(!sunset) {
//                startAnimation(sunYStart)
                startAnimation()
            }else {
//                reverseAnimation(sunYStart)
                reverseAnimation()
            }
//            sunYStart = binding.sun.y
//                startAnimation()
        }

    }

    fun startAnimation() {
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(binding.sun, "y", sunYStart, sunYEnd)
            .setDuration(3000)

//        heightAnimator.repeatCount = 2
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(binding.sky,"backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)

        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
            .ofInt(binding.sky,"backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(3000)

        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val sunYHeatStart = 0f
        val sunYHeatEnd = 360f


        // heat animator
        val heatAnimator = ObjectAnimator
            .ofFloat(binding.sun, "rotation", sunYHeatStart, sunYHeatEnd)
            .setDuration(9000)

        heatAnimator.repeatCount = ObjectAnimator.INFINITE

        // sun reflection
        val refStart = binding.sunReflection.top.toFloat()
        val refEnd = binding.sea.height.toFloat()

        val sunReflectorAnimator = ObjectAnimator
            .ofFloat(binding.sunReflection, "y", refStart, refEnd)
            .setDuration(3000)

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
            .with(sunReflectorAnimator)
            .with(heatAnimator)
            .before(nightSkyAnimator)

        animatorSet.start()

        sunset = true
//        heightAnimator.start()
//        sunsetSkyAnimator.start()




    }

    fun reverseAnimation() {
        val sunYStart = binding.sky.height.toFloat()
        val sunYEnd = binding.sun.top.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(binding.sun, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

//        heightAnimator.start()
        val nightSkyAnimator = ObjectAnimator
            .ofInt(binding.sky,"backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)

        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(binding.sky,"backgroundColor", sunsetSkyColor, blueSkyColor)
            .setDuration(3000)

        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val sunYHeatStart = 0f
        val sunYHeatEnd = 360f

        // heat animator
        val heatAnimator = ObjectAnimator
            .ofFloat(binding.sun, "rotation", sunYHeatStart, sunYHeatEnd)
            .setDuration(9000)

        heatAnimator.repeatCount = ObjectAnimator.INFINITE

        // sun reflection
        val refStart = binding.sea.height.toFloat()
        val refEnd = binding.sunReflection.top.toFloat()


        val sunReflectorAnimator = ObjectAnimator
            .ofFloat(binding.sunReflection, "y", refStart, refEnd)
            .setDuration(3000)

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
//            .with(heatAnimator)
            .with(sunReflectorAnimator)
            .before(nightSkyAnimator)

        animatorSet.start()

        sunset = false
    }
}