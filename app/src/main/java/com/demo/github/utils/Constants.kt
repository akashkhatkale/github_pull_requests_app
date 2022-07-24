package com.demo.github.utils

import com.demo.github.R


object PullRequestState {
    const val CLOSED_PULL_REQUEST_STATUS = "Closed pull requests"
    const val NORMAL_PULL_REQUEST_STATUS = "Pull requests"
    
    const val CLOSED_PULL_REQUEST = "closed"
    
    val state = hashMapOf(
        "closed" to hashMapOf(
            "state" to CLOSED_PULL_REQUEST,
            "color" to R.color.redColor,
            "icon" to R.drawable.icon_close_pull_request,
            "status" to CLOSED_PULL_REQUEST_STATUS,
            "date" to "closed_at"
        )
    )
}

object UrlConstants {
    // urls
    const val BASE_URL = "https://api.github.com"
    const val REPO_URL = "repos/sampotts/plyr/pulls"
    val USER_URL = "users/${REPO_URL.split('/')[1]}"
}

object ErrorConstants {
    // errors
    const val UNKNOWN_ERROR = "Please try again later"
    const val HTTP_ERROR = "Server timed out.\n Please try again later."
    const val INTERNET_CONNECTION_ERROR = "No internet connection"
}

object Constants {
    // ui logs
    const val FEED_FRAGMENT_LOG = "FEED_FRAGMENT_LOG"

    // ui constants
    const val NO_PULL_REQUEST_STATUS = "No pull requests available"
    const val DEFAULT_STATE = "closed"
    const val PLACEHOLDER_DRAWABLE = R.drawable.icon_person

    // constants
    val month = listOf("Jan", "Feb", "Mar", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec")
}