package com.dimits.globotour.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimits.globotour.R
import com.dimits.globotour.city.City
import com.dimits.globotour.city.CityAdapter
import com.dimits.globotour.city.VacationSpots
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteCityList  : ArrayList<City>
    private lateinit var favoriteAdapter  : FavoriteAdapter
    private lateinit var recyclerView  : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View) {
        val context = requireContext()

        favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City>
        favoriteAdapter = FavoriteAdapter(context, favoriteCityList)

        recyclerView = view.findViewById<RecyclerView>(R.id.fav_recycler_view)
        recyclerView?.adapter = favoriteAdapter
        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            Collections.swap(favoriteCityList,fromPosition, toPosition)

            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.adapterPosition
            val deletedCity : City = favoriteCityList.get(position)

            deletedItem(position)
            updateCityList(deletedCity,false)

            Snackbar.make(recyclerView,"deleted", Snackbar.LENGTH_SHORT)
                .setAction("UNDO"){
                    undoDelete(position,deletedCity)
                    updateCityList(deletedCity,true)
                }
                .show()


        }


    })

    private fun undoDelete(position: Int, deletedCity: City) {
        favoriteCityList.add(position,deletedCity)
        favoriteAdapter.notifyItemInserted(position)
        favoriteAdapter.notifyItemRangeChanged(position,favoriteCityList.size)

        val cityList = VacationSpots.cityList!!
        val position = cityList.indexOf(deletedCity)
        cityList[position].isFavorite = false

    }

    private fun updateCityList(deletedCity: City, isFavorite: Boolean) {
        val cityList = VacationSpots.cityList!!
        val position = cityList.indexOf(deletedCity)
        cityList[position].isFavorite = isFavorite
    }


    private fun deletedItem(position: Int) {

        favoriteCityList.removeAt(position)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyItemRangeChanged(position,favoriteCityList.size)
    }
}
