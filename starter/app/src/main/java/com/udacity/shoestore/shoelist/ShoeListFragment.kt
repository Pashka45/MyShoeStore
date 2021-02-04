package com.udacity.shoestore.shoelist

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding

    private lateinit var viewModel: ShoeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_shoe_list, container, false)

        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)

        val shoeListFragmentArgs by navArgs<ShoeListFragmentArgs>()
        val addedShoe = shoeListFragmentArgs.shoe

        binding.lifecycleOwner = this

        binding.fab.setOnClickListener {
            it.findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToDetailedShoeFragment2())
        }

        viewModel.shoesLiveData.observe(viewLifecycleOwner, Observer { shoes ->
            shoes.forEach { shoe ->
                addShoeFullCard(shoe)
            }
        })

        val isNotShoeAdded = viewModel.isNotShoeAdded.value
        if (addedShoe != null && isNotShoeAdded != null && isNotShoeAdded) {
            viewModel.addShoe(addedShoe)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun addShoeFullCard(shoe: Shoe) {
        val shoeCard = LinearLayout(context)
        val shoeCardParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        shoeCardParams.setMargins(0, 0, 0, 30)
        shoeCard.setPadding(10, 20, 10, 20)
        shoeCard.layoutParams = shoeCardParams
        shoeCard.setBackgroundColor(Color.RED)
        shoeCard.setHorizontalGravity(Gravity.HORIZONTAL_GRAVITY_MASK)

        val nameTextView = TextView(context)
        val cardTextLayout = LinearLayout(context)

        cardTextLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        nameTextView.text = shoe.name
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        cardTextLayout.orientation = LinearLayout.VERTICAL

        cardTextLayout.setPadding(20, 0, 0, 0)
        cardTextLayout.addView(nameTextView)

        addFields(R.string.company, shoe.company, cardTextLayout)
        addFields(R.string.size, shoe.size.toString(), cardTextLayout)
        addFields(R.string.description, shoe.description, cardTextLayout)

        if (shoe.images.isNotEmpty()) {
            addShoeImage(shoe.images[0], shoeCard)
        }

        shoeCard.addView(cardTextLayout)

        binding.shoeListContainer.addView(shoeCard);
    }

    private fun addFields(labelText: Int, valueText: String, parent: LinearLayout) {
        val container = LinearLayout(context)
        container.setHorizontalGravity(Gravity.HORIZONTAL_GRAVITY_MASK)

        val label = TextView(context)
        label.setText(labelText)

        val text = TextView(context)
        text.text = valueText

        container.addView(label)
        container.addView(text)
        parent.addView(container)
    }

    private fun addShoeImage(shoeDrawableId: Int, parent: LinearLayout) {
        val imageView = ImageView(context)
        val imageParams = LinearLayout.LayoutParams(200, 200)
        imageView.layoutParams = imageParams
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        imageView.setImageResource(shoeDrawableId)
        parent.addView(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navdrawer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                || super.onOptionsItemSelected(item)

    }

}