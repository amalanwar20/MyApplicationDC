package com.example.myapplicationdc.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplicationdc.Domain.DoctorModel
import com.example.myapplicationdc.R
import com.example.myapplicationdc.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: DoctorModel
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the binding and set the content view
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load data and set listeners
        getBundle()
        setupFavoriteButton()
        setupRatingBar()
    }

    private fun setupRatingBar() {
        // Set up listener for the RatingBar
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                val doctorId = item.Id
                saveDoctorRating(doctorId, rating.toInt())
            }
        }

        // Set the initial rating
        binding.ratingBar.rating = item.Rating.toFloat()
    }

    private fun getBundle() {
        item = intent.getParcelableExtra<DoctorModel>("object")!!

        binding.apply {
            titleTxt.text = item.Name
            specialTxt.text = item.Special
            patiensTxt.text = item.Patients
            bioTxt.text = item.Biography
            addressTxt.text = item.Address
            experienceTxt.text = "${item.Experience} Years"
            ratingTxt.text = item.Rating.toString()
        }

        binding.backBtn.setOnClickListener { finish() }

        // Load the doctor picture
        Glide.with(this).load(item.Picture).into(binding.img)
    }

    private fun saveDoctorRating(doctorId: Int, rating: Int) {
        Log.d("DetailActivity", "Doctor ID: $doctorId rated with: $rating stars")
        showToast("You rated this doctor with $rating stars.")
    }

    private fun setupFavoriteButton() {
        val preferences = getSharedPreferences("doctor_favorites", MODE_PRIVATE)

        // Check if this doctor is already a favorite
        isFavorite = preferences.getBoolean(item.Id.toString(), false)
        updateFavoriteButton()

        binding.favBtn.setOnClickListener {
            // Toggle favorite state
            isFavorite = !isFavorite
            updateFavoriteButton()

            // Save the favorite state in SharedPreferences
            with(preferences.edit()) {
                putBoolean(item.Id.toString(), isFavorite)
                apply()
            }
        }
    }

    private fun updateFavoriteButton() {
        binding.favBtn.setImageResource(
            if (isFavorite) R.drawable.fav_bold else R.drawable.favorite_white
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}