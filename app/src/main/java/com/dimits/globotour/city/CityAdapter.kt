package com.dimits.globotour.city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.dimits.globotour.R

class CityAdapter(val context : Context, var cityList : ArrayList<City>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.grid_item_city,parent,false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]

        holder.txvCityName.text = city.name
        holder.imvCityImage.setImageResource(city.imageId)

        if (city.isFavorite){
            holder.imvFavorite.setImageDrawable(holder.icFavoriteFilledImage)
        } else {
            holder.imvFavorite.setImageDrawable(holder.icFavoriteBorderedImage)
        }

        holder.currentPosition = position
        holder.currentCity = city

        holder.setListeners()
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        var currentPosition : Int = -1
        var currentCity : City? = null

        val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        val imvDelete = itemView.findViewById<ImageView>(R.id.imv_delete)
        val imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        val icFavoriteFilledImage = ResourcesCompat.getDrawable(context.resources,
            R.drawable.ic_favorite_filled,null)

        val icFavoriteBorderedImage = ResourcesCompat.getDrawable(context.resources,
            R.drawable.ic_favorite_bordered,null)

        fun setListeners() {
            imvDelete.setOnClickListener(this@CityViewHolder)
            imvFavorite.setOnClickListener(this@CityViewHolder)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.imv_delete -> deleteItem()
                R.id.imv_favorite -> addToFavorites()
            }
        }

        fun deleteItem(){
            cityList.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
            notifyItemRangeChanged(currentPosition,cityList.size)

            VacationSpots.favoriteCityList.remove(currentCity!!)
        }

        fun addToFavorites() {
            currentCity?.isFavorite = !(currentCity?.isFavorite!!) // Toggle the isFavorite Boolean

            if (currentCity?.isFavorite!!){
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
                VacationSpots.favoriteCityList.add(currentCity!!)
            } else {
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)
                VacationSpots.favoriteCityList.remove(currentCity!!)
            }
        }

    }
}