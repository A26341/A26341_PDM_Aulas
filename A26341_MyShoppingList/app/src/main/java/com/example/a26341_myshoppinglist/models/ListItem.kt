package com.example.a26341_myshoppinglist.models

data class ListItem(
    var tipo: String?,
    var objecto: String?,
    var checked: Boolean
){
    constructor() : this(null, null, false)
}
