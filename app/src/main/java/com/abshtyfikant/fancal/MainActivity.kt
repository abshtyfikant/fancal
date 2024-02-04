package com.abshtyfikant.fancal

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.abshtyfikant.fancal.services.EventStoreModel
import com.abshtyfikant.fancal.data.eventsList
import com.abshtyfikant.fancal.screens.MainScreen
import com.abshtyfikant.fancal.services.readEvents
import com.abshtyfikant.fancal.services.saveEvents
import com.abshtyfikant.fancal.ui.theme.FancalTheme

class MainActivity : ComponentActivity() {
    private lateinit var eventStoreModel: EventStoreModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        Graph.provideContext(this)

        eventStoreModel = ViewModelProvider(this).get(EventStoreModel::class.java)
        eventStoreModel.readDataStore.observe(this) { storedEvents ->
            readEvents(storedEvents)
        }
        setContent {
            //getUpcoming()
            var darkTheme by remember {mutableStateOf(true)}
            FancalTheme(useDarkTheme = darkTheme) {
                MainScreen(darkTheme = darkTheme,
                    onThemeUpdated = {darkTheme = !darkTheme})
            }
        }

//        eventStoreModel = ViewModelProvider(this).get(EventStoreModel::class.java)
//        eventStoreModel.readDataStore.observe(this) { storedEvents ->
//            readEvents(storedEvents)
//        }
    }
    override fun onPause() {
        super.onPause()
        eventStoreModel.saveDataStore(saveEvents(eventsList))
    }
}