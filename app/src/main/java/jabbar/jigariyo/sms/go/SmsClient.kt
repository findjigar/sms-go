package jabbar.jigariyo.sms.go

interface SmsClient {
    fun sendSms(body: String)
}