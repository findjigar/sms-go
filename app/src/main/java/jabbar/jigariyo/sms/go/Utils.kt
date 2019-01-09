package jabbar.jigariyo.sms.go

import android.util.Log

const val TAG = "SMS GO"

object Utils {
    fun log(vararg msgs: String) {
        if (BuildConfig.DEBUG) {
            msgs.forEach {
                Log.d(TAG, it)
            }
        }
    }
}