package com.example.myapplicationdc.Activity.NavigationButtons

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationdc.Adapter.CategoryAdapter
import com.example.myapplicationdc.Adapters.TopDoctorAdapter
import com.example.myapplicationdc.R
import com.example.myapplicationdc.ViewModel.MainViewModel
import com.example.myapplicationdc.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize category and top doctors
        initCategory()
        initTopDoctor()

        // Set onClickListeners for bottom navigation
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_fav_bold -> {
                    startActivity(Intent(this, FavouriteActivity::class.java))
                    true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.navigation_account -> {
                    startActivity(Intent(this, AllProfilesActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Setup search functionality
        setupSearch() // Make sure to call this to enable search
    }

    private fun setupSearch() {
        binding.editTextText2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().toLowerCase(Locale.ROOT)
                filterDoctors(searchText) // Call the filter function after text changes
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterDoctors(searchText: String) {
        val filteredDoctors = viewModel.doctor.value?.filter {
            it.Name.toLowerCase(Locale.ROOT).contains(searchText) ||
                    it.Special.toLowerCase(Locale.ROOT).contains(searchText)
        } ?: emptyList()

        (binding.recyclerViewTopDoctors.adapter as TopDoctorAdapter).updateDoctors(filteredDoctors)
    }

    private fun initTopDoctor() {
        binding.progressBarTopDoctors.visibility = View.VISIBLE

        viewModel.doctor.observe(this) { doctors ->
            // Set up RecyclerView layout and adapter when data is available
            binding.recyclerViewTopDoctors.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerViewTopDoctors.adapter = TopDoctorAdapter(doctors.toMutableList())

            // Hide progress bar after data is loaded
            binding.progressBarTopDoctors.visibility = View.GONE
        }

        viewModel.loadDoctors()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE

        viewModel.category.observe(this) { categories ->
            binding.viewCategory.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.viewCategory.adapter = CategoryAdapter(categories)

            binding.progressBarCategory.visibility = View.GONE
        }

        viewModel.loadCategory()
    }
}