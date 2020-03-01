package com.android.omise.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.preference.PreferenceManager
import com.android.omise.R
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.NoSuchProviderException
import java.security.PrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.AlgorithmParameterSpec
import java.util.Calendar
import javax.crypto.Cipher
import javax.inject.Inject
import javax.inject.Singleton
import javax.security.auth.x500.X500Principal

const val IS_ENCRYPTION_KEY_CREATED = "IS_ENCRYPTION_KEY_CREATED"

@Singleton
open class EncryptionUtil @Inject constructor(val context: Context) {
    private val PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore"
    private val TYPE_RSA = "RSA"
    private val TRANSFORMATION = "RSA/ECB/PKCS1Padding"

    /*we can store multiple key pairs in the Key Store.  The string used to refer to the Key you
    want to store, or later pull, is referred to as an "alias" in this case, because calling it*/
    private val KEY_ALIAS = "MyApplicationKey"

    lateinit var preference: SharedPreferences

    private fun createKey() {
        preference = PreferenceManager.getDefaultSharedPreferences(context)

        if (!preference.getBoolean(IS_ENCRYPTION_KEY_CREATED, false)) {
            val endDate = Calendar.getInstance()
            endDate.add(Calendar.YEAR, 25) // key validity till 25 year

            try {
                // Initialize a KeyPair generator using the the intended algorithm (RSA
                // and the KeyStore. here using the AndroidKeyStore.
                val keyPairGenerator = KeyPairGenerator.getInstance(
                    TYPE_RSA, PROVIDER_ANDROID_KEYSTORE
                )
                // The KeyPairGeneratorSpec object is how parameters for your key pair are passed
                // to the KeyPairGenerator.
                val spec: AlgorithmParameterSpec

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    // Below Android M, use the KeyPairGeneratorSpec.Builder.
                    spec = KeyPairGeneratorSpec.Builder(context)
                        .setAlias(KEY_ALIAS)
                        .setSerialNumber(BigInteger.ONE)
                        .setSubject(X500Principal(context.getString(R.string.certificate).format(KEY_ALIAS)))
                        .setStartDate(Calendar.getInstance().time)
                        .setEndDate(endDate.time).build()
                } else {
                    spec = KeyGenParameterSpec.Builder(
                        KEY_ALIAS, KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_ENCRYPT or
                            KeyProperties.PURPOSE_DECRYPT
                    )
                        .setCertificateSerialNumber(BigInteger.ONE)
                        .setKeyValidityEnd(endDate.time)
                        .setCertificateSubject(
                            X500Principal(
                                context.getString(R.string.certificate).format(
                                    KEY_ALIAS
                                )
                            )
                        )
                        .setUserAuthenticationRequired(false)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA1)
                        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1).build()
                }
                keyPairGenerator.initialize(spec)
                keyPairGenerator.generateKeyPair()
                preference.set(IS_ENCRYPTION_KEY_CREATED, true)
            } catch (e: NoSuchProviderException) {
                e.printStackTrace()
            }
        }
    }

    private fun getKeyStore(): KeyStore {
        return KeyStore.getInstance(PROVIDER_ANDROID_KEYSTORE).apply { load(null) }
    }

    open fun getKey(): Any? {
        createKey()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getKeyStore().getKey(KEY_ALIAS, null)
        } else {
            getKeyStore().getEntry(KEY_ALIAS, null)
        }
    }

    /* Encrypts data using the key*/
    open fun encrypt(data: String): String {
        var pubKey: RSAPublicKey = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getKeyStore().getCertificate(KEY_ALIAS).publicKey as RSAPublicKey
        } else {
            (getKey() as KeyStore.PrivateKeyEntry).certificate?.publicKey as RSAPublicKey
        }

        val cipIn = Cipher.getInstance(TRANSFORMATION)
        cipIn!!.init(Cipher.ENCRYPT_MODE, pubKey)
        val encryptBytes = cipIn.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptBytes, Base64.DEFAULT)
    }

    /*Decrypts data using the key*/
    open fun decrypt(data: String): String {
        if (data.isEmpty()) return ""
        val cipOut = Cipher.getInstance(TRANSFORMATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cipOut.init(Cipher.DECRYPT_MODE, getKey() as PrivateKey)
        } else {
            cipOut.init(Cipher.DECRYPT_MODE, (getKey() as KeyStore.PrivateKeyEntry).privateKey)
        }

        val decryptedBytes = cipOut.doFinal(Base64.decode(data, Base64.DEFAULT))
        return String(decryptedBytes)
    }
}