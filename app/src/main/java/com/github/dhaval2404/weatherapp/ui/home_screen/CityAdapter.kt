package com.github.dhaval2404.weatherapp.ui.home_screen

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.databinding.AdapterCityListingBinding
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseAdapter
import java.util.Locale

/**
 * List bookmarked cities
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class CityAdapter :
    BaseAdapter<CityEntity, AdapterCityListingBinding, CityAdapter.CityViewHolder>(),
    Filterable {

    private var mCityClickListener: ((CityEntity) -> Unit)? = null
    private var mCityDeleteListener: ((CityEntity) -> Unit)? = null

    private var mCityList: MutableList<CityEntity> = ArrayList()

    /**
     * City click listener
     */
    fun setCityClickListener(listener: ((CityEntity) -> Unit)?) {
        mCityClickListener = listener
    }

    /**
     * City delete listener
     */
    fun setCityDeleteListener(listener: ((CityEntity) -> Unit)?) {
        mCityDeleteListener = listener
    }

    override fun getLayout() = R.layout.adapter_city_listing

    override fun getViewHolder(binding: AdapterCityListingBinding) = CityViewHolder(binding)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CityViewHolder(private val binding: AdapterCityListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            /**
             * Handle city material card click
             */
            binding.root.setOnClickListener {
                mCityClickListener?.invoke(it.tag as CityEntity)
            }

            /**
             * Handle delete button click
             */
            binding.deleteImg.setOnClickListener {
                val cityInfo = it.tag as CityEntity
                remove(cityInfo)
                mCityDeleteListener?.invoke(cityInfo)
            }
        }

        /**
         * Bind city object with xml data-binding
         */
        fun bind(item: CityEntity) {
            binding.cityInfo = item
            binding.executePendingBindings()
        }
    }

    /**
     * Update city list
     */
    override fun refresh(list: List<CityEntity>) {
        super.refresh(list)
        mCityList.clear()
        mCityList.addAll(list)
    }

    /**
     * Remove city item
     */
    private fun remove(city: CityEntity) {
        val indexOf = itemList.indexOf(city)
        itemList.removeAt(indexOf)
        notifyItemRemoved(indexOf)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().toLowerCase(Locale.getDefault())

                val values = if (charSearch.isEmpty()) {
                    // return all if empty
                    mCityList
                } else {
                    // filter city ignore case
                    mCityList.filter {
                        it.address.toLowerCase(Locale.getDefault()).contains(charSearch)
                    }
                }

                return filterResults.also {
                    it.values = values
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemList.clear()
                (results?.values as? List<CityEntity>)?.let {
                    itemList.addAll(it)
                }
                notifyDataSetChanged()
            }
        }
    }
}
