package com.ram.elctricbillscalculator.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ram.elctricbillscalculator.databinding.ActivitySplashScreenBinding

class  SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //This method will be executed once the timer is over
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    this@SplashScreenActivity,
                    CalculateElectricBillActivity::class.java
                )
            )
            finish()
        }, 3000)


    }
}