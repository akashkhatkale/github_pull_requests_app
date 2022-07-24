package com.demo.github.utils

import com.demo.github.R
import com.demo.github.data.model.PullRequestModel
import com.demo.github.data.model.UserModel
import com.demo.github.utils.Constants.DEFAULT_STATE
import com.demo.github.utils.PullRequestState.CLOSED_PULL_REQUEST
import com.demo.github.utils.PullRequestState.NORMAL_PULL_REQUEST_STATUS
import java.text.SimpleDateFormat
import java.util.*


fun getHeadingTitle(state : String) : String {
    return if (PullRequestState.state.containsKey(state)){
        PullRequestState.state[state]!!["status"] as String
    }else{
        NORMAL_PULL_REQUEST_STATUS
    }
}

fun getInfoText(pullRequest: PullRequestModel) : String {
    val state = pullRequest.state
    if(state == CLOSED_PULL_REQUEST){
        if(pullRequest.closed_at != null){
            val date = getDateAsDay(pullRequest.closed_at)
            if (date == "") {
                return "#${pullRequest.number} ${getStatusText(state)}"
            }
            return "#${pullRequest.number} ${getStatusText(state)} on $date"
        }else{
            return "#${pullRequest.number} ${getStatusText(state)}"
        }

    }else{
        return "#${pullRequest.number}"
    }
}


fun getDateAsDay(dateString : String?) : String {
    return try{
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val formatted = dateTimeFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = formatted
        "${calendar.get(Calendar.DAY_OF_MONTH)} ${Constants.month[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}"
    }catch (e : Exception){
        ""
    }
}

fun getInfoIcon(state : String) : Int {
    return if(PullRequestState.state.containsKey(state)){
        PullRequestState.state[state]?.get("icon") as Int
    }else{
        R.drawable.icon_git
    }
}

fun getStatusText(state : String) : String {
    return if(PullRequestState.state.containsKey(state)){
        PullRequestState.state[state]?.get("state") as String
    }else{
        DEFAULT_STATE
    }
}

fun getStatusTextColor(state : String) : Int {
    return if(PullRequestState.state.containsKey(state)){
        PullRequestState.state[state]?.get("color") as Int
    }else{
        R.color.colorAccent
    }
}

fun getWelcomeText(user : UserModel) : String {
    return if(user.login.isEmpty()){
        "Hello"
    }else{
        "Hello, ${user.login}"
    }
}