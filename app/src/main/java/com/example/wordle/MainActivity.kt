package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var guessesRemaining = 3
    private lateinit var wordToGuess: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        val guessButton = findViewById<Button>(R.id.guessButton)
        val textInput = findViewById<EditText>(R.id.guessEditTextText)
        val guessWord = findViewById<TextView>(R.id.guessWord)
        val resetButton = findViewById<Button>(R.id.resetButton)

        val guessOne = findViewById<TextView>(R.id.textView3)
        val guessOneCheck = findViewById<TextView>(R.id.textView5)

        val guessTwo = findViewById<TextView>(R.id.textView10)
        val guessTwoCheck = findViewById<TextView>(R.id.textView11)

        val guessThree = findViewById<TextView>(R.id.textView12)
        val guessThreeCheck = findViewById<TextView>(R.id.textView13)

        // Get a random word to guess
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        resetButton.visibility = Button.GONE
        guessButton.setOnClickListener {
            if (guessesRemaining > 0) {
                val guess = textInput.text.toString().uppercase()
                val result = checkGuess(guess, wordToGuess)

                when (guessesRemaining) {
                    3 -> {
                        guessOne.text = guess
                        guessOneCheck.text = result
                    }
                    2 -> {
                        guessTwo.text = guess
                        guessTwoCheck.text = result
                    }
                    1 -> {
                        guessThree.text = guess
                        guessThreeCheck.text = result
                        guessButton.isEnabled = false // Disable the guess button
                    }
                }

                guessesRemaining--
                textInput.editableText.clear()

                if (result == "OOOO") {
                    guessWord.text = wordToGuess
                    Toast.makeText(this, "Congratulations! You guessed the word", Toast.LENGTH_SHORT).show();
                    resetButton.visibility = Button.VISIBLE // Show the Reset button
                    guessButton.visibility = Button.GONE // Hide the Submit button
                } else if (guessesRemaining == 0) {
                    guessWord.text = wordToGuess
                    Toast.makeText(this, "Out of Guesses", Toast.LENGTH_SHORT).show();
                    resetButton.visibility = Button.VISIBLE // Show the Reset button
                    guessButton.visibility = Button.GONE // Hide the Submit button
                }
            }
        }
        resetButton.setOnClickListener {
            // Reset the game
            guessesRemaining = 3
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            guessWord.text = ""
            resetButton.visibility = Button.GONE // Hide the Reset button
            guessButton.visibility = Button.VISIBLE // Show the Submit button
            guessOne.text = ""
            guessOneCheck.text = ""
            guessTwo.text = ""
            guessTwoCheck.text = ""
            guessThree.text = ""
            guessThreeCheck.text = ""
            textInput.editableText.clear()
        }
    }

    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        for (i in 0 until 4) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }
}
