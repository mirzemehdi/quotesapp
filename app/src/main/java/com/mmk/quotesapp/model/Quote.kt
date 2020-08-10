package com.mmk.quotesapp.model

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Quote (var author:String,var text:String){
    @ServerTimestamp var createdDate:Date?=null


}