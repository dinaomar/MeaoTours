package com.dina.elcg.meaotours.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dina.elcg.meaotours.R
import com.dina.elcg.meaotours.databinding.FragmentHomeBinding
import com.dina.elcg.meaotours.ui.HomeRecyclerAdapter
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var homeViewModel: HomeViewModel? = null
    var recyclerAdapter: HomeRecyclerAdapter = HomeRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel?.startDataQuery()

        homeViewModel?.mutableResult!!.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.updateUsers(it)
        })
        homeViewModel?.queryDataErrorExposed!!.observe(viewLifecycleOwner, Observer {
            showErrorDialog(it.message!!)
        })
        binding.viewmodel = homeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            homeViewModel?.refresh()
        }
    }

    private fun showErrorDialog(message: String) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.ok
                ) { dialog, id ->
                    dialog.dismiss()
                }
                setTitle("Error")
                setMessage(message)
            }
            builder.create()
        }
    }
}
