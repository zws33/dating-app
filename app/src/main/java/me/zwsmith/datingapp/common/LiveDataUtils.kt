package me.zwsmith.datingapp.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

inline fun <reified S1, S2, U> zip(
    sourceOne: LiveData<S1>,
    sourceTwo: LiveData<S2>,
    crossinline transformation: (S1, S2) -> U
): LiveData<U> {
    return MediatorLiveData<U>().apply {
        var lastS1: S1? = null
        var lastS2: S2? = null

        addSource(sourceOne) {
            lastS1 = it
            if (lastS1 != null && lastS2 != null) {
                this.value = transformation(lastS1!!, lastS2!!)
            }
        }
        addSource(sourceTwo) {
            lastS2 = it
            if (lastS1 != null && lastS2 != null) {
                this.value = transformation(lastS1!!, lastS2!!)
            }
        }
    }
}