package com.example.navigationfragmentsapp.model

import java.io.Serializable

data class Event (

    val name: String,
    val category: String,
    val date: String,
    val mili: Long
        ):Serializable