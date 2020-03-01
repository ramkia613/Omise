package com.android.omise.helper.shadow

import android.content.Context
import com.android.omise.util.EncryptionUtil

class EncryptionTestUtil(context: Context) : EncryptionUtil(context) {
    override fun getKey(): Any? {
        return null
    }

    override fun encrypt(data: String): String {
        return data
    }

    override fun decrypt(data: String): String {
        return data
    }
}