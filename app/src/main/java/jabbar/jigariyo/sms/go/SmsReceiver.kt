package jabbar.jigariyo.sms.go

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import com.google.firebase.analytics.FirebaseAnalytics
import jabbar.jigariyo.sms.go.Utils.log

class SmsReceiver : BroadcastReceiver() {

    private val restrictedSenders = arrayOf("BX-CENTRL", "AX-FCHRGE", "HP-KKLIFE", "MD-VirDas", "HP-TIMPTS")

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var newMsg = ""
            val subscriptionInfo = getSubscriptionInfo(context, intent)
            val messagesFromIntent = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            var shouldIgnoreMsg = false
            var blockedWord = false
            messagesFromIntent?.let {
                val stringBuilder = StringBuilder()
                for (message in messagesFromIntent) {
                    message?.let { msg ->
                        stringBuilder.append(msg.displayMessageBody)
                    }
                }
                blockedWord = Utils.doesTheMsgContainBlockedWords(stringBuilder.toString())
                val firstMsg = messagesFromIntent[0]
                firstMsg?.let { sms ->
                    val sender = sms.displayOriginatingAddress
                    if (restrictedSenders.contains(sender)) {
                        shouldIgnoreMsg = true
                    }
                    newMsg = "SMS from $sender\n\n$stringBuilder"
                    fireAnalytics(context, subscriptionInfo.number, blockedWord, shouldIgnoreMsg, firstMsg)
                }

            }
            if (!shouldIgnoreMsg && !blockedWord) {
                val xxx = "\nx-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x"
                log(xxx, newMsg, "Number on this slot: --> ${subscriptionInfo.number}", xxx)
                TwilioClient.sendSms(newMsg)
            } else {
                log("Message ignored from blocked sender or containing blocked word")
            }
        }
    }

    private fun fireAnalytics(
        context: Context,
        userPhone: String,
        blockedWord: Boolean,
        blockedSender: Boolean,
        message: SmsMessage
    ) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, message.timestampMillis.toString())
        bundle.putString("DISPLAY_SENDER", message.displayOriginatingAddress)
        bundle.putString("SENDER", message.originatingAddress)
        bundle.putString("SERVICE_CENTER", message.serviceCenterAddress)
        bundle.putString("BLOCKED_SENDER", if (blockedSender) "TRUE" else "FALSE")
        bundle.putString("BLOCKED_WORD", if (blockedWord) "TRUE" else "FALSE")
        firebaseAnalytics.setUserId(userPhone)
        firebaseAnalytics.logEvent("MESSAGE", bundle)
    }

    @SuppressLint("MissingPermission") // Permission already added at the start of the app
    private fun getSubscriptionInfo(context: Context, intent: Intent): SubscriptionInfo {
        val slot = intent.getIntExtra("slot", -1)
        val subscriptionManager =
            context.getSystemService(SubscriptionManager::class.java) as SubscriptionManager
        return subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slot)
    }
}