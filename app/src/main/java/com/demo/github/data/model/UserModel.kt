package com.demo.github.data.model

data class UserModel(
    val login : String = "",
    val name : String = "",
    val bio : String = "",
    val avatar_url : String = "",
    val followers : Int = 0,
    val following : Int = 0,
)
