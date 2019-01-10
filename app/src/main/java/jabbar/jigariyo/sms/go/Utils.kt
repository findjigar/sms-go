package jabbar.jigariyo.sms.go

import android.util.Log

const val TAG = "SMS GO"

object Utils {

    private val regex = Regex("\\b(offer|http)\\b", RegexOption.IGNORE_CASE)

    fun log(vararg msgs: String) {
        if (BuildConfig.DEBUG) {
            msgs.forEach {
                Log.d(TAG, it)
            }
        }
    }

    fun doesTheMsgContainBlockedWords(msg: String): Boolean {
        return regex.containsMatchIn(msg)
    }
}