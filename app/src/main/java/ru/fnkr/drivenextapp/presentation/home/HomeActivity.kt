package ru.fnkr.drivenextapp.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fnkr.drivenextapp.databinding.ActivityHomeBinding
import ru.fnkr.drivenextapp.domain.model.Car

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val adapter = CarAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        adapter.carList = arrayListOf(
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
        )
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        binding.apply {
            rvCars.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvCars.adapter = adapter
        }
    }
}