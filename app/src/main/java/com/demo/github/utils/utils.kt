package com.demo.github.utils

import com.demo.github.R
import com.demo.github.data.api.Resource
import com.demo.github.data.model.PullRequestModel
import com.demo.github.exceptions.UnknownException
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


fun getHeadingTitle(state: String) : Int {
    return if (PullRequestState.state.containsKey(state)){
        PullRequestState.state[state]!!["status"] as Int
    }else{
        R.string.pullRequests
    }
}

fun getInfoText(pullRequest: PullRequestModel): String {
    val date =
        when (pullRequest.state) {
            "open" -> getDateAsDay(pullRequest.created_at)
            "closed" -> getDateAsDay(pullRequest.closed_at)
            else -> ""
        }

    return "#${pullRequest.number} ${PullRequestState.state[pullRequest.state]?.get("state")} on $date"
}

fun getDate(dateString : String) : Calendar {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val formatted = dateTimeFormat.parse(dateString)
    val calender = Calendar.getInstance()
    calender.time = formatted
    return calender
}

fun getDateAsDay(dateString: String) : String {
    val calendar = getDate(dateString)
    return "${calendar.get(Calendar.DAY_OF_MONTH)} ${Constants.month[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}"
}

fun <T> handleResponseResult(response : Response<T>) : Resource<T> {
    if (response.isSuccessful){
        response.body()?.let{res->
            return Resource.Success(res)
        }
    }
    return Resource.Error(UnknownException())
}