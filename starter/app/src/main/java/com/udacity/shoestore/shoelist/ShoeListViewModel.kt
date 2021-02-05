package com.udacity.shoestore.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {

    private val shoes = mutableListOf<Shoe>()

    private val _shoesLiveData = MutableLiveData<List<Shoe>>()

    val shoesLiveData: LiveData<List<Shoe>>
        get() = _shoesLiveData

    init {
        fillShoeList()
    }

    private fun fillShoeList() {
        val shoesList = listOf(
            Shoe("shoe7", 20.6, "Nike", "Very nice shoes!", listOf(R.drawable.shoe7)),
            Shoe("shoe6", 35.5, "Adidas", "Impressive shoes!", listOf(R.drawable.shoe6)),
            Shoe("shoe5", 40.0, "Reebok", "Epic shoes!", listOf(R.drawable.shoe5)),
            Shoe("shoe4", 37.8, "Adidas", "Elite shoes!", listOf(R.drawable.shoe4)),
            Shoe("shoe3", 38.2, "Nike", "Comfortable shoes!", listOf(R.drawable.shoe3)),
            Shoe("shoe2", 39.3, "Reebok", "Sport shoes!", listOf(R.drawable.shoe2)),
            Shoe("shoe1", 30.5, "Nike", "Cheap shoes!", listOf(R.drawable.shoe1))
        )

        for (shoe in shoesList) {
            shoes.add(shoe)
        }

        _shoesLiveData.value = shoes
    }

    fun addShoe(newShoe: Shoe) {
        shoes.add(newShoe)
    }
}