package com.bignerdranch.android.yelpapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.yelpapp.MainActivity
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
     var binding: ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater())
        binding?.imageView2?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anmation))
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 1500)
        val view: View = binding!!.root
        setContentView(view)
    }
}
