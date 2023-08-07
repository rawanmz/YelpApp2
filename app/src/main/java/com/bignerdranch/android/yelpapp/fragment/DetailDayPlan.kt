package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.databinding.FragmentDetailDayPlanBinding
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDayPlan : Fragment() {

    private lateinit var myViewModel: MyViewModel
    private val args by navArgs<DetailDayPlanArgs>()
    private lateinit var dayPlan: DayPlan
    var binding: FragmentDetailDayPlanBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailDayPlanBinding.inflate(inflater, container, false);
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        bindData()
        return binding?.root
    }

    private fun bindData() {
        myViewModel.searchDayPlan(args.id).observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    binding?.descriptionedittext?.setText(it.listDescription)
                    it.listDescription = binding?.descriptionedittext?.text.toString()
                    dayPlan = DayPlan(
                        it.yelpId,
                        it.name,
                        it.rating,
                        it.phone,
                        it.is_closed,
                        it.numReviews,
                        it.distanceInMeters,
                        it.imageUrl,
                        it.categories,
                        it.coordinates,
                        it.listDescription
                    )
                    binding?.nametextview?.text = it.name
                    binding?.resturantimageView?.let { it1 ->
                        Glide.with(it1)
                            .load(it.imageUrl).apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(binding?.resturantimageView!!)
                    }

                    binding?.savebutton?.setOnClickListener {
                        dayPlan.listDescription = binding?.descriptionedittext?.text.toString()
                        Toast.makeText(context, R.string.saved_msg, Toast.LENGTH_LONG).show()
                        myViewModel.updateDayPlan(dayPlan)
                        val action = DetailDayPlanDirections.actionDetailDayPlanToDayPlanList()
                        findNavController().navigate(action)
                    }
                    binding?.Detail?.setOnClickListener {
                        val action=DetailDayPlanDirections.actionDetailDayPlanToWeatherFragment(args.id)
                        findNavController().navigate(action)
                    }
                }
            })
    }
}
