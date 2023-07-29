package com.example.java_application

import androidx.lifecycle.MutableLiveData

object UserData {
    private val _isSignedIn = MutableLiveData<Boolean>()
    //val isSignedIn: MutableLiveData<Boolean>
    val isSignedInLiveData: MutableLiveData<Boolean>
        get() = _isSignedIn

    // Add other properties and methods related to user data

    fun setSignedIn(isSignedIn: Boolean) {
        _isSignedIn.value = isSignedIn
    }

    fun isUserSignedIn(): Boolean {
        return _isSignedIn.value ?: false
    }
}

//object UserData {
//    private var isSignedIn: Boolean = false
//
//    fun setSignedIn(isSignedIn: Boolean) {
//        this.isSignedIn = isSignedIn
//        // Add any other logic related to user data management here
//    }
//
//    fun isUserSignedIn(): Boolean {
//        return isSignedIn
//    }
//
//    // Add other properties and methods related to user data
//}

