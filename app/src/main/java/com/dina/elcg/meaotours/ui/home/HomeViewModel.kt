package com.dina.elcg.meaotours.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dina.elcg.meaotours.MeaoDataBase
import com.google.firebase.firestore.QuerySnapshot

class HomeViewModel : ViewModel() {
    private var queryResult:MutableLiveData<QuerySnapshot> = MutableLiveData()
    private val queryError:MutableLiveData<Exception> = MutableLiveData()
    val exposedData:LiveData<QuerySnapshot> get() = queryResult
    val queryDataErrorExposed:LiveData<Exception> get() = queryError
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true)

    fun startDataQuery(){
        MeaoDataBase.meaoDataBase.collection("activities")
            .get().addOnSuccessListener {
                queryResult.postValue(it)
                isLoading.postValue(false)
            }.addOnFailureListener { queryError.postValue(it) }
    }
}