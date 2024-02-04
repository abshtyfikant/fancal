package com.abshtyfikant.fancal.services

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abshtyfikant.fancal.data.Event
import com.abshtyfikant.fancal.data.eventsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

const val PREFERENCE_NAME = "events"

class EventStore(context: Context) {
    private object PreferenceKeys {
        val events = preferencesKey<String>("events")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveDataStore(){
        dataStore.edit { preference ->
            preference[PreferenceKeys.events] = saveEvents(eventsList)
        }
    }

    val readDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val storedEvents = preference[PreferenceKeys.events] ?: ""
            storedEvents}
}

class EventStoreModel(application: Application) : AndroidViewModel(application) {
    private val repo = EventStore(application)
    val readDataStore = repo.readDataStore.asLiveData()

    fun saveDataStore(events: String) = viewModelScope.launch(Dispatchers.IO) {
        repo.saveDataStore()
    }
}

fun saveEvents(eventsList: MutableList<Event>): String{
    if(eventsList.isEmpty())
        return ""

    var eventsSerialized = ""
    for(e in eventsList){
        eventsSerialized += (e.id.toString() + ";")
        eventsSerialized += (e.title + ";")
        eventsSerialized += (e.date + ";")
        eventsSerialized += (e.type + "+")
    }
    return eventsSerialized
}

fun readEvents(serializedEvents: String){
    if(serializedEvents == "")
        return
    val words = serializedEvents.split("[;+]".toRegex()).map { it.trim() }

    for (i in words.indices step 4) {
        if (i + 3 < words.size) {
            val event = Event(words[i].toInt(), words[i + 1], words[i + 2], words[i + 3])
            eventsList.add(event)
        }
    }
}