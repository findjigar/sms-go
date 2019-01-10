package jabbar.jigariyo.sms.go

import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun doesTheMsgContainBlockedWords_offer_true() {
        val input = "offer"
        Assert.assertTrue(Utils.doesTheMsgContainBlockedWords(input))
    }

    @Test
    fun `Ok bye offer ok bye`() {
        val input = "Ok bye offer ok bye"
        Assert.assertTrue(Utils.doesTheMsgContainBlockedWords(input))
    }

    @Test
    fun doesTheMsgContainBlockedWords_Offer_true() {
        val input = "Offer"
        Assert.assertTrue(Utils.doesTheMsgContainBlockedWords(input))
    }

    @Test
    fun doesTheMsgContainBlockedWords_OFFER_true() {
        val input = "OFFER"
        Assert.assertTrue(Utils.doesTheMsgContainBlockedWords(input))
    }

    @Test
    fun doesTheMsgContainBlockedWords_OTP_false() {
        val input = "OTP"
        Assert.assertFalse(Utils.doesTheMsgContainBlockedWords(input))
    }

    @Test
    fun doesTheMsgContainBlockedWords_http_true() {
        val input = "http"
        Assert.assertTrue(Utils.doesTheMsgContainBlockedWords(input))
    }
}