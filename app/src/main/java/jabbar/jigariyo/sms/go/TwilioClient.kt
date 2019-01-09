package jabbar.jigariyo.sms.go

import jabbar.jigariyo.sms.go.Utils.log
import okhttp3.*
import java.io.IOException

object TwilioClient : SmsClient {

    private const val AUTHORIZATION = "Authorization"
    private const val FORM_TO = "To"
    private const val FORM_FROM = "From"
    private const val FORM_BODY = "Body"

    override fun sendSms(body: String) {

        val okHttpClient = OkHttpClient.Builder().authenticator { _, response ->
            val credentials =
                Credentials.basic(BuildConfig.TWILIO_ACCOUNT_SID, BuildConfig.TWILIO_AUTH_TOKEN)
            response.request().newBuilder().header(AUTHORIZATION, credentials).build()
        }.build()

        val formBody = FormBody.Builder()
            .add(FORM_TO, BuildConfig.TWILIO_TO_NUMBER)
            .add(FORM_FROM, BuildConfig.TWILIO_FROM_NUMBER)
            .add(FORM_BODY, body)
            .build()

        val request = Request.Builder()
            .url(BuildConfig.TWILIO_BASE_URL)
            .post(formBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                log("SMS Sending FAILED!!!!")
            }

            override fun onResponse(call: Call, response: Response) {
                log("SMS SENT....Hurray XO :)")
            }
        })
    }
}