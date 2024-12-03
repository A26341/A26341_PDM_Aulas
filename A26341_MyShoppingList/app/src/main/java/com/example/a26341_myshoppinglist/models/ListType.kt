package com.example.a26341_myshoppinglist.models

import android.os.Parcelable
import com.google.firebase.firestore.Exclude




data class ListType(
    @get:Exclude var docId: String?,
    var name: String,
    var description: String,
    var owners: List<String>
){

    constructor() : this(null, "", "", emptyList())
}
