package com.mmk.domain.model

data class Quote(val id:String,val author: String = "", val text: String = "", val isLiked: Boolean = false) {
}