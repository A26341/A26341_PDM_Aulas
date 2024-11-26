package com.example.a26341_myshoppinglist.models

data class ListType(
    var docId: String?,
    var name: String?,
    var description: String?,
    var owners : List<String>?){
    constructor() : this(null, null, null, null)
}
