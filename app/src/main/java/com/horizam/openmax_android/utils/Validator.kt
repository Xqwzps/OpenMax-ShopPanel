package com.horizam.openmax_android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.horizam.openmax_android.R
import java.util.regex.Matcher
import java.util.regex.Pattern


class Validator {

        companion object {

            private fun getText(data: Any): String {
                var str = ""
                if (data is EditText) {
                    str = data.text.toString().trim()
                } else if (data is String) {
                    str = data
                }
                return str
            }

            fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
                val str = getText(data)
                val valid = str.trim().length > 2
                if (updateUI) {
                    val error: String? = if (valid) null else ""
                    setError(data, error)
                }
                return valid
            }

            fun isValidUserName(data: Any, updateUI: Boolean = true , context: Context): Boolean {
                val str = getText(data)
                val valid = str.trim().length > 5
                if (updateUI) {
                    val error: String? = if (valid) null else context.getString(R.string.str_enter_valid_name)
                    setError(data, error)
                }
                return valid
            }

            fun isValidEmail(data: Any, updateUI: Boolean = true, context: Context): Boolean {
                val str = getText(data)
                val valid = Patterns.EMAIL_ADDRESS.matcher(str).matches()
                if (updateUI) {
                    val error: String? = if (valid) null else context
                        .getString(R.string.str_enter_valid_email_address)
                    setError(data, error)
                }
                return valid
            }

            fun isValidGender(data: Any, updateUI: Boolean = true, context: Context): Boolean {
                val str = getText(data)
                var valid = true
                if(str.isNullOrEmpty()){
                    valid = false
                }
                if (updateUI) {
                    val error: String? = if (valid) null else context
                        .getString(R.string.str_enter_valid_email_address)
                    setError(data, error)
                }
                return valid
            }


            fun isValidPassword(data: Any, updateUI: Boolean = true, context: Context): Boolean {
                val str = getText(data)
                var valid = true
                if (str.length < 6) {
                    valid = false
                }
                if (updateUI) {
                    val error: String? = if (valid) null else context
                        .getString(R.string.str_enter_valid_password_policy)
                    setError(data, error)
                }
                return valid
            }
            fun checkPassword(password: String): Boolean {
                val pattern: Pattern
                val passwordPattern =
                    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
                pattern = Pattern.compile(passwordPattern)
                val matcher: Matcher = pattern.matcher(password)
                return matcher.matches()
            }

            fun isValidPhone(data: Any, updateUI: Boolean = true, context: Context): Boolean {
                val str = getText(data)
                val valid = checkPhone(str)
                // Set error if required
                if (updateUI) {
                    val error: String? = if (valid) null else context
                        .getString(R.string.str_enter_valid_phone)
                    setError(data, error)
                }
                return valid
            }

            fun checkPhone(phone:String): Boolean{
                if(phone.length < 10 ||  phone.length > 14){
                    return false
                }
                return true
            }

            fun isValidAddress(data: Any, updateUI: Boolean = true,context: Context): Boolean {
                val str = getText(data)
                val valid = str.trim().isNotEmpty()

                // Set error if required
                if (updateUI) {
                    val error: String? = if (valid) null else context
                        .getString(R.string.str_enter_valid_address)
                    setError(data, error)
                }

                return valid
            }

            fun isValidInput(data: Any, updateUI: Boolean = true,context: Context): Boolean {
                val str = getText(data)
                val valid = str.trim().isNotEmpty()
                if (updateUI) {
                    val error: String? = if (valid) null else context
                        .getString(R.string.str_enter_valid_input)
                    setError(data, error)
                }
                return valid
            }

//            @SuppressLint("ShowToast")
//            fun snackBarMessage(view: View, message: String, isTrue : Boolean = true){
//                val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
//                val snackBarView = snackBar.view
//                snackBar.show()
//                if (isTrue){
////                snackBarView.setBackgroundColor(App.getAppContext()!!.resources.getColor(R.color.snackbar_success))
//                    snackBarView.setBackgroundColor(
//                        ContextCompat.getColor(App.getAppContext()!!,
//                            R.color.snackbar_success))
//                }
//                else{
//                    snackBarView.setBackgroundColor(
//                        ContextCompat.getColor(App.getAppContext()!!,
//                            R.color.snackbar_failure))
//                }
//            }

            private fun setError(data: Any, error: String?) {
                if (data is EditText) {
                    if (data.parent.parent is TextInputLayout) {
                        (data.parent.parent as TextInputLayout).error = error
                    } else {
                        data.error = error
                    }
                }
            }

        }

}