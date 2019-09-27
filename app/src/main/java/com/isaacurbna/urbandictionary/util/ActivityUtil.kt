package com.isaacurbna.urbandictionary.util

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ActivityUtil {
    companion object {

        private const val TAG = "ActivityUtil"

        fun AppCompatActivity.hideKeyboard(editText: EditText) {
            Log.i(TAG, "hideKeyboard()")
            try {
                val imm =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
            } catch (e: Exception) {
                Log.e(TAG, "error hidding keyboard", e)
            }
        }
    }
}