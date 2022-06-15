package com.example.navigationfragmentsapp.adapter

import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.navigationfragmentsapp.model.Event

object SINGLETON {

    var list : MutableList<Event> = mutableListOf()
   var sortedList=list.sortedBy { it.date }

}