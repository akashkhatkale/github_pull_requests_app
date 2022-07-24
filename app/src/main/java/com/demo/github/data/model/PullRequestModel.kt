package com.demo.github.data.model


data class PullRequestModel (
    val title : String = "",
    val body : String = "",
    val created_at : String? = null,
    val closed_at : String? = null,
    val url : String = "",
    val id : Int = 0,
    val number : Int = 0,
    val state : String = "",
    val user : PullRequestUserModel = PullRequestUserModel()
)