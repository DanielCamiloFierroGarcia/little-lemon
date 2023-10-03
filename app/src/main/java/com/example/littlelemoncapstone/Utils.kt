package com.example.littlelemoncapstone

fun validateData(firstName: String, lastName: String, email: String): Boolean{
    if(firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()){
        return true
    }
    return false
}