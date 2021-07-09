package com.example.authboy.utils

import android.content.Context
import android.widget.Toast

fun Context.toas(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}