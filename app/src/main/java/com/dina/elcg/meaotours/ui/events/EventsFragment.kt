package com.dina.elcg.meaotours.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dina.elcg.meaotours.R
import com.dina.elcg.meaotours.ui.home.HomeRecyclerAdapter
import com.dina.elcg.meaotours.ui.shared.SharedViewModel
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.fragment_eventss.*


class EventsFragment : Fragment(), HomeRecyclerAdapter.ClickListner {

    private lateinit var eventsViewModel: SharedViewModel
    var recyclerAdapter: HomeRecyclerAdapter =
        HomeRecyclerAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel =
            ViewModelProvider(this).get(SharedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_eventss, container, false)
        eventsViewModel.startDataQuery()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
        eventsViewModel.mutableEventsResult.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.updateUsers(it)
        })
    }

    override fun onClick(item: QueryDocumentSnapshot) {
        var geoPoint:GeoPoint = item.getGeoPoint("location")!!
        val bundle = Bundle()
        bundle.putString("title", item.data["title"].toString())
        bundle.putString("description", item.data["description"].toString())
        bundle.putString("image", item.data["image"].toString())
        bundle.putDouble("lat", geoPoint?.latitude)
        bundle.putDouble("long",geoPoint.longitude)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_navigation_events_to_eventDetailedFragment, bundle)
    }

}
