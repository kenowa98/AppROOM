package com.kenowa.approom

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        definirAnimacion()

        runTimer()
    }

    private fun definirAnimacion() {
        val animacion: Animation = AnimationUtils.loadAnimation(this, R.anim.entrada)
        iv_logo.animation = animacion
    }

    private fun runTimer() {
        val timer = Timer()
        timer.schedule(
            timerTask {
                goToLogin()
            }, 1800
        )
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}