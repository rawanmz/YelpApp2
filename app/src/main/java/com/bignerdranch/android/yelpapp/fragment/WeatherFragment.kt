package com.bignerdranch.android.yelpapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.yelpapp.BuildConfig
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.data.Hour
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.databinding.ActivitySplashScreenBinding
import com.bignerdranch.android.yelpapp.databinding.FragmentWeatherBinding
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    var binding: FragmentWeatherBinding? = null

//    private lateinit var myViewModelFactory: MyViewModelFactory
    private val args by navArgs<WeatherFragmentArgs>()
    private lateinit var adapter: HourAdapter
    private lateinit var dayPlan: DayPlan
    val sharedViewModel by hiltNavGraphViewModels<MyViewModel>(R.id.my_nav)

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = binding?.root
            //inflater.inflate(R.layout.fragment_weather, container, false)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.constraintLayout2)
        adapter = HourAdapter()
        val myLayoutManager = LinearLayoutManager(context)
        myLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView?.layoutManager = myLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        val value = sharedViewModel.searchRestaurantById(args.id).observe(viewLifecycleOwner,
                Observer {
                    if (it != null) {
                        dayPlan = DayPlan(
                                it.yelpId, it.name, it.rating, it.phone,
                                it.is_closed, it.numReviews, it.distanceInMeters, it.imageUrl,
                                it.categories, it.coordinates, it.listDescription
                        )
                        binding?.placeName?.text = it.name
                        binding?.phone?.text = getString(R.string.phone) + it.phone
                        binding?.rate?.rating = it.rating.toFloat()
                        binding?.reviews?.text = it.numReviews.toString() + getString(R.string.reviews)
                        binding?.category?.text = it.categories[0].title
                        binding?.PlaceDistance?.text = it.displayDistance()
                        if (it.is_closed == true) {
                            binding?.isClose?.text = getString(R.string.closed)
                            binding?.isClose?.setTextColor(Color.RED)
                        } else {
                            binding?.isClose?.text = getString(R.string.open)
                            binding?.isClose?.setTextColor(Color.parseColor("#9AD9DC"))
                        }
                        binding?.placeImg?.let { it1 ->
                            Glide.with(it1)
                                .load(it.imageUrl).apply(
                                    RequestOptions().transforms(
                                        CenterCrop(), RoundedCorners(20)
                                    )
                                ).into(binding?.placeImg!!)
                        }
                        binding?.phoneCallImg?.setOnClickListener {
                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dayPlan.phone))
                            startActivity(intent)
                        }
                        binding?.addImage?.setOnClickListener { view ->
                            sharedViewModel.addDayPlan(dayPlan)
                            val action =
                                    WeatherFragmentDirections.actionWeatherFragmentToDayPlanList()
                            findNavController().navigate(action)
                        }
                    }
                    val connectivityManager =
                            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        sharedViewModel.searchForecastWeather(
                                BuildConfig.WEATHER_API_KEY,
                                "${it.coordinates.latitude},${it.coordinates.longitude}",
                                "3"
                        ).observe(viewLifecycleOwner, Observer {
                            adapter.setData(it.forecast.forecastday[0].hour)
                            binding?.day1?.text = it.forecast.forecastday[0].date
                            binding?.dayIcon1?.let { it1 ->
                                Glide.with(it1)
                                    .load("https://${it.forecast.forecastday[0].day.condition.icon}")
                                    .apply(
                                        RequestOptions().transforms(
                                            CenterCrop(), RoundedCorners(20)
                                        )
                                    ).into(binding?.dayIcon1!!)
                            }
                            binding?.daytemp1?.text =
                                    it.forecast.forecastday[0].day.avgtemp_c.toString() + getString(R.string.temp_) +
                                            it.forecast.forecastday[0].day.avgtemp_f.toString() + getString(
                                            R.string.f
                                    )
                            binding?.dayweatherdescription1?.text =
                                    getString(R.string.chance_of_rain) + it.forecast.forecastday[0]
                                            .day.daily_chance_of_rain + getString(R.string.percentage)

                            binding?.day2?.text = it.forecast.forecastday[1].date
                            binding?.dayIcon2?.let { it1 ->
                                Glide.with(it1)
                                    .load("https://${it.forecast.forecastday[1].day.condition.icon}")
                                    .apply(
                                        RequestOptions().transforms(
                                            CenterCrop(), RoundedCorners(20)
                                        )
                                    ).into(binding?.dayIcon2!!)
                            }
                            binding?.daytemp2?.text =
                                    it.forecast.forecastday[1].day.avgtemp_c.toString() + getString(R.string.temp_) +
                                            it.forecast.forecastday[1].day.avgtemp_f.toString() +
                                            getString(R.string.f)
                            binding?.dayweatherdescription2?.text =
                                    getString(R.string.chance_of_rain) + it.forecast.forecastday[1].day.daily_chance_of_rain +
                                            getString(R.string.percentage)

                            binding?.day3?.text = it.forecast.forecastday[2].date
                            binding?.dayIcon3?.let { it1 ->
                                Glide.with(it1)
                                    .load("https://${it.forecast.forecastday[2].day.condition.icon}")
                                    .apply(
                                        RequestOptions().transforms(
                                            CenterCrop(), RoundedCorners(20)
                                        )
                                    ).into(binding?.dayIcon3!!)
                            }
                            binding?.daytemp3?.text =
                                    it.forecast.forecastday[2].day.avgtemp_c.toString() + getString(R.string.temp_) +
                                            it.forecast.forecastday[2].day.avgtemp_f.toString() +
                                            getString(R.string.f)
                            binding?.dayweatherdescription3?.text =
                                    getString(R.string.chance_of_rain) + it.forecast.forecastday[2].day.daily_chance_of_rain +
                                            getString(R.string.percentage)
                        })
                    } else {
                        AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.no_internet))
                                .setMessage(getString(R.string.check_internet))
                                .setCancelable(true)
                                .setIcon(R.drawable.no_signal)
                                .show()
                        Toast.makeText(context, R.string.no_connection, Toast.LENGTH_LONG).show()
                    }
                })
        return view
    }

    private inner class HourHolder(view: View) :
            RecyclerView.ViewHolder(view)

    private inner class HourAdapter : RecyclerView.Adapter<HourHolder>() {
        var hours = emptyList<Hour>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.hour_item, parent, false)
            return HourHolder(view)
        }

        override fun getItemCount(): Int = hours.size
        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: HourHolder, position: Int) {
            val hour = hours[position]
            val hour1=holder.itemView.findViewById<TextView>(R.id.hour1)
            val temp1=holder.itemView.findViewById<TextView>(R.id.temp1)
            val weatherdescription1=holder.itemView.findViewById<TextView>(R.id.weatherdescription1)
            val icon1=holder.itemView.findViewById<ImageView>(R.id.icon1)

            hour1.text = hour.time.split(" ")[1]
            temp1.text = hour.temp_c.toString() + getString(R.string.temp)
            weatherdescription1.text = hour.condition.text
            Glide.with(icon1)
                    .load("https://${hour.condition.icon}")
                    .apply(
                            RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                            )
                    ).into(icon1)
        }

        fun setData(hours: List<Hour>) {
            this.hours = hours
            notifyDataSetChanged()
        }
    }
}
