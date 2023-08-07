package com.bignerdranch.android.yelpapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.sharedpreferences.SharedPreferencesCoordinates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    lateinit var search: SearchView
    lateinit var sharedPreferencesCoordinates: SharedPreferencesCoordinates
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        sharedPreferencesCoordinates = SharedPreferencesCoordinates(requireContext())
        googleMap.setOnMapLongClickListener {
            val lat = it.latitude
            val lon = it.longitude
            val location = LatLng(lat, lon)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            val coordinates = Coordinates(lat, lon)
            sharedPreferencesCoordinates.setCoordinate("key", coordinates)
            findNavController().navigate(R.id.action_mapsFragment_to_categoryFragment)
            googleMap.addMarker(MarkerOptions().position(location))

        }
        googleMap.clear()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { place ->
                val location = place.result
                if (location != null) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val address = geocoder.getFromLocation(
                            location.latitude, location.longitude, 1
                    )
                    lat = address?.get(0)?.latitude ?: 0.0
                    lon = address?.get(0)?.longitude?:0.0
                }
                val currentLocation = LatLng(lat, lon)
                mMap.addMarker(MarkerOptions().position(currentLocation))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
                search = view.findViewById(R.id.search_lcation) as SearchView
                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(string: String?): Boolean {
                        val location = search.query.toString()
                        var list = mutableListOf<Address>()
                        if (location != null || location != " ") {
                            try {
                                val geocoder = Geocoder(requireContext())

                                list = geocoder.getFromLocationName(location, 1) as MutableList<Address>
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            val adress: Address = list.get(0)
                            val latlan: LatLng = LatLng(adress.latitude, adress.longitude)
                            mMap.addMarker(MarkerOptions().position(latlan).title(location))
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlan, 10F))
                        }
                        return false
                    }

                    override fun onQueryTextChange(string: String?): Boolean {
                        return false
                    }
                })
            }
            return view
        } else {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}