package com.fidato.shaadiassignment.utility


import java.text.SimpleDateFormat
import java.util.*

class Utility {

    companion object {
        fun getCurrentTimeStamp(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ")
            return sdf.format(Calendar.getInstance().time)
        }

    }

}