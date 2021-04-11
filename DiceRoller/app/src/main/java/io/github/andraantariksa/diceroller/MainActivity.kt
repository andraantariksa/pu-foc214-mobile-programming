package io.github.andraantariksa.diceroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.andraantariksa.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var diceValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setupButton()
    }

    fun setupButton() {
        binding.buttonRoll.setOnClickListener {
            diceValue = (1..6).random()
            changeDice()
        }
    }

    fun changeDice() {
        val imageId: Int
        when (diceValue) {
            1 -> {
                imageId = R.mipmap.dice_one
            }
            2 -> {
                imageId = R.mipmap.dice_two
            }
            3 -> {
                imageId = R.mipmap.dice_three
            }
            4 -> {
                imageId = R.mipmap.dice_four
            }
            5 -> {
                imageId = R.mipmap.dice_five
            }
            6 -> {
                imageId = R.mipmap.dice_six
            }
            else -> { TODO("Unimplemented") }
        }
        binding.imageViewDice.setImageResource(imageId)
        binding.textViewDiceInfo.setText("You got $diceValue!")
    }
}
