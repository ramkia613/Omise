package com.android.omise.data.repository

import android.content.SharedPreferences
import co.omise.android.api.Client
import co.omise.android.api.RequestListener
import co.omise.android.models.CardParam
import co.omise.android.models.Token
import com.android.omise.main.PUBLIC_KEY
import com.android.omise.util.EncryptionUtil
import com.android.omise.util.getDecryptedString
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val encryptionUtil: EncryptionUtil
) {
    internal var tokenGenerated: (Token) -> Unit = { _ -> }
    internal var tokenNotGenerated: (Throwable) -> Unit = { _ -> }

    fun createToken(cardParam: CardParam) {
        val key = sharedPreferences.getDecryptedString(PUBLIC_KEY, null, encryptionUtil)
        val client = Client(key)

        val request = Token.CreateTokenRequestBuilder(cardParam).build()

        client.send(request, object : RequestListener<Token> {
            override fun onRequestSucceed(model: Token) {
                tokenGenerated(model)
            }

            override fun onRequestFailed(throwable: Throwable) {
                tokenNotGenerated(throwable)
            }
        })
    }
}