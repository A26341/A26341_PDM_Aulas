package com.example.a26341_myshoppinglist.models

data class User(
    var email: String?,
    var password: String?
){
    constructor() : this(null, null)
}
