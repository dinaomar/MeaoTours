package com.dina.elcg.meaotours.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dina.elcg.meaotours.MeaoDataBase
import com.google.firebase.firestore.QueryDocumentSnapshot

class SharedViewModel : ViewModel() {
    private var queryResult: ArrayList<QueryDocumentSnapshot> = ArrayList()
    private var eventsFromQuery: ArrayList<QueryDocumentSnapshot> = ArrayList()
    var mutableResult: MutableLiveData<ArrayList<QueryDocumentSnapshot>> = MutableLiveData()
    var mutableEventsResult: MutableLiveData<ArrayList<QueryDocumentSnapshot>> = MutableLiveData()
    private val queryError: MutableLiveData<Exception> = MutableLiveData()
    val queryDataErrorExposed: LiveData<Exception> get() = queryError
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    fun startDataQuery() {
        MeaoDataBase.meaoDataBase.collection("activities")
            .get().addOnSuccessListener {
                queryResult.clear()
                eventsFromQuery.clear()
                for (document in it) {
                    queryResult.add(document)
                    if (document.data["type"].toString() == "event")
                        eventsFromQuery.add(document)
                }
                isLoading.value = false
                mutableResult.postValue(queryResult)
                mutableEventsResult.postValue(eventsFromQuery)
            }.addOnFailureListener { queryError.postValue(it) }
    }

    fun getEvents(): ArrayList<QueryDocumentSnapshot> {
        return eventsFromQuery
    }

    fun refresh() {
        startDataQuery()
    }
}