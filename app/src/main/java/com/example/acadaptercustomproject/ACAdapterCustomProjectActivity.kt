package com.example.acadaptercustomproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.acadaptercustomproject.databinding.ActivityAcAdapterCustomProjectBinding

class ACAdapterCustomProjectActivity : AppCompatActivity() {

    lateinit var binding: ActivityAcAdapterCustomProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcAdapterCustomProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}