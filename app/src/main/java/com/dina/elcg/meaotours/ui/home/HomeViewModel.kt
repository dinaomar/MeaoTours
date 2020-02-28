package com.dina.elcg.meaotours.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dina.elcg.meaotours.MeaoDataBase
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class HomeViewModel : ViewModel() {
    private var queryResult: ArrayList<QueryDocumentSnapshot> = ArrayList()
    var mutableResult: MutableLiveData<ArrayList<QueryDocumentSnapshot>> = MutableLiveData()
    private val queryError: MutableLiveData<Exception> = MutableLiveData()
    val queryDataErrorExposed: LiveData<Exception> get() = queryError
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun startDataQuery() {
        MeaoDataBase.meaoDataBase.collection("activities")
            .get().addOnSuccessListener {
                queryResult.clear()
                for (document in it)
                    queryResult.add(document)
                isLoading.value = false
                mutableResult.postValue(queryResult)
            }.addOnFailureListener { queryError.postValue(it) }
    }

    fun refresh() {
        startDataQuery()
    }
}