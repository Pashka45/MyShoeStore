package com.udacity.shoestore.detailedshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.shoestore.R
import com.udacity.shoestore.shoelist.ShoeListViewModel
import com.udacity.shoestore.databinding.FragmentDetailedShoeBinding
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class DetailedShoeFragment : Fragment() {

    private lateinit var binding: FragmentDetailedShoeBinding

    private val viewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_detailed_shoe, container, false
            )

        binding.lifecycleOwner = this

        binding.shoe = Shoe("", 0.0, "", "")

        binding.addBtn.setOnClickListener {
            addShoe(it)
        }

        binding.cancelBtn.setOnClickListener {
            cancelAddingShoe(it)
        }

        return binding.root
    }

    private fun addShoe(view: View) {
        val shoeName: String = binding.shoeNameEdit.text.toString()

        if (shoeName.isEmpty()) {
            Snackbar.make(view, "Shoe name is empty. Please write it", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            return
        }

        val size = binding.shoeSizeBtn.text.toString().toDoubleOrNull()
        if (size == null || size <= 0.0) {
            Snackbar.make(view, "Shoe size is empty or not a number or size less than 0.0 or equal to 0.0", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            return
        }

        val company = binding.shoeCompanyEdit.text.toString()
        if (company.isEmpty()) {
            Snackbar.make(view, "Shoe company is empty", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            return
        }

        val description = binding.shoeDescriptionEdit.text.toString()
        if (description.isEmpty()) {
            Snackbar.make(view, "Shoe description is empty", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            return
        }

        val shoe = Shoe(shoeName, size, company, description)
        viewModel.addShoe(shoe)
        view.findNavController().navigate(
            DetailedShoeFragmentDirections.actionDetailedShoeFragmentToShoeListFragment()
        )
    }

    private fun cancelAddingShoe(view: View) {
        view.findNavController().navigate(
            DetailedShoeFragmentDirections.actionDetailedShoeFragmentToShoeListFragment()
        )
    }
}