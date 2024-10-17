package com.example.myapplicationdc.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplicationdc.Activity.DetailActivity
import com.example.myapplicationdc.Domain.DoctorModel
import com.example.myapplicationdc.R
import com.example.myapplicationdc.databinding.ViewholderTopDoctorBinding
import java.util.Locale


class TopDoctorAdapter(private val items: MutableList<DoctorModel>) : RecyclerView.Adapter<TopDoctorAdapter.Viewholder>() {
    private var context: Context? = null

    class Viewholder(val binding: ViewholderTopDoctorBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderTopDoctorBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val doctor = items[position]

        // Set doctor information
        holder.binding.nameTxt.text = doctor.Name
        holder.binding.specialTxt.text = doctor.Special
        holder.binding.scoreTxt.text = doctor.Rating.toString()
        holder.binding.yearTxt.text = "${doctor.Experience} Years"

        // Load image
        Glide.with(holder.itemView.context)
            .load(doctor.Picture)
            .apply(RequestOptions().centerCrop())
            .into(holder.binding.img)

        // Get the SharedPreferences for doctor favorites
        val preferences = context?.getSharedPreferences("doctor_favorites", Context.MODE_PRIVATE)
        val isFavorite = preferences?.getBoolean(doctor.Id.toString(), false) ?: false

        // Set the favorite button icon based on the isFavorite state
        holder.binding.favBtn.setImageResource(if (isFavorite) R.drawable.fav_bold else R.drawable.favorite_white)

        // Set click listener for favorite button
        holder.binding.favBtn.setOnClickListener {
            // Toggle favorite state
            val updatedState = !isFavorite
            preferences?.edit()?.putBoolean(doctor.Id.toString(), updatedState)?.apply()

            // Update favorite button icon based on new state
            holder.binding.favBtn.setImageResource(if (updatedState) R.drawable.fav_bold else R.drawable.favorite_white)
        }

        // Handle item click to navigate to DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("object", doctor)
            }
            context?.startActivity(intent)
        }
    }
    fun updateDoctors(newDoctors: List<DoctorModel>) {
        items.clear()
        items.addAll(newDoctors)
        notifyDataSetChanged()
    }

}