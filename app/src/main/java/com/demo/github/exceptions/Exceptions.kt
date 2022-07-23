package com.demo.github.exceptions

import com.demo.github.utils.ErrorConstants.HTTP_ERROR
import com.demo.github.utils.ErrorConstants.INTERNET_CONNECTION_ERROR
import com.demo.github.utils.ErrorConstants.UNKNOWN_ERROR

class NoInternetConnectionException : Exception(INTERNET_CONNECTION_ERROR)


class HttpException : Exception(HTTP_ERROR)


class UnknownException : Exception(UNKNOWN_ERROR)
