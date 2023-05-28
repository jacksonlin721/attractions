package com.example.attractions.util.dataBind

import android.os.Looper
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.attractions.util.Event

class EventClickHandler : ClickHandler{
    val event =  MutableLiveData<Event<Unit>>()

    fun triggerClick(){
        if(Looper.myLooper() == Looper.getMainLooper()) {
            event.value = Event(Unit)
        }else {
            event.postValue(Event(Unit))
        }
    }

    override fun onClick(view: View?) {
        triggerClick()
    }
}