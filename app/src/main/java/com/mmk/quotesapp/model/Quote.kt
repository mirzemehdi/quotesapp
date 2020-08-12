package com.mmk.quotesapp.model

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import com.mmk.quotesapp.utils.GenericRecyclerViewItemModel
import java.util.*

data class Quote (var author:String="",var text:String=""):GenericRecyclerViewItemModel{
    @ServerTimestamp var createdDate:Date?=null
    var id:String?=null

}