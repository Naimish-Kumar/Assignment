package com.codecreater.assignment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.codecreater.assignment.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var balloon: ImageView
    private lateinit var burstButton: Button
    private lateinit var mediaPlayer: MediaPlayer
    private var currentBalloonColor: Int = R.drawable.balloon_red

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balloon = findViewById(R.id.balloon)
        burstButton = findViewById(R.id.burstButton)
        mediaPlayer = MediaPlayer.create(this, R.raw.burst_sound)

        displayRandomBalloon()

        burstButton.setOnClickListener {
            if (balloon.tag == currentBalloonColor) {
                // Balloon popped successfully
                mediaPlayer.start()
                displayRandomBalloon()
            } else {
                // Wrong color balloon clicked
                Toast.makeText(this, "Wrong color! Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        balloon.setOnClickListener {
            // Simulate a burst when clicking the balloon
            balloon.visibility = View.INVISIBLE
            mediaPlayer.start()
            displayRandomBalloon()
        }
    }

    private fun displayRandomBalloon() {
        val randomColor = if (Random.nextBoolean()) R.drawable.balloon_red else R.drawable.balloon_blue
        val newBalloon = ImageView(this)
        newBalloon.setImageResource(randomColor)
        newBalloon.tag = randomColor
        newBalloon.layoutParams = balloon.layoutParams
        newBalloon.setOnClickListener {
            if (newBalloon.tag == currentBalloonColor) {
                // Balloon popped successfully
                mediaPlayer.start()
                val parent = newBalloon.parent as ViewGroup
                parent.removeView(newBalloon)
                displayRandomBalloon()
            } else {
                // Wrong color balloon clicked
                Toast.makeText(this, "Wrong color! Try again.", Toast.LENGTH_SHORT).show()
            }
        }
        val container = findViewById<ConstraintLayout>(R.id.container) // Replace with your container layout
        container.addView(newBalloon)
        val scaleX = ObjectAnimator.ofFloat(newBalloon, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(newBalloon, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 500 // Adjust animation duration as needed
        animatorSet.start()
        currentBalloonColor = randomColor
    }

}
