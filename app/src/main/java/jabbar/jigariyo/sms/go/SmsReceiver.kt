package jabbar.jigariyo.sms.go

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SubscriptionManager
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "SMS GO"
    }

    @SuppressLint("MissingPermission") // Permission already added at the start of the app
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val stringBuilder = StringBuilder()
            val messagesFromIntent = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            messagesFromIntent?.let {
                for (message in messagesFromIntent) {
                    message?.let { msg ->
                        stringBuilder.append("SMS from ${msg.displayOriginatingAddress} : ${msg.displayMessageBody}\n")
                    }
                }
            }
            val xxx = "\nx-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x"
            Log.d(TAG, xxx)
            Log.d(TAG, stringBuilder.toString())
            val slot = intent.getIntExtra("slot", -1)
            val subscriptionManager =
                context.getSystemService(SubscriptionManager::class.java) as SubscriptionManager
            val subscriptionInfo =
                subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slot)
            Log.d(TAG, "Received on slot --> $slot")
            Log.d(TAG, "Number on this slow: --> ${subscriptionInfo?.number}")
            Log.d(TAG, xxx)
        }
    }
}