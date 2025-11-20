package me.jaypatelbond.countrypicker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.jaypatelbond.countrypicker.R
import me.jaypatelbond.countrypicker.model.Country

/**
 * RecyclerView adapter for displaying country list items.
 */
class CountryAdapter(
    private var countries: List<Country>,
    private val preSelectedCountryCode: String?,
    private val onCountryClick: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country, country.code == preSelectedCountryCode)
    }

    override fun getItemCount(): Int = countries.size

    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flagTextView: TextView = itemView.findViewById(R.id.flagTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val dialCodeTextView: TextView = itemView.findViewById(R.id.dialCodeTextView)

        fun bind(country: Country, isSelected: Boolean) {
            flagTextView.text = country.flag
            nameTextView.text = country.name
            dialCodeTextView.text = country.dialCode

            // Highlight selected country
            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.selected_country_background)
            } else {
                itemView.setBackgroundResource(R.drawable.country_item_background)
            }

            itemView.setOnClickListener {
                onCountryClick(country)
            }
        }
    }
}

