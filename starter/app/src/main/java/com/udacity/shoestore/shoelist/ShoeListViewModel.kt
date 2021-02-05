package com.udacity.shoestore.shoelist

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ShoeListViewModel : ViewModel() {

    private val shoes = mutableListOf<Shoe>()

    private val _shoesLiveData = MutableLiveData<List<Shoe>>()

    val shoesLiveData: LiveData<List<Shoe>>
        get() = _shoesLiveData

    private val _isNotShoeAdded = MutableLiveData<Boolean>()
    val isNotShoeAdded: LiveData<Boolean>
        get() = _isNotShoeAdded

    val shoeName = ObservableField<String>()

    val shoeSize = ObservableField<String>()

    val shoeCompany = ObservableField<String>()

    val shoeDescription = ObservableField<String>()

    private val _errorShoeAddingMsg = MutableLiveData<String>()
    val errorShoeAddingMsg: LiveData<String>
        get() = _errorShoeAddingMsg

    init {
        Timber.i("init!")
        _isNotShoeAdded.value = true
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
        _isNotShoeAdded.value = false
    }

    fun getShoe(): Shoe? {
        Timber.i("shoe getting")
        if (shoeName.get() == null) {
            _errorShoeAddingMsg.value = "Shoe name is empty. Please write it"
            return null
        }

        if (shoeSize.get().toString().toDoubleOrNull() == null) {
            _errorShoeAddingMsg.value = "Shoe size is empty or not a number"
            return null
        }

        if (shoeCompany.get() == null) {
            _errorShoeAddingMsg.value = "Shoe company is empty"
            return null
        }

        if (shoeDescription.get() == null) {
            _errorShoeAddingMsg.value = "Shoe description is empty"
            return null
        }

        return Shoe(
            shoeName.get()!!,
            shoeSize.get().toString().toDoubleOrNull()!!,
            shoeCompany.get()!!,
            shoeDescription.get()!!
        )
    }

}