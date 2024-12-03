package com.example.a26341_myshoppinglist.models

import com.google.firebase.firestore.Exclude

data class ListItem(
    @get:Exclude var docId: String?,
    var tipo: String?,
    var objecto: String?,
    var ischecked: Boolean
){
    constructor() : this(null, null, null,false)
}
