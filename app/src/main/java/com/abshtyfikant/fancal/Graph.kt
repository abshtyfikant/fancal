package com.abshtyfikant.fancal

import android.content.Context

object Graph {
    lateinit var appContext: Context

    fun provideContext(context: Context) {
        appContext = context
    }
}