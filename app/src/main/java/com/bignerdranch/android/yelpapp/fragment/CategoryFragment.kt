package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInteractions()
    }
   fun setupInteractions(){

        binding.All.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.all))
            findNavController().navigate(action)
        }
        binding.coffee.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.coffee))
            findNavController().navigate(action)
        }
       binding.restaurant.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.food))
            findNavController().navigate(action)
        }
       binding.malls.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.malls))
            findNavController().navigate(action)
        }
       binding.museumes.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.museums))
            findNavController().navigate(action)
        }
        binding.hotel.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.hotels))
            findNavController().navigate(action)
        }
    }
}