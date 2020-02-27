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
import com.dina.elcg.meaotours.R
import com.dina.elcg.meaotours.databinding.FragmentHomeBinding
import com.dina.elcg.meaotours.ui.HomeRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var homeViewModel: HomeViewModel? = null
    private lateinit var recyclerAdapter: HomeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel?.startDataQuery()
        homeViewModel?.exposedData!!.observe(viewLifecycleOwner, Observer {
            recyclerAdapter = HomeRecyclerAdapter(it)
            homeRecyclerView.adapter = recyclerAdapter
        })
        homeViewModel?.queryDataErrorExposed!!.observe(viewLifecycleOwner, Observer {
            showErrorDialog(it.message!!)
        })
        return binding.root
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
