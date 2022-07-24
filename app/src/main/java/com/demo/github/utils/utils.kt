package com.demo.github.utils

import com.demo.github.data.api.Resource
import com.demo.github.data.model.PullRequestModel
import com.demo.github.exceptions.UnknownException
import com.demo.github.utils.PullRequestState.CLOSED_PULL_REQUEST
import com.demo.github.utils.PullRequestState.NORMAL_PULL_REQUEST_STATUS
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


fun getHeadingTitle(state: String) : String {
    return if (PullRequestState.state.containsKey(state)){
        PullRequestState.state[state]!!["status"] as String
    }else{
        NORMAL_PULL_REQUEST_STATUS
    }
}

fun getInfoText(pullRequest: PullRequestModel): String {
    val state = pullRequest.state
    return try{
        if(state == CLOSED_PULL_REQUEST) {
            val date = getDateAsDay(pullRequest.closed_at)
            "#${pullRequest.number} ${PullRequestState.state[state]?.get("state")} on $date"
        }else{
            "#${pullRequest.number}"
        }
    }catch (e : Exception){
        "#${pullRequest.number} ${PullRequestState.state[pullRequest.state]?.get("state")}"
    }
}

fun getDate(dateString : String?) : Calendar {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val formatted = dateTimeFormat.parse(dateString)
    val calender = Calendar.getInstance()
    calender.time = formatted
    return calender
}

fun getDateAsDay(dateString: String?) : String {
    val calendar = getDate(dateString)
    return "${calendar.get(Calendar.DAY_OF_MONTH)} ${Constants.month[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}"
}