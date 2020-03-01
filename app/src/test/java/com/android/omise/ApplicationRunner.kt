package com.android.omise

import android.os.Build
import com.android.omise.helper.shadow.ShadowUseCase
import com.android.omise.main.MyTestApplication
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class ApplicationRunner(testClass: Class<*>?) : RobolectricTestRunner(testClass) {
    override fun buildGlobalConfig(): Config {
        val shadows: Array<out Class<*>> =
            arrayOf(
                ShadowUseCase::class.java
            )
        return Config.Builder.defaults().setApplication(MyTestApplication::class.java)
            .setShadows(*shadows).setSdk(Build.VERSION_CODES.P).build()
    }
}