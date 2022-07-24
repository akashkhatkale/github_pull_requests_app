package com.demo.github.utils

import com.demo.github.R


object PullRequestState {
    val state = hashMapOf(
        "closed" to hashMapOf(
            "state" to "closed",
            "color" to R.color.redColor,
            "icon" to R.drawable.icon_close_pull_request,
            "status" to R.string.closedPullRequestsTitle
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