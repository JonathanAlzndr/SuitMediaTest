package com.example.suitmediamobiletest.ui.secondscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.suitmediamobiletest.R
import com.example.suitmediamobiletest.databinding.ActivitySecondBinding
import com.example.suitmediamobiletest.ui.thirdscreen.ThirdActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: "John Doe"

        binding.tvUsername.text = username
        
        binding.btnChooseAUser.setOnClickListener {
            startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }
    }

    companion object {
        const val EXTRA_USERNAME = "username"
    }

}