package com.example.suitmediamobiletest.ui.firstscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.suitmediamobiletest.R
import com.example.suitmediamobiletest.databinding.ActivityMainBinding
import com.example.suitmediamobiletest.ui.secondscreen.SecondActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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

        binding.btnNext.setOnClickListener {
            val username = binding.edtName.text.toString()

            if (username.isEmpty() || username.isBlank()) {
                showDialog("Username Checker","Please fill the username")
            } else {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra(SecondActivity.EXTRA_USERNAME, username)
                startActivity(intent)
            }
        }

        binding.btnCheck.setOnClickListener {
            val palindrome = binding.edtPalindrome.text.toString()

            if (palindrome.isEmpty() || palindrome.isBlank()) {
                showDialog("Palindrome Checker", "Palindrome is empty")
            } else {
                val palindromeChecker = isPalindrome(palindrome)
                if (palindromeChecker) {
                    showDialog("Palindrome Checker", "isPalindrome")
                } else {
                    showDialog("Palindrome Checker", "not palindrome")
                }
            }
        }
    }

    private fun isPalindrome(inputSentence: String): Boolean {
        val cleanSentence = inputSentence.replace(Regex("[^a-zA-Z]"), "").lowercase()
        return cleanSentence == cleanSentence.reversed()
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("OK") { _, _ -> }
        builder.show()
    }
}