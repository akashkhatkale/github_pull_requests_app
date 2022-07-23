package com.demo.github.data.model


data class PullRequestModel (
    val title : String = "",
    val body : String = "",
    val created_at : String = "",
    val updated_at : String = "",
    val closed_at : String = "",
    val merged_at : String = "",
    val url : String = "",
    val id : Int = 0,
    val number : Int = 0,
    val state : String = "",
    val user : PullRequestUserModel = PullRequestUserModel()
)