package jabbar.jigariyo.sms.go

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SubscriptionManager
import jabbar.jigariyo.sms.go.Utils.log

class SmsReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission") // Permission already added at the start of the app
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val stringBuilder = StringBuilder()
            val messagesFromIntent = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            messagesFromIntent?.let {
                for (message in messagesFromIntent) {
                    message?.let { msg ->
                        stringBuilder.append("SMS from ${msg.displayOriginatingAddress}\n\n${msg.displayMessageBody}\n")
                    }
                }
            }
            val xxx = "\nx-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x"
            log(xxx, stringBuilder.toString())
            val slot = intent.getIntExtra("slot", -1)
            val subscriptionManager =
                context.getSystemService(SubscriptionManager::class.java) as SubscriptionManager
            val subscriptionInfo =
                subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slot)

            log(
                "Received on slot --> $slot",
                "Number on this slot: --> ${subscriptionInfo?.number}",
                xxx
            )
            TwilioClient.sendSms(stringBuilder.toString())
        }
    }
}