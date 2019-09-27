package com.isaacurbna.urbandictionary.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {

        private const val TAG = "DateUtil"

        private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        private val uiDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

        /**
         * Converts a string date to the same format used in the
         * Urban Dictionary website.
         * Eg: "2016-09-19T00:00:00.000Z" becomes "September 19, 2016"
         *
         * @input stringDate - the string with the date in format "yyyy-MM-ddTHH:mm:ss.SSSZ"
         * @return a string in format "MMMM dd, yyyy"
         */
        fun toUiFormat(dateString: String?): String? {
            if (dateString.isNullOrEmpty()) {
                return null
            }

            var formattedDate: String?
            try {
                val date = apiDateFormat.parse(dateString)
                formattedDate = uiDateFormat.format(date)
            } catch (e: Exception) {
                Log.e(TAG, "error parsing date: \"$dateString\"", e)
                formattedDate = dateString
            }
            return formattedDate
        }
    }
}