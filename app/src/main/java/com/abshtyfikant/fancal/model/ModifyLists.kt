package com.abshtyfikant.fancal.model

import com.abshtyfikant.fancal.data.Event
import com.abshtyfikant.fancal.data.eventsList

fun addToFollowing(id: Int, title: String, date: String, type: String){
    eventsList.add(Event(id, title, date, type))
}

fun deleteFromFollowing (id: Int){
    val iterator = eventsList.iterator()
    while(iterator.hasNext()){
        if(iterator.next().id == id){
            iterator.remove()
            break
        }
    }
}