package com.example.myapplicationdc.Activity.NavigationButtons

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationdc.Adapters.TopDoctorAdapter
import com.example.myapplicationdc.Domain.DoctorModel
import com.example.myapplicationdc.R
import com.example.myapplicationdc.ViewModel.MainViewModel
import com.example.myapplicationdc.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteBinding
    private val doctorList = mutableListOf<DoctorModel>()
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = TopDoctorAdapter(doctorList)
    }

    private fun loadFavorites() {
        val preferences = getSharedPreferences("doctor_favorites", MODE_PRIVATE)

        // Load favorite doctors from SharedPreferences
        val allDoctors = viewModel.doctor.value ?: emptyList()

        allDoctors.forEach { doctor ->
            if (preferences.getBoolean(doctor.Id.toString(), false)) {
                doctorList.add(doctor)
            }
        }

        (binding.recyclerView.adapter as TopDoctorAdapter).notifyDataSetChanged()

        // Show empty view if no favorites
        binding.emptyFavoriteView.visibility = if (doctorList.isNotEmpty()) View.GONE else View.VISIBLE
    }
}