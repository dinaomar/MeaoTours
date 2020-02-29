package com.dina.elcg.meaotours.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dina.elcg.meaotours.R
import com.dina.elcg.meaotours.loadImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.event_item.*


class EventDetailedFragment : Fragment(), OnMapReadyCallback {
    private var title: String? = null
    private var description: String? = null
    private var imageUrl: String? = null
    private var long: Double? = null
    private var lat:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title")
            description = it.getString("description")
            imageUrl = it.getString("image")
            lat = it.getDouble("lat")
            long = it.getDouble("long")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.event_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventTitle.text = title
        eventDescription.text = description
        eventImage.loadImage(imageUrl)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val location = LatLng(lat!!, long!!)
        googleMap!!.addMarker(
            MarkerOptions().position(location)
                .title("Marker in Alexandria")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
