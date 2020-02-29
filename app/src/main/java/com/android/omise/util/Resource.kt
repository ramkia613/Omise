package com.android.omise.util

import com.android.omise.data.repository.Failure

enum class Status {
    LOADING,
    LOADED,
    FAILED
}

data class Resource<T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val exception: Failure?,
    val requestId: String?
) {

    constructor(status: Status) : this(status, null)

    constructor(status: Status, failure: Failure) : this(status, null, null, failure, null)

    constructor(status: Status, data: T?) : this(status, data, null, null, null)
}